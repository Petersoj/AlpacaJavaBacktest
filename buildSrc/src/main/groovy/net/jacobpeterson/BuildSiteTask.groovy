package net.jacobpeterson

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.*
import org.gradle.work.Incremental
import org.gradle.work.InputChanges

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
    abstract DirectoryProperty getInputDir()

    // This is a directory with an implicit getter that will register our task to look for changes
    // in this directory and execute this Task Action when there is a change (aka when gradle clean is invoked).
    @OutputDirectory
    abstract DirectoryProperty getOutputDir()

    @TaskAction
    void executeTaskAction(InputChanges inputChanges) {
        checkNodeJS()
        buildAssets(inputChanges)
    }

    void checkNodeJS() {
        executeCommand("node", "-v").eachLine { line -> println line }

    }

    void buildAssets(InputChanges inputChanges) {

    }

    /**
     * Executes a command via {@link java.lang.Process} with working directory of {@link #getInputDir()}
     *
     * @return the input stream of the process
     */
    private InputStream executeCommand(String... command) {
        try {
            Process process = new ProcessBuilder()
                    .directory(inputDir.getAsFile().get())
                    .command(command)
                    .start()

            process.waitFor()
            return process.getInputStream()
        } catch (IOException e) {
            logger.log(LogLevel.ERROR, "Error executing command: " + Arrays.toString(command))
            throw e // Fail the task
        }
    }
}
