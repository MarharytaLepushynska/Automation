package org.naukma;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProcessedJavaFile {
    Path path;
    String className = "";
    String classDescription = "";
    int classesCount;
    List<String> todos = new ArrayList<>();
    List<String> code = new ArrayList<>();

    public ProcessedJavaFile(Path path) {
        this.path = path;
    }
}
