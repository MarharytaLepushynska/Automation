import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ReportGenerator extends DefaultTask {

    @TaskAction
    public void generateReport() {
        getLogger().info("Generating report started");

        Path path = getProject().file("src/main/java").toPath();

        List<ProcessedJavaFile> javaFiles = null;
        try {
            javaFiles = findJavaFiles(path);
        } catch (IOException e) {
            getLogger().error("Failed to find java files", e);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("###REPORT###\n\n");

        createStatistics(javaFiles, sb);
        createClasses(javaFiles, sb);
        createTodos(javaFiles, sb);
        createAllCode(javaFiles, sb);

        Path reportPath = getProject().file("Report.txt").toPath();
        try{
            Files.writeString(reportPath, sb.toString());
            getLogger().info("Report generated: " + reportPath.toAbsolutePath());
        } catch (IOException e) {
            getLogger().error("Failed to write report file", e);
        }
    }

    private List<ProcessedJavaFile> findJavaFiles(Path path) throws IOException {
        try(Stream<Path> walk = Files.walk(path)) {
            return walk.filter(name -> name.toString().endsWith(".java"))
                    .map(this::processJavaFile)
                    .collect(Collectors.toList());
        }
    }

    private ProcessedJavaFile processJavaFile(Path path){
        ProcessedJavaFile file = new ProcessedJavaFile(path);

        try{
            List<String> lines = Files.readAllLines(path);
            file.code = lines;

            String javaDocs = "";
            boolean isJavaDocText = false;
            StringBuilder builder = new StringBuilder();

            for(int i = 0; i < lines.size(); i++){
                String line = lines.get(i).trim();

                if(line.startsWith("/**")) {
                    isJavaDocText = true;
                    builder.setLength(0);
                }
                if (isJavaDocText) {
                    builder.append(line).append("\n");
                }
                if(isJavaDocText && line.endsWith("*/")){
                    isJavaDocText = false;
                    javaDocs = builder.toString();
                }

                if(line.contains("TODO")) {
                    file.todos.add((i+1) + ": " + line);
                }

                if(line.matches(".*\\b(class|interface|enum)\\b.*\\{.*")) {
                    file.classesCount++;
                    if(file.classDescription.isEmpty()) {
                        file.classDescription = javaDocs;
                    }

                    file.className = file.path.getFileName().toString();

                    javaDocs = "";
                }
            }
        } catch (IOException e) {
            getLogger().error("Exception while processing file" + e);
        }

        return file;
    }

    private void createStatistics(List<ProcessedJavaFile> files, StringBuilder sb) {
        int classCount = files.stream().mapToInt(file -> file.classesCount).sum();
        int todosCount = files.stream().mapToInt(file -> file.todos.size()).sum();

        sb.append("##Statistics##\n");
        sb.append("Files: ").append(files.size()).append("\n");
        sb.append("Classes: ").append(classCount).append("\n");
        sb.append("TODOs: ").append(todosCount).append("\n");
    }

    private void createTodos(List<ProcessedJavaFile> files, StringBuilder sb){
        sb.append("##TODOs##\n");
        files.stream().flatMap(file -> file.todos.stream())
                .forEach(todo -> sb.append(todo).append("\n"));
        sb.append("\n");
    }

    private void createClasses(List<ProcessedJavaFile> files, StringBuilder sb){
        sb.append("##Classes##\n");
        for(ProcessedJavaFile file : files){
            sb.append(file.className).append("\n");

            if(!file.classDescription.isEmpty()){
                sb.append(file.classDescription).append("\n");
            }

            sb.append("\n");
        }
    }

    private void createAllCode(List<ProcessedJavaFile> files, StringBuilder sb){
        sb.append("##AllCode##\n");
        for(ProcessedJavaFile file : files){
            sb.append(file.className).append("\n");
            file.code.forEach(code -> sb.append(code).append("\n"));
            sb.append("\n\n");
        }
    }
}
