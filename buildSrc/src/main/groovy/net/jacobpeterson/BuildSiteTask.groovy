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
import java.util.stream.Stream

/**
 * A site/ directory should have the following structure:
 * <ul>
 *     <li>src/</li>
 *     <ul>
 *         <li>assets</li>
 *         <li>scss</li>
 *         <li>ts</li>
 *         <li>index.html</li>
 *     </ul>
 *     <li>node_modules/</li>
 *     <li>package.json</li>
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

    final def sourceAssetsDir = siteDir.dir("src/assets")
    final def sourceSCSSDir = siteDir.dir("src/assets")
    final def sourceTSDir = siteDir.dir("src/assets")

    final def distAssetsDir = distDir.dir("assets")
    final def distSCSSDir = distDir.dir("scss")
    final def distTSDir = distDir.dir("ts")

    BuildSiteTask() {
        description = "Builds a site. See net.jacobpeterson.BuildSiteTask."
    }

    @TaskAction
    void executeTaskAction(InputChanges inputChanges) {
        checkFileTree()
        checkNodeJS()
        buildAssets(inputChanges)
    }

    protected void checkFileTree() {
        final def requiredFiles = (Stream<File>) [sourceAssetsDir, sourceSCSSDir, sourceTSDir,
                                                  siteDir.file("package.json")]
                .stream().map({ fileProvider -> fileProvider.get().getAsFile() })

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
        final def requiredPackages = ["typescript", "browserify", "tsify", "node-sass"]

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
        for (fileChangeEntry in fileChangesDepthFirstWithOutputDir(inputChanges.getFileChanges(siteDir),
                sourceAssetsDir.get().getAsFile(), distAssetsDir.get().getAsFile())) {
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

    protected buildHTML() {
        // TODO copy to dist
    }

    protected buildSASS() {
        // TODO node-sass
    }

    protected buildTS() {
        // TODO browserify
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
            Process process = new ProcessBuilder()
                    .directory(siteDir.getAsFile().get())
                    .command(command)
                    .start()

            process.waitFor()
            return process.getInputStream()
        } catch (IOException e) {
            logger.log(LogLevel.ERROR, "Error executing command: " + Arrays.toString(command))
            throw e // Fail the task
        }
    }

    /**
     * Sort paths in inputDir longest to shortest to get depth first of input changes file tree and then maps that
     * path to it's corresponding output path.
     *
     * @param fileChanges the file changes
     * @param inputDir the directory that we care about within inputChanges
     * @param outputDir the output directory that will be the starting point of the corresponding output files
     * (maintain the file tree from inputDir)
     * @return a map of the file change and its corresponding output file
     */
    protected Map<FileChange, String> fileChangesDepthFirstWithOutputDir(Iterable<FileChange> fileChanges,
                                                                         File inputDir, File outputDir) {
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

        for (fileChange in fileChanges.sort(true, longestToShortestPath)) {
            def fileChangeType = fileChange.getFileType()

            if (fileChangeType == FileType.MISSING) {
                throw new UnsupportedOperationException(getClass().getName() + " cannot handle the \'missing\'" +
                        "file change type!")
            }

            def fileChangePathString = fileChange.getFile().getPath()

            // Check if source change file/directory is in the targeted input directory
            if (fileChangePathString.contains(inputDir.getPath())) {
                def fileDistPath = fileChangePathString.replace(inputDir.getPath(), outputDir.getPath())

                returnMap.put(fileChange, fileDistPath)
            }
        }

        return returnMap
    }
}
