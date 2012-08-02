package com.robustaweb.library.rest.representation;

/**
 * User: Nicolas
 * Date: 24/07/12
 * Time: 21:48
 */
public interface JsonRepresentation<JsonObject> extends Representation {


    enum JsonType{
        OBJECT, BOOLEAN, STRING, NUMBER, ARRAY, NULL;
    }

    public JsonRepresentation getObject(String nodeName);

    public boolean isObject();
    public boolean isBoolean();
    public boolean isString();
    public boolean isNumber();
    public boolean isArray();
    public boolean isNull();
    public JsonType getTypeof();



}

