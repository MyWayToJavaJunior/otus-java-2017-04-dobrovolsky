package ru.otus.dobrovolsky.reflect;

import ru.otus.dobrovolsky.dbService.dataSets.DataSet;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageMetaData {
    private Map<Class<?>, ClassMetaData> annotatedClassesMap = new HashMap<>();

    private static PackageMetaData instance;

    private PackageMetaData() {
    }

    public static PackageMetaData getInstance() {
        if (instance == null) {
            instance = new PackageMetaData();
        }
        return instance;
    }

    private Map<Class<?>, ClassMetaData> getAllClassesFromPackage(String packageName) {
        String packagePath = packageName.replace('.', '/');
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(packagePath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format("Unable to get resources from path '%s'. Are you sure the package '%s' exists?", packagePath, packageName));
        }
        File scannedDir = new File(scannedUrl.getFile());
        for (File file : scannedDir.listFiles()) {
            annotatedClassesMap.putAll(getAllClassesFromPackage(file, packageName));
        }

        return annotatedClassesMap;
    }

    private Map<Class<?>, ClassMetaData> getAllClassesFromPackage(File file, String packageName) {
        Map<Class<?>, ClassMetaData> classes = new HashMap<>();
        if (!file.getName().equalsIgnoreCase("DataSet.class")) {
            String resource = packageName + '.' + file.getName();
            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    classes.putAll(getAllClassesFromPackage(child, resource));
                }
            } else if (resource.endsWith(".class")) {
                int endIndex = resource.length() - ".class".length();
                String className = resource.substring(0, endIndex);
                try {
                    if (ReflectionHelper.isEntity(Class.forName(className))) {
                        classes.put(Class.forName(className), null);
                    }
                } catch (ClassNotFoundException ignore) {
                }
            }
        }
        return classes;
    }

    public void parsePackage(String packageName) throws Exception {
//        annotatedClassesMap = null;
        annotatedClassesMap = getAllClassesFromPackage(packageName);
        parseClasses();
    }

    private void parseClasses() throws Exception {
        for (Map.Entry entry : annotatedClassesMap.entrySet()) {
            ClassMetaData cmd = new ClassMetaData((Class<?>) entry.getKey());
            annotatedClassesMap.replace((Class<?>) entry.getKey(), cmd);
        }
    }

    public Map<Class<?>, ClassMetaData> getAnnotatedClassesMap() {
        return annotatedClassesMap;
    }

    public String getTableName(Class<? extends DataSet> clazz) {
        for (Map.Entry entry : annotatedClassesMap.entrySet()) {
            if (entry.getKey() == clazz) {
                return ((ClassMetaData) entry.getValue()).getTableName();
            }
        }
        return null;
    }

    public List<String> getColumns(Class<? extends DataSet> clazz) {
        for (Map.Entry entry : annotatedClassesMap.entrySet()) {
            if (entry.getKey() == clazz) {
                return ((ClassMetaData) entry.getValue()).getColumns();
            }
        }
        return null;
    }

    public List<Field> getAnnotatedFields(Class<? extends DataSet> clazz) {
        for (Map.Entry entry : annotatedClassesMap.entrySet()) {
            if (entry.getKey() == clazz) {
                return ((ClassMetaData) entry.getValue()).getAnnotatedFields();
            }
        }
        return null;
    }

    public String getColumnsString(Class<? extends DataSet> clazz) {
        for (Map.Entry entry : annotatedClassesMap.entrySet()) {
            if (entry.getKey() == clazz) {
                return ((ClassMetaData) entry.getValue()).getColumnsString();
            }
        }
        return null;
    }

    public void clearAnnotatedClassesMap() {
        this.annotatedClassesMap.clear();
    }
}
