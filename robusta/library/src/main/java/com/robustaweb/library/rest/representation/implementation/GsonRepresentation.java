package com.robustaweb.library.rest.representation.implementation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.util.Couple;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.rest.representation.JsonRepresentation;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.resource.Resource;
import com.robustaweb.library.rest.resource.ResourceList;

import java.util.Date;
import java.util.List;

/**
 * Created by Nicolas Zozol
 * Date: 12/08/12
 */
public class GsonRepresentation extends AbstractJsonRepresentation<JsonElement> {


    Gson gson = new Gson();
    JsonElement document;

    /**
     * In that case, is serialization if not null, it's always a JsonObject
     * @param serialization
     */
    public GsonRepresentation(CoupleList<String, Object>  serialization) {


        String str = gson.toJson(serialization);
        this.document = gson.toJsonTree(serialization.getHashMap());

    }

    @Override
    public GsonRepresentation getObject(String nodeName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isObject() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isBoolean() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isString() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isNumber() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isArray() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isNull() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JsonType getTypeof() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String get(String nodeName) throws RepresentationException {

     return null;
    }

    @Override
    public Representation set(String nodeName, String value) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public JsonElement getDocument() {
        return this.document;
    }

    @Override
    public Long getNumber(String nodeName) throws RepresentationException, NumberFormatException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T extends Number> T getNumber(String nodeName, T exemple) throws RepresentationException, NumberFormatException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getOptionalValue(String nodeName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getValues(String nodeName) throws RepresentationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Long> getNumbers(String nodeName) throws RepresentationException, NumberFormatException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T extends Number> List<T> getNumbers(String nodeName, T exemple) throws RepresentationException, NumberFormatException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Representation add(String nodeName, String nodeValue) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Representation addList(String listName, String nodeName, List<Object> values) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Representation addList(ResourceList resources, String prefixIfListIsEmpty) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Representation remove(String nodeName) throws RepresentationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Representation fetch(String nodeName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Representation copy() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Representation reset() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Representation construct(Resource resource) {
        CoupleList<String, Object> serialization;
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Representation construct(String prefix, CoupleList<String, Object> serialization) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getEtagValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getLastModified() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setLastModified(Date date) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setEmptyValue(String value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getEmptyValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
