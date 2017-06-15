package ru.otus.dobrovolsky.myJSON.objectParser;

import ru.otus.dobrovolsky.myJSON.reflect.ReflectionHelper;

public class ObjectParserFactory {
    private ObjectParserFactory() throws Exception {
        throw new Exception("Do not instantiate");
    }

    public static ObjectParser getObjectParser(Object object) {
        ObjectParser objectParser;
        if (ReflectionHelper.checkComplexClass(object)) {
            objectParser = new ComplexObjectParser();
        } else {
            objectParser = new CommonObjectParser();
        }
        return objectParser;
    }
}
