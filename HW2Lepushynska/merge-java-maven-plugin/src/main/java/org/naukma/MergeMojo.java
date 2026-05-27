package org.naukma;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Mojo(name = "mergeJavaFiles")
public class MergeMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Merging java files started");

        Path path = Paths.get("src/main/java");
        StringBuilder sb = new StringBuilder();

        try(Stream<Path> walk = Files.walk(path)) {
            walk.filter(name -> name.toString().endsWith(".java"))
                    .forEach(file -> {
                        try {
                           sb.append("###File: ").append(file.getFileName()).append("###\n");
                           sb.append(Files.readString(file)).append("\n\n");
                        } catch(IOException e) {
                            getLog().error("Exception while reading files" + e);
                        }
                    });

            Path resultPath = Paths.get("MergedJavaFiles.txt");
            Files.writeString(resultPath, sb.toString());

            getLog().info("Merging java files finished: " + resultPath);
        } catch (IOException e) {
            getLog().error("Exception while reading files" + e);
        }
    }
}
