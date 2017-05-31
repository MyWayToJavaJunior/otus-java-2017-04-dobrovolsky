package ru.otus.dobrovolsky.reflect.parser.parserFactory;

import ru.otus.dobrovolsky.reflect.parser.parserToJson.*;
import ru.otus.dobrovolsky.reflect.parser.parserToString.*;

public class ParserFactory {
    private ParserFactory() {
    }

    public static ArrayToStringParser getArrayToStringParser() {
        return new ArrayToStringParser();
    }

    public static PrimitiveToStringParser getPrimitiveToStringParser() {
        return new PrimitiveToStringParser();
    }

    public static ObjectToStringParser getObjectToStringParser() {
        return new ObjectToStringParser();
    }

    public static CollectionToStringParser getCollectionToStringParser() {
        return new CollectionToStringParser();
    }

    public static MapToStringParser getMapToStringParser() {
        return new MapToStringParser();
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
