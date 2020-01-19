package net.jacobpeterson

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileType
import org.gradle.api.file.RegularFileProperty
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
 * This is a task that will run a NodeJS 'build' file whenever there is a change to a JS, TS, JSX, TSX, CSS, or SCSS
 * file in the passed in site directory. It will then copy everything else that isn't in the aforementioned list
 * into the distribution directory with the exact same structure. The passed in NodeJS file will be executed as:
 * <code>node <build.js file> <dist dir path></code>
 * <p>
 * This is so Gradle incremental builds can be used to speed up build time.
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
    // in this directory and execute this Task Action when there is a change (aka when the output dir has been removed).
    @OutputDirectory
    abstract DirectoryProperty getDistDir()

    @InputFile
    abstract RegularFileProperty getNodeJSBuildFile()

    @Input
    boolean startDevServer

    File sourceDir
    File nodeModulesDir
    String[] nodeJSBuildFileExtensions

    BuildSiteTask() {
        description = "Builds a site. See net.jacobpeterson.BuildSiteTask."
    }

    @TaskAction
    void executeTaskAction(InputChanges inputChanges) {
        def isDaemon = Thread.allStackTraces.keySet().any { it.name.contains "Daemon" }
        println isDaemon
        if (startDevServer && isDaemon) {
            throw new IllegalArgumentException("You must run this task with the --no-daemon argument!")
        }

        sourceDir = siteDir.dir("src").get().getAsFile()
        nodeModulesDir = siteDir.dir("node_modules").get().getAsFile()
        nodeJSBuildFileExtensions = (String[]) ["js", "ts", "jsx", "tsx", "css", "scss"].toArray()

        checkFileTree()
        copyFiles(inputChanges)
        runNodeJSBuild(inputChanges)
    }

    protected void checkFileTree() {
        final def requiredFiles = [nodeModulesDir, siteDir.file("package.json").get().getAsFile()]

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

    protected copyFiles(InputChanges inputChanges) {
        def excludedCopyPaths = (String[]) [".idea", "node_modules", "package.json", "package-lock.json"]
                .stream().map({ filePath -> new File(siteDir.get().getAsFile(), filePath).getPath() }).toArray()

        def targetedInputChanges = []

        inputChangesLoop:
        for (inputChange in inputChanges.getFileChanges(siteDir)) {
            if (inputChange.getFile().getPath().endsWithAny(nodeJSBuildFileExtensions)) {
                continue
            }

            for (excludedCopyPath in excludedCopyPaths) {
                if (inputChange.getFile().getPath().contains(excludedCopyPath)) {
                    continue inputChangesLoop
                }
            }
            targetedInputChanges.add(inputChange)
        }

        // Depth first to take into account deleting of directories in the source directory so that we don't delete
        // directories that have files in them before deleting the files themselves first
        for (fileChangeEntry in fileChangesDepthFirstAndMapOutputPath(targetedInputChanges, sourceDir,
                distDir.get().getAsFile())) {
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

    protected runNodeJSBuild(InputChanges inputChanges) {
        def targetedInputChanges = []

        for (inputChange in inputChanges.getFileChanges(siteDir)) {
            if (inputChange.getFile().getPath().endsWithAny(nodeJSBuildFileExtensions)) {
                targetedInputChanges.add(inputChange)
            }
        }

        if (targetedInputChanges.size() > 0) {
            logger.log(LogLevel.INFO, "Running Node JS Build File")

            executeCommand(true, "node", nodeJSBuildFile.get().getAsFile().getPath(),
                    distDir.get().getAsFile().getPath(), startDevServer.toString())

            logger.log(LogLevel.INFO, "Finished running Node JS Build File")
        }
    }

    /**
     * Executes a command via {@link java.lang.Process} with working directory of {@link #getSiteDir()}
     *
     * @param printOutput whether or not to print the output to the Gradle logger
     * @param commands the commands
     *
     * @return the input stream of the process
     */
    protected InputStream executeCommand(boolean printOutput, String... commands) {
        ProcessBuilder processBuilder = new ProcessBuilder()
                .directory(siteDir.getAsFile().get())
                .command(commands)
                .redirectErrorStream(true)

        // This is for the case that you want to run a node JS file from outside the directory containing the
        // node_modules directory
        processBuilder.environment().put("NODE_PATH", nodeModulesDir.getAbsolutePath())

        Process process = processBuilder.start()

        if (printOutput) {
            process.getInputStream().eachLine { line -> logger.log(LogLevel.INFO, "\t" + line) }
        }

        process.waitFor()

        if (process.exitValue() != 0) {
            // Fail the Gradle task
            throw new RuntimeException("Error executing command: " + processBuilder.command())
        }

        return process.getInputStream()
    }

    /**
     * Sort paths in inputDir longest to shortest to get depth first of input changes file tree and then maps that
     * path to it's corresponding output path.
     *
     * @param fileChanges the file changes
     * @param inputDir the directory that we care about within fileChanges
     * @param outputDir the output directory that will be the starting point of the corresponding output files
     * (maintain the file tree from inputDir)
     * @return a map of the file change and its corresponding output file
     */
    protected Map<FileChange, String> fileChangesDepthFirstAndMapOutputPath(List<FileChange> fileChanges,
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
            def fileChangePathString = fileChange.getFile().getPath()

            if (fileChange.getFileType() == FileType.MISSING) {
                throw new UnsupportedOperationException(getClass().getName() + " cannot handle the \'missing\'" +
                        "file change type!")
            }

            // Check if source change file/directory is in the targeted input directory
            if (fileChangePathString.contains(inputDir.getPath())) {
                def fileOutputPath = fileChangePathString.replace(inputDir.getPath(), outputDir.getPath())

                returnMap.put(fileChange, fileOutputPath)
            }
        }

        return returnMap
    }
}
