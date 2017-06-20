package ru.otus.dobrovolsky.reflect;

import ru.otus.dobrovolsky.dbService.dataSets.DataSet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*public*/ class ClassMetaData {

    private String tableName;
    private List<String> columns;
    private List<Field> annotatedFields;
    private Class<?> entity;

    /*public*/ ClassMetaData(Class<?> clazz) throws Exception {
        if (ReflectionHelper.isEntity(clazz)) {
            entity = clazz;
        } else {
            throw new Exception("Use only for entity annotated classes");
        }
        parse();
    }

    @SuppressWarnings("unchecked")
    private <T extends DataSet> void parse() {
        columns = new ArrayList<>();
        annotatedFields = new ArrayList<>();

        Collections.addAll(annotatedFields, entity.getDeclaredFields());

        tableName = ReflectionHelper.getTableName(entity);
        columns = ReflectionHelper.getColumnsAndValuesString((Class<T>) entity);
        System.out.println(columns.size() - 1);
    }

    /*public*/ String getColumnsString() {
        StringBuilder ret = new StringBuilder();
        for (String s : columns) {
            ret.append(s).append(", ");
        }
        ret.delete(ret.length() - 2, ret.length()).append(")");

        return ret.toString();
    }

    /*public*/ String getTableName() {
        return tableName;
    }

    /*public*/ List<String> getColumns() {
        return columns;
    }

    /*public*/ List<Field> getAnnotatedFields() {
        return annotatedFields;
    }
}
