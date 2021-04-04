package io.cjf.jimservice.util;

import java.lang.reflect.Field;
import java.util.HashSet;

public class BeanUtil {

    public static String[] getNulls(Object obj) throws IllegalAccessException {
        HashSet<String> nullFields = new HashSet<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Object v = declaredField.get(obj);
            if (v == null) {
                nullFields.add(declaredField.getName());
            }
        }
        String[] strings = new String[nullFields.size()];
        nullFields.toArray(strings);
        return strings;
    }

}
