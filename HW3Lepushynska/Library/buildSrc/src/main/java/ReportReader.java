import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public abstract class ReportReader extends DefaultTask {
    private File file = getProject().file("Report.txt");

    @InputFile
    public File getFile(){
        return file;
    }


    @TaskAction
    public void readFile() {
        if(!file.exists()) {
            getLogger().error("File does not exist: " + file.getAbsolutePath());
        }

        try {
            List<String> lines = Files.readAllLines(file.toPath());

            getLogger().lifecycle("\n\nReading file: " + file.getAbsolutePath() + "\n\n");

            for(String line : lines) {
                getLogger().lifecycle(line);
            }

        } catch (IOException e) {
            getLogger().error("Error reading file: " + file.getAbsolutePath());
        }
    }
}
