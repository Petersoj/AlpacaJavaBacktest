package net.jacobpeterson

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileType
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.*
import org.gradle.work.ChangeType
import org.gradle.work.FileChange
import org.gradle.work.Incremental
import org.gradle.work.InputChanges

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

/**
 * A site/ directory should have the following structure:
 * <ul>
 *     <li>src/</li>
 *     <ul>
 *         <li>assets</li>
 *         <li>scss</li>
 *         <li>js</li>
 *         <li>index.html</li>
 *     </ul>
 *     <li>node_modules/</li>
 *     <li>package.json</li>
 *     <li>...</li>
 * </ul>
 * and will produce the following output structure:
 * <ul>
 *     <li>js/</li>
 *     <ul>
 *         <li>bundle.js</li>
 *     </ul>
 *     <li>css/</li>
 *     <ul>
 *         <li>style.css</li>
 *     </ul>
 *     <li>index.html</li>
 *     <li>...</li>
 * </ul>
 */
abstract class BuildSiteTask extends DefaultTask {

    // This is a directory with an implicit getter that will register our task to look for changes
    // in this directory and execute this Task Action when there is a change (aka when there is a modification to the
    // source).
    @Incremental
    @PathSensitive(PathSensitivity.RELATIVE)
    @InputDirectory
    abstract DirectoryProperty getSiteDir()

    // This is a directory with an implicit getter that will register our task to look for changes
    // in this directory and execute this Task Action when there is a change (aka when gradle clean is invoked).
    @OutputDirectory
    abstract DirectoryProperty getDistDir()

    def sourceDir = siteDir.dir("src")
    File sourceAssetsDir
    File sourceSCSSDir
    File sourceJSDir

    File distAssetsDir
    File distCSSDir
    File distJSDir

    File nodeModulesDir
    File[] standardExcludedFiles

    BuildSiteTask() {
        description = "Builds a site. See net.jacobpeterson.BuildSiteTask."
    }

    @TaskAction
    void executeTaskAction(InputChanges inputChanges) {
        initialize()

        checkFileTree()
        checkNodeJS()

        buildAssets(inputChanges)
        buildHTML(inputChanges)
        buildSCSS(inputChanges)
        buildJS(inputChanges)
    }

    protected void initialize() {
        sourceAssetsDir = sourceDir.get().dir("assets").getAsFile()
        sourceSCSSDir = sourceDir.get().dir("scss").getAsFile()
        sourceJSDir = sourceDir.get().dir("js").getAsFile()

        distAssetsDir = distDir.get().dir("assets").getAsFile()
        distCSSDir = distDir.get().dir("css").getAsFile()
        distJSDir = distDir.get().dir("js").getAsFile()

        nodeModulesDir = siteDir.dir("node_modules").get().getAsFile()
        standardExcludedFiles = (File[]) ["node_modules", "package.json", "package-lock.json"]
                .stream().map({ filePath -> new File(siteDir.get().getAsFile(), filePath) }).toArray()
    }

    protected void checkFileTree() {
        final def requiredFiles = [sourceAssetsDir, sourceSCSSDir, sourceJSDir, nodeModulesDir,
                                   siteDir.file("package.json").get().getAsFile()]

        File[] fileTreeDepth2 = (File[]) Files.walk(siteDir.getAsFile().get().toPath(), 2)
                .map({ path -> path.toFile() }).toArray()

        for (requiredFile in requiredFiles) {
            boolean requiredFileNotFound = true

            for (file in fileTreeDepth2) {
                if (file == requiredFile) {
                    requiredFileNotFound = false
                    break
                }
            }

            if (requiredFileNotFound) {
                throw new RuntimeException("You must have the file: " + requiredFile)
            } else {
                logger.log(LogLevel.INFO, "Found required file: " + requiredFile)
            }
        }
    }

    protected checkNodeJS() {
        final def requiredPackages = ["browserify", "node-sass", "@babel/core", "babelify"]

        executeCommand("node", "-v").eachLine { line -> logger.log(LogLevel.INFO, "NodeJS version: " + line) }
        executeCommand("npm", "-v").eachLine { line -> logger.log(LogLevel.INFO, "npm version: " + line) }

        ArrayList<String> npmList = new ArrayList<>()
        executeCommand("npm", "list", "--depth=0").eachLine { line -> npmList.add(line) }

        for (requiredPackage in requiredPackages) {
            boolean requiredPackageNotFound = true

            for (installedPackage in npmList) {
                if (installedPackage.contains(requiredPackage)) {
                    requiredPackageNotFound = false
                    break
                }
            }

            if (requiredPackageNotFound) {
                throw new RuntimeException("You must install node package: " + requiredPackage)
            } else {
                logger.log(LogLevel.INFO, "Found required node package: " + requiredPackage)
            }
        }
    }

    protected buildAssets(InputChanges inputChanges) {
        // Depth first to take into account deleting of directories in the source directory so that we don't delete
        // directories that have files in them before deleting the files themselves first
        for (fileChangeEntry in fileChangesDepthFirstMappedOutputDir(inputChanges.getFileChanges(siteDir),
                sourceAssetsDir, distAssetsDir, standardExcludedFiles)) {
            def fileChange = fileChangeEntry.getKey()
            def fileSourcePath = fileChange.getFile().toPath()
            def fileDistPath = Paths.get(fileChangeEntry.getValue())

            switch (fileChange.getChangeType()) {
                case ChangeType.ADDED:
                case ChangeType.MODIFIED:
                    if (fileChange.getFileType() == FileType.FILE) {
                        Files.createDirectories(fileDistPath.toFile().getParentFile().toPath())
                        Files.copy(fileSourcePath, fileDistPath, StandardCopyOption.REPLACE_EXISTING)
                    } else { // DIRECTORY
                        Files.createDirectories(fileDistPath)
                    }
                    break
                case ChangeType.REMOVED:
                    if (fileChange.getFileType() == FileType.FILE) {
                        Files.deleteIfExists(fileDistPath)
                    } else { // DIRECTORY
                        def removedDirectoryFileList = fileChange.getFile().list()

                        // Check if directory is empty before deleting it from distribution directory
                        if (removedDirectoryFileList == null || removedDirectoryFileList.length == 0) {
                            Files.deleteIfExists(fileDistPath)
                        }
                    }
                    break
            }

            def fileChangeTypeString = fileChange.getChangeType().toString().toLowerCase()
            logger.log(LogLevel.INFO, fileSourcePath.toString() + " has been " +
                    fileChangeTypeString + " in the distribution directory.")
        }
    }

    protected buildHTML(InputChanges inputChanges) {
        for (fileChangeEntry in fileChangesDepthFirstMappedOutputDir(inputChanges.getFileChanges(siteDir),
                sourceDir.get().getAsFile(), distDir.get().getAsFile(), standardExcludedFiles)) {
            def fileChange = fileChangeEntry.getKey()
            def fileSourcePath = fileChange.getFile().toPath()
            def fileDistPath = Paths.get(fileChangeEntry.getValue())

            if (!fileChange.getFile().getName().endsWith(".html")) {
                continue
            }

            switch (fileChange.getChangeType()) {
                case ChangeType.ADDED:
                case ChangeType.MODIFIED:
                    if (fileChange.getFileType() == FileType.FILE) {
                        Files.createDirectories(fileDistPath.toFile().getParentFile().toPath())
                        Files.copy(fileSourcePath, fileDistPath, StandardCopyOption.REPLACE_EXISTING)
                    } else { // DIRECTORY
                        Files.createDirectories(fileDistPath)
                    }
                    break
                case ChangeType.REMOVED:
                    if (fileChange.getFileType() == FileType.FILE) {
                        Files.deleteIfExists(fileDistPath)
                    } else { // DIRECTORY
                        def removedDirectoryFileList = fileChange.getFile().list()

                        // Check if directory is empty before deleting it from distribution directory
                        if (removedDirectoryFileList == null || removedDirectoryFileList.length == 0) {
                            Files.deleteIfExists(fileDistPath)
                        }
                    }
                    break
            }

            def fileChangeTypeString = fileChange.getChangeType().toString().toLowerCase()
            logger.log(LogLevel.INFO, fileSourcePath.toString() + " has been " +
                    fileChangeTypeString + " in the distribution directory.")
        }
    }

    protected buildSCSS(InputChanges inputChanges) {
        def fileChanges = inputChanges.getFileChanges(siteDir)

        if (fileChanges.size() > 0) {
            Files.createDirectories(distCSSDir.toPath())

            logger.log(LogLevel.INFO, "Executing node-sass: ")

            executeCommand("npx", "node-sass", sourceSCSSDir.getAbsolutePath(), "--output", distCSSDir.getAbsolutePath())
                    .eachLine { line -> logger.log(LogLevel.INFO, "\t" + line) }

            logger.log(LogLevel.INFO, "Finished executing node-sass")
        }
    }

    protected buildJS(InputChanges inputChanges) {
        def fileChanges = inputChanges.getFileChanges(siteDir)

        if (fileChanges.size() > 0) {
            Files.createDirectories(distJSDir.toPath())

            logger.log(LogLevel.INFO, "Executing Browserify: ")

            executeCommand("npx", "browserify", sourceJSDir.getAbsolutePath(),
                    "-t", "[babelify", "--presets", "[@babel/preset-env", "@babel/preset-react]", "]",
                    "-o", new File(distJSDir, "bundle.js").getAbsolutePath())
                    .eachLine { line -> logger.log(LogLevel.INFO, "\t" + line) }

            logger.log(LogLevel.INFO, "Finished executing Browserify")
        }
    }

    /**
     * Executes a command via {@link java.lang.Process} with working directory of {@link #getSiteDir()}
     *
     * @param command the commands
     *
     * @return the input stream of the process
     */
    protected InputStream executeCommand(String... command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder()
                    .directory(siteDir.getAsFile().get())
                    .command(command)
                    .redirectErrorStream(true)

            // This is for the case that you want to run a node JS file from outside the directory containing the
            // node_modules directory
            processBuilder.environment().put("NODE_PATH", nodeModulesDir.getAbsolutePath())

            Process process = processBuilder.start()
            process.waitFor()

            return process.getInputStream()
        } catch (IOException e) {
            logger.log(LogLevel.ERROR, "Error executing command: " + Arrays.toString(command))
            throw e // Fail the task
        }
    }

    /**
     * Sort paths in inputDir longest to shortest to get depth first of input changes file tree and then maps that
     * path to it's corresponding desired output path.
     *
     * @param fileChanges the file changes
     * @param inputDir the directory that we care about within inputChanges
     * @param outputDir the output directory that will be the starting point of the corresponding output files
     * (maintain the file tree from inputDir)
     * @param exclude files to exclude in the final map
     * @return a map of the file change and its corresponding output file
     */
    protected Map<FileChange, String> fileChangesDepthFirstMappedOutputDir(Iterable<FileChange> fileChanges,
                                                                           File inputDir, File outputDir,
                                                                           File... excludes) {
        // Sort paths longest to shortest to get depth first of file tree
        // This is to take into account deleting of directories in the source directory so that we don't delete
        // directories that have files in them before deleting the files themselves first
        def longestToShortestPath = new Comparator<FileChange>() {
            @Override
            int compare(FileChange o1, FileChange o2) {
                def o1PathLength = o1.getFile().getPath().length()
                def o2PathLength = o2.getFile().getPath().length()

                return o2PathLength - o1PathLength
            }
        }

        def returnMap = new LinkedHashMap<FileChange, String>()

        fileChangesLoop:
        for (fileChange in fileChanges.sort(true, longestToShortestPath)) {
            def fileChangePathString = fileChange.getFile().getPath()

            for (exclude in excludes) {
                if (fileChangePathString.contains(exclude.getPath())) {
                    continue fileChangesLoop
                }
            }

            if (fileChange.getFileType() == FileType.MISSING) {
                throw new UnsupportedOperationException(getClass().getName() + " cannot handle the \'missing\'" +
                        "file change type!")
            }

            // Check if source change file/directory is in the targeted input directory
            if (fileChangePathString.contains(inputDir.getPath())) {
                def fileDistPath = fileChangePathString.replace(inputDir.getPath(), outputDir.getPath())

                returnMap.put(fileChange, fileDistPath)
            }
        }

        return returnMap
    }
}
