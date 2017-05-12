package ru.otus.dobrovolsky.framework;

import ru.otus.dobrovolsky.framework.annotation.After;
import ru.otus.dobrovolsky.framework.annotation.Before;
import ru.otus.dobrovolsky.framework.annotation.Test;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("SameParameterValue")
public class ReflectionHelper {
    private ReflectionHelper() {
    }

    private static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        Method[] methods = clazz.getMethods();
        List<Method> annotatedMethods = new ArrayList<>(methods.length);
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotationClass)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    public static void doReflectionWithArrayOfClasses(Class<?>[] classes, HashMap<String, Integer> annotatedMethodsCounter) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        System.out.println("===========================================================");
        System.out.println("Starting using reflections on array of classes:");
        System.out.println(Arrays.toString(classes));
        System.out.println("-----------------------------------------------------------");

        List<Method> beforeList;
        List<Method> testList;
        List<Method> afterList;

        int beforeListSize = 0;
        int testListSize = 0;
        int afterListSize = 0;

        for (Class<?> clazz : classes) {
            beforeList = getAnnotatedMethods(clazz, Before.class);
            testList = getAnnotatedMethods(clazz, Test.class);
            afterList = getAnnotatedMethods(clazz, After.class);

            Object object = clazz.newInstance();
            printMethodInvocationInfo(clazz, null);
            for (Method testMethod : testList) {
                for (Method beforeMethod : beforeList) {
                    beforeMethod.invoke(object);
                    printMethodInvocationInfo(clazz, beforeMethod);
                }

                testMethod.invoke(object);
                printMethodInvocationInfo(clazz, testMethod);

                for (Method afterMethod : afterList) {
                    afterMethod.invoke(object);
                    printMethodInvocationInfo(clazz, afterMethod);
                }
            }
            beforeListSize += beforeList.size();
            testListSize += testList.size();
            afterListSize += afterList.size();

            annotatedMethodsCounter.put("beforeListSize", beforeListSize);
            annotatedMethodsCounter.put("testListSize", testListSize);
            annotatedMethodsCounter.put("afterListSize", afterListSize);
        }
        System.out.println("-----------------------------------------------------------");
        System.out.println("All methods with annotations were invoked successfully");
        System.out.println("===========================================================");
    }

    public static void doReflectionWithPackage(String packageName, HashMap<String, Integer> annotatedMethodsCounter) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        System.out.println("===========================================================");
        System.out.println("Starting using reflections on package " + packageName + ":");
        System.out.println("-----------------------------------------------------------");

        List<Class<?>> classes = getAllClassesFromPackage(packageName);

        List<Method> beforeList;
        List<Method> testList;
        List<Method> afterList;

        int beforeListSize = 0;
        int testListSize = 0;
        int afterListSize = 0;

        for (Class<?> clazz : classes) {
            beforeList = getAnnotatedMethods(clazz, Before.class);
            testList = getAnnotatedMethods(clazz, Test.class);
            afterList = getAnnotatedMethods(clazz, After.class);

            Object object = clazz.newInstance();
            printMethodInvocationInfo(clazz, null);
            for (Method testMethod : testList) {
                for (Method beforeMethod : beforeList) {
                    beforeMethod.invoke(object);
                    printMethodInvocationInfo(clazz, beforeMethod);
                }

                testMethod.invoke(object);
                printMethodInvocationInfo(clazz, testMethod);

                for (Method afterMethod : afterList) {
                    afterMethod.invoke(object);
                    printMethodInvocationInfo(clazz, afterMethod);
                }
            }
            beforeListSize += beforeList.size();
            testListSize += testList.size();
            afterListSize += afterList.size();

            annotatedMethodsCounter.put("beforeListSize", beforeListSize);
            annotatedMethodsCounter.put("testListSize", testListSize);
            annotatedMethodsCounter.put("afterListSize", afterListSize);
        }
        System.out.println("-----------------------------------------------------------");
        System.out.println("All methods with annotations were invoked successfully");
        System.out.println("===========================================================");
    }

    private static List<Class<?>> getAllClassesFromPackage(String packageName) {
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
        return classes;
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

    private static void printMethodInvocationInfo(Class<?> clazz, Method method) {
        System.out.println("====>   " + clazz.getCanonicalName() + "    :   " + ((method != null) ? method.getName(): "class was instantiated") + "<====");
    }
}
