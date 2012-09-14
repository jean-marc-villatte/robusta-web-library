package com.robustaweb.library.rest.representation;

import com.robustaweb.library.commons.exception.RepresentationException;

import java.util.List;

/**
 * User: Nicolas
 * Date: 24/07/12
 * Time: 21:48
 */
public interface JsonRepresentation<JsonObject> extends Representation {


    enum JsonType{
        OBJECT, BOOLEAN, STRING, NUMBER, ARRAY, NULL, UNDEFINED;
    }

    @Deprecated
    public JsonRepresentation getObject(String nodeName);

    /**
     * The document can be directly a Json Array
     * @param nodeName
     * @throws RepresentationException if the current Representation is not a JsonArray
     */
    public List<String> getValuesFromArray() throws RepresentationException;

    /**
     * The document can be directly a Json Array
     * @param nodeName
     * @throws RepresentationException if the current Representation is not a JsonArray
     */
    public List<Long> getNumbersFromArray() throws RepresentationException, NumberFormatException;


    /**
     * The document can be directly a Json Array
     * @param exemple
     * @param <T>
     * @return
     * @throws RepresentationException if the current document is not a Document
     * @throws NumberFormatException
     * @throws RepresentationException if the current Representation is not a JsonArray
     */
    public <T extends Number> List<T> getNumbersFromArray(T exemple) throws RepresentationException, NumberFormatException;


    /**
     * Pluck extracting a list of property values.
     * <p>
     * From Underscore.js documentation :<br/>
     * var stooges = [{name : 'moe', age : 40}, {name : 'larry', age : 50}, {name : 'curly', age : 60}];<br/>
     * _.pluck(stooges, 'name');<br/>
     * => ["moe", "larry", "curly"]<br/>
     *</p>
     * @param nodeName
     * @throws RepresentationException if the current Representation is not a JsonArray
     */
    public List<String> pluck(String key) throws RepresentationException;

    /**
     * The document can be directly a Json Array
     * @param nodeName
     * @throws RepresentationException if the current Representation is not a JsonArray
     */
    public List<Long> pluckNumbers(String key) throws RepresentationException, NumberFormatException;


    /**
     * The document can be directly a Json Array
     * @param exemple
     * @param <T>
     * @return
     * @throws RepresentationException if the current document is not a Document
     * @throws NumberFormatException
     * @throws RepresentationException if the current Representation is not a JsonArray
     */
    public <T extends Number> List<T> pluckNumbers(String key, T exemple) throws RepresentationException, NumberFormatException;




    public boolean isObject();
    public boolean isBoolean();
    public boolean isString();
    public boolean isNumber();
    public boolean isArray();
    public boolean isNull();
    public JsonType getTypeof();



}

