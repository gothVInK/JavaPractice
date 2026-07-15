package org.example.entity.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EntityMetaProvider {
    public static java.util.List<Field> getFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        java.util.List<Field> annotatedFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(annotation)) {
                annotatedFields.add(field);
            }
        }

        return annotatedFields;
    }

    public static Map<String, String> getColumnsStringValue(Object entity) throws IllegalAccessException {
        if (entity == null) {
            return null;
        }

        Map<String, String> ret = new HashMap<>();
        for (Field field : getFieldsWithAnnotation(entity.getClass(), Column.class)) {
            field.setAccessible(true);
            String val = field.get(entity) != null ? String.valueOf(field.get(entity)) : null;
            ret.put(getColumnTitle(field), val);
        }
        return ret;


    }

    public static String getColumnTitle(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null && !column.title().isEmpty()) {
            return column.title();
        }
        return field.getName();
    }
}
