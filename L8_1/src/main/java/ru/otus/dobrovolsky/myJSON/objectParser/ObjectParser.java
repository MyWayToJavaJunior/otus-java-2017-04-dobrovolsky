package ru.otus.dobrovolsky.myJSON.objectParser;

import org.json.simple.JSONAware;

public interface ObjectParser {
    JSONAware parse(Object object);
}
