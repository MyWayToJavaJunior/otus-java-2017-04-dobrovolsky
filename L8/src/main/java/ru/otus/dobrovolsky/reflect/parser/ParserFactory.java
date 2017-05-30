package ru.otus.dobrovolsky.reflect.parser;

public class ParserFactory {
    private ParserFactory() {
    }

    public static ArrayParser getArrayParser() {
        return new ArrayParser();
    }

    public static PrimitiveParser getPrimitiveParser() {
        return new PrimitiveParser();
    }

    public static ObjectParser getObjectParser() {
        return new ObjectParser();
    }

    public static CollectionParser getCollectionParser() {
        return new CollectionParser();
    }

    public static MapParser getMapParser() {
        return new MapParser();
    }
}
