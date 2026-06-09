package runtime;

import annotations.Email;
import annotations.MaxLength;
import annotations.MinLength;
import annotations.NotNull;

import java.lang.reflect.Field;

public class ValidatorRun {
    public static void validate(Object obj, Class<?> validatorClass) {
        for (Field field: validatorClass.getDeclaredFields()) {

            String fieldName = field.getName();

            try {
                Field objectField = obj.getClass().getDeclaredField(fieldName);
                objectField.setAccessible(true);

                Object value = objectField.get(obj);

                if (field.isAnnotationPresent(NotNull.class)) {
                    if (value == null) {
                        throw new Exception(fieldName + " cannot be null");
                    }
                }

                if (field.isAnnotationPresent(Email.class)) {
                    if(value == null || !value.toString().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+$")) {
                        throw new Exception(fieldName + " is not a valid email address");
                    }
                }
                if (field.isAnnotationPresent(MinLength.class)) {
                    int minLength = field.getAnnotation(MinLength.class).value();
                    if (value == null || value.toString().length() < minLength) {
                        throw new Exception(fieldName + " must be at least " + minLength + " characters");
                    }
                }
                if(field.isAnnotationPresent(MaxLength.class)) {
                    int maxLength = field.getAnnotation(MaxLength.class).value();
                    if (value == null || value.toString().length() > maxLength) {
                        throw new Exception(fieldName + " must be at most " + maxLength + " characters");
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
