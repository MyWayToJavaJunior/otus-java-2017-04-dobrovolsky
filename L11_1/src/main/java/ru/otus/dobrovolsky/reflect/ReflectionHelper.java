package ru.otus.dobrovolsky.reflect;

import ru.otus.dobrovolsky.dbService.dataSets.DataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReflectionHelper {
    private static boolean checkString(Object object) {
        return object.getClass().getSimpleName().equals("String");
    }

    private static String prepFieldValue(Object object) {
        if (checkString(object)) {
            return "'" + object.toString() + "'";
        }
        return object.toString();
    }

    public static <T extends DataSet> String getStringForInsert(T dataSet, Class<?> clz) throws IllegalAccessException {
        Class<? extends DataSet> clazz = dataSet.getClass();
        StringBuilder ret = new StringBuilder("(");
        PackageMetaData pmd = PackageMetaData.getInstance();

        for (Field f : pmd.getAnnotatedFields(clazz)) {
            f.setAccessible(true);
            if (!f.isAnnotationPresent(Column.class)) {
                continue;
            }
            if (clz.getSimpleName().equals("Column")) {
                ret.append(getColumnName(f)).append(", ");
            } else if (clz.getSimpleName().equals("Field")) {
                Object val = f.get(dataSet);
                ret.append(prepFieldValue(val)).append(", ");
            } else {
                throw new RuntimeException("Not supported class");
            }
        }
        ret.delete(ret.length() - 2, ret.length()).append(")");

        return ret.toString();
    }

    public static <T extends DataSet> List<String> getColumnsAndValuesString(Class<T> clazz) {
        StringBuilder ret = new StringBuilder();
        Field[] fields = clazz.getDeclaredFields();
        List<String> annotatedFields = new ArrayList<>();

        for (Field f : fields) {
            f.setAccessible(true);
            if (!f.isAnnotationPresent(Column.class)) {
                continue;
            }
            ret.append(getColumnName(f)).append(" ").append(getColumnType(f));

            annotatedFields.add(ret.toString());

            ret.setLength(0);
        }

        return annotatedFields;
    }

    private static String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column.name().equals("") ? field.getName() : column.name();
    }

    private static String getColumnType(Field field) throws RuntimeException {
        Column column = field.getAnnotation(Column.class);
        int length = column.length();
        boolean nullable = column.nullable();
        StringBuilder ret = new StringBuilder();
        String fieldType = field.getType().getSimpleName();
        if (fieldType.equalsIgnoreCase("string")) {
            ret.append("VARCHAR");
        } else if (fieldType.equalsIgnoreCase("boolean")) {
            ret.append("BOOLEAN");
        } else if (fieldType.equalsIgnoreCase("long")) {
            ret.append("BIGINT");
        } else if (fieldType.equalsIgnoreCase("int")) {
            ret.append("INT");
        } else if (fieldType.equalsIgnoreCase("integer")) {
            ret.append("INT");
        } else if (fieldType.equalsIgnoreCase("double")) {
            ret.append("DOUBLE");
        } else if (fieldType.equalsIgnoreCase("float")) {
            ret.append("FLOAT");
        } else {
            throw new RuntimeException("Not supported type");
        }

        if (length > 0) {
            ret.append("(").append(length).append(")");
        }
        if (!nullable) {
            ret.append(" NOT NULL");
        }
        return ret.toString();
    }

    public static String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            return table.name();
        } else {
            return clazz.getName();
        }
    }

    public static boolean isEntity(Class<?> clazz) {
        return clazz.isAnnotationPresent(Entity.class);
    }

    private static void setFieldValue(Object object, String name, Object value) {
        Field field;
        try {
            field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <T extends DataSet> T makeObject(T object, ResultSet result) throws
            SQLException {
        object.setId(result.getLong("id"));
        String fieldType;
        Column column;
        PackageMetaData pmd = PackageMetaData.getInstance();
        for (Field f : pmd.getAnnotatedFields(object.getClass())) {
            if (!f.isAnnotationPresent(Column.class)) {
                continue;
            }
            fieldType = f.getType().getSimpleName();
            column = f.getAnnotation(Column.class);
            if (fieldType.equalsIgnoreCase("string")) {
                setFieldValue(object, f.getName(), result.getString(column.name()));
            } else if (fieldType.equalsIgnoreCase("long")) {
                setFieldValue(object, f.getName(), result.getLong(column.name()));
            } else if (fieldType.equalsIgnoreCase("int")) {
                setFieldValue(object, f.getName(), result.getInt(column.name()));
            } else if (fieldType.equalsIgnoreCase("integer")) {
                setFieldValue(object, f.getName(), result.getInt(column.name()));
            } else if (fieldType.equalsIgnoreCase("boolean")) {
                setFieldValue(object, f.getName(), result.getBoolean(column.name()));
            } else if (fieldType.equalsIgnoreCase("short")) {
                setFieldValue(object, f.getName(), result.getShort(column.name()));
            } else if (fieldType.equalsIgnoreCase("double")) {
                setFieldValue(object, f.getName(), result.getDouble(column.name()));
            } else if (fieldType.equalsIgnoreCase("float")) {
                setFieldValue(object, f.getName(), result.getFloat(column.name()));
            } else {
                throw new RuntimeException("Not supported type.");
            }
        }
        return object;
    }
}
