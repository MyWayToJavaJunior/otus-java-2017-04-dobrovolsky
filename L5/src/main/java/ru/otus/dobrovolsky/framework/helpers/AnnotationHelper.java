package ru.otus.dobrovolsky.framework.helpers;

import ru.otus.dobrovolsky.framework.annotation.After;
import ru.otus.dobrovolsky.framework.annotation.Before;
import ru.otus.dobrovolsky.framework.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.otus.dobrovolsky.framework.helpers.ReflectionHelper.*;

/**
 * Created by ketaetc on 13.05.17.
 */
public class AnnotationHelper {

    private AnnotationHelper() {
    }

    static <T> int calcAnnotatedMethodByName(T[] inputData, Class<? extends Annotation> annotationClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?>[] classes = prepareInputData(inputData);

        List<Method> annotatedMethodsList;
        int counter = 0;
        for (Class<?> clazz : classes) {
            annotatedMethodsList = getAnnotatedMethods(clazz, annotationClass);
            counter += annotatedMethodsList.size();
        }
        return counter;
    }

    public static <T> Map<String, Integer> getAnnotatedMethodsMap(T[] inputData) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?>[] classes = prepareInputData(inputData);

        Map<String, Integer> annotationSizeList = new HashMap<>(4);

        annotationSizeList.put("Before", calcAnnotatedMethodByName(classes, Before.class));
        annotationSizeList.put("Test", calcAnnotatedMethodByName(classes, Test.class));
        annotationSizeList.put("After", calcAnnotatedMethodByName(classes, After.class));
        annotationSizeList.put("jUnit.Test", calcAnnotatedMethodByName(classes, org.junit.Test.class));

        return annotationSizeList;
    }
}
