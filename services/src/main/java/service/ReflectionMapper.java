package service;

import java.lang.reflect.Field;

public class ReflectionMapper {

    public static <T, U> U map(T source, Class<U> targetClass) {
        try {
            U target = targetClass.getDeclaredConstructor().newInstance();

            // Loop through all fields of source
            for (Field sourceField : source.getClass().getDeclaredFields()) {
                sourceField.setAccessible(true);
                Object value = sourceField.get(source); // get value from source

                try {
                    Field targetField = targetClass.getDeclaredField(sourceField.getName());
                    targetField.setAccessible(true);
                    targetField.set(target, value); // set value in target
                } catch (NoSuchFieldException ignored) {
                    // skip if field doesn't exist in target
                }
            }

            return target;

        } catch (Exception e) {
            throw new RuntimeException("Failed to map " + source.getClass() + " to " + targetClass, e);
        }
    }
}
