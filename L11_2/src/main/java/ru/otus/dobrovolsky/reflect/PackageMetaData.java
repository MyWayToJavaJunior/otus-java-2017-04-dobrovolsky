package ru.otus.dobrovolsky.reflect;

import java.util.HashMap;
import java.util.Map;

public class PackageMetaData {
    private Map<Class, ClassMetaData> annotatedClassesMap = new HashMap<>();

    private static PackageMetaData instance;

    private PackageMetaData() {
    }

    public PackageMetaData getInstance() {
        if (instance == null) {
            instance = new PackageMetaData();
        }
        return instance;
    }


}
