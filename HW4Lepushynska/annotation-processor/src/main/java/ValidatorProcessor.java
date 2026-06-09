import annotations.*;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes("annotations.GenerateValidator")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@AutoService(Processor.class)
public class ValidatorProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) return false;

        for (Element element : roundEnv.getElementsAnnotatedWith(GenerateValidator.class)) {
            if(element.getKind() != ElementKind.CLASS) continue;

            TypeElement classElement = (TypeElement) element;

            generateValidator(classElement);
        }

        return true;
    }

    private void generateValidator(TypeElement classElement) {
        String className = classElement.getSimpleName().toString();
        String packageName = processingEnv.getElementUtils().getPackageOf(classElement).getQualifiedName().toString();

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(";\n\n");

        sb.append("import annotations.*;\n");
        sb.append("import runtime.ValidatorRun;\n\n");

        sb.append("public class ").append(className).append("Validator {\n\n");

        for(Element element : classElement.getEnclosedElements()) {
            if(element.getKind() != ElementKind.FIELD) continue;

            VariableElement field = (VariableElement) element;

            generateAnnotations(sb, field);
        }

        sb.append("    public void validate(" + className + " obj) throws Exception {\n");
        sb.append("        runtime.ValidatorRun.validate(obj, this.getClass());\n");
        sb.append("    }\n\n");
        sb.append("}\n");

        try {
            JavaFileObject file = processingEnv.getFiler()
                    .createSourceFile(packageName + "." + className + "Validator");

            try (Writer writer = file.openWriter()) {
                writer.write(sb.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateAnnotations(StringBuilder sb, VariableElement field) {
        if(field.getAnnotation(NotNull.class) != null) {
            sb.append("     @NotNull\n");
        }
        if(field.getAnnotation(Email.class) != null) {
            sb.append("     @Email\n");
        }
        if(field.getAnnotation(MinLength.class) != null) {
            int value = field.getAnnotation(MinLength.class).value();
            sb.append("     @MinLength(").append(value).append(")\n");
        }
        if(field.getAnnotation(MaxLength.class) != null) {
            int value = field.getAnnotation(MaxLength.class).value();
            sb.append("     @MaxLength(").append(value).append(")\n");
        }

        String[] fieldType = field.asType().toString().split("\\.");

        sb.append("     private " + fieldType[fieldType.length - 1] + " " + field.getSimpleName() + ";\n\n");
    }
}
