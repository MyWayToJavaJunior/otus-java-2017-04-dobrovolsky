package ru.otus.dobrovolsky.framework.helpers;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ReflectionHelper {
    private ReflectionHelper() {
    }

    static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        Method[] methods = clazz.getMethods();
        List<Method> annotatedMethods = new ArrayList<>(methods.length);
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    private static Class<?>[] getAllClassesFromPackage(String packageName) {
        String packagePath = packageName.replace('.', '/');
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(packagePath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format("Unable to get resources from path '%s'. Are you sure the package '%s' exists?", packagePath, packageName));
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(getAllClassesFromPackage(file, packageName));
        }

        Class<?>[] retClasses = new Class[classes.size()];
        retClasses = classes.toArray(retClasses);

        return retClasses;
    }

    private static List<Class<?>> getAllClassesFromPackage(File file, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String resource = packageName + '.' + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(getAllClassesFromPackage(child, resource));
            }
        } else if (resource.endsWith(".class")) {
            int endIndex = resource.length() - ".class".length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

    static <T> Class<?>[] prepareInputData(T[] inputData) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        StringBuilder packageName = new StringBuilder();
        Class<?>[] classes;

        if (inputData[0].getClass() == java.lang.Class.class) {
            classes = (Class<?>[]) inputData;
        } else if (inputData[0].getClass() == Character.class) {
            Arrays.stream(inputData).forEach(packageName::append);
            classes = getAllClassesFromPackage(packageName.toString());
        } else {
            throw new IllegalArgumentException("Wrong input data!");
        }
        return classes;
    }
}
