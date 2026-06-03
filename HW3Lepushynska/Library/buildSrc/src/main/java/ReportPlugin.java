import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ReportPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().register("generateReport", ReportGenerator.class, task -> {
            task.setGroup("report");
            task.setDescription("Generates report");
        });

        project.getTasks().register("readReport", ReportReader.class, task -> {
            task.setGroup("report");
            task.setDescription("Reads report");
        });
    }
}
