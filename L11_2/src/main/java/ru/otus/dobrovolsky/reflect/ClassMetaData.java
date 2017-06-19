package ru.otus.dobrovolsky.reflect;

import ru.otus.dobrovolsky.dbService.dataSets.DataSet;

import java.util.ArrayList;
import java.util.List;

public class ClassMetaData {

    private String tableName;
    private List<String> annotatedFields = new ArrayList<>();
    private Class<? extends DataSet> entity;

    public ClassMetaData(Class<? extends DataSet> clazz) throws Exception {
        if (ReflectionHelper.isEntity(clazz)) {
            entity = clazz;
        } else {
            throw new Exception("Use only for entity annotated classes");
        }
    }

    public void parse() {
        tableName = ReflectionHelper.getTableName(entity);
        annotatedFields = ReflectionHelper.getColumnsAndValuesString1(entity);
        annotatedFields.add(0, "(id bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY, ");
        System.out.println(annotatedFields.size()-1);
    }

    public void printAnnotatedFields() {
        annotatedFields.forEach(System.out::println);
    }

    public String getTableName() {
        return tableName;
    }

    public List<String> getAnnotatedFields() {
        return annotatedFields;
    }
}
