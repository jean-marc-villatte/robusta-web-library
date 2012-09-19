package com.robustaweb.library.rest.representation.implementation;


import com.robustaweb.library.commons.MyRobusta;
import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.commons.util.MathUtils;
import com.robustaweb.library.commons.util.SerializationUtils;
import com.robustaweb.library.rest.representation.JsonRepresentation;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.resource.Resource;
import com.robustaweb.library.rest.resource.ResourceList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: Nicolas
 * Date: 24/07/12
 * Time: 22:03
 */
public class JsonSimpleRepresentation implements JsonRepresentation<Object> {


    Object json;
    JSONParser parser = new JSONParser();


    private JsonSimpleRepresentation(Object object) {
        //Primitives
        if (object instanceof Boolean || object instanceof Number || object instanceof String || object == null) {
            this.json = object;
        }
        //Array
        else if (object instanceof JSONArray) {
            this.json = object;
        }
        else if (object instanceof Iterable) {
            Iterable iterable = (Iterable) object;
            JSONArray array = new JSONArray();
            for (Object item : iterable) {
                array.add(SerializationUtils.getSimpleValue(item));
            }
            this.json = array;
        }
        //Objects
        else if (object instanceof JSONObject){
            this.json = object;
        }
        else if (object instanceof Resource){
            Representation rep = ((Resource) object).getRepresentation();
            if (rep instanceof JsonSimpleRepresentation){
                this.json =((JsonSimpleRepresentation)rep).json;
            }
        }
        else{
            HashMap<String, Object> map = SerializationUtils.serialize(object);
            //TODO : check here for different strategies instead of calling twice
            map = SerializationUtils.simplify(map);
            this.json = new JSONObject(map);
        }
    }




    public JsonSimpleRepresentation(String string, boolean parse) throws RepresentationException {

        if (string == null) {
            throw new IllegalArgumentException("Can't create a JsonObject from null");
        }
        if (parse){
            try {
                this.json = parser.parse(string);
            } catch (ParseException e) {
                MyRobusta.error("Paring error with SimpleJsonRepresentation. Most server side Json Parser are more strict than Browsers and left keys MUST be wrapped with double quotes");
                throw new RepresentationException(e);
            }
        }else{

        }

    }

    public JsonSimpleRepresentation(CoupleList<String, Object> serialization) {
        this(new JSONObject(serialization.getHashMap()));
    }

    @Override
    public JsonSimpleRepresentation getObject(String nodeName) {
        return null;
    }

    private JSONObject toObject() {
        if (this.isObject()) {
            return (JSONObject) this.json;
        } else {
            throw new RepresentationException(this + " is not JsonObject");
        }
    }


    @Override
    public String get(String nodeName) throws RepresentationException {
        Object result = toObject().get(nodeName);
        return result == null ? this.getEmptyValue() : result.toString();
    }

    @Override
    public JsonSimpleRepresentation set(String nodeName, String value) {
        this.toObject().put(nodeName, value);
        return this;
    }

    @Override
    public boolean has(String nodeName) {
        if (nodeName == null || nodeName.isEmpty()) {
            return false;
        }
        try {
            return toObject().containsKey(nodeName);
        } catch (RepresentationException rex) {
            return false;
        }

    }

    @Override
    public Object getDocument() {
        return this.json;
    }

    @Override
    public Long getNumber(String nodeName) throws RepresentationException, NumberFormatException {
        String value = get(nodeName);
        return MathUtils.convert(value, 1L);
    }

    @Override
    public <T extends Number> T getNumber(String nodeName, T exemple) throws RepresentationException, NumberFormatException {
        String s = get(nodeName);
        if (s == null) {
            throw new RepresentationException("No value for node " + nodeName + " ; it's imposible to make it a Long");
        }
        s = s.trim();
        T result = MathUtils.convert(s, exemple);
        return result;
    }


    JSONArray toArray() {
        if (!isArray()) {
            throw new RepresentationException(this + "is not an array");
        }
        return (JSONArray) this.json;
    }

    /**
     * ex : {human : 'jack',  ids : [1,3,7]}
     * The method returns [1,3,7]
     *
     * @param nodeName
     * @return
     */
    JSONArray toArray(String nodeName) {
        Object obj = toObject().get(nodeName);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        } else {
            throw new RepresentationException(this + " has not " + nodeName + " as an array");
        }
    }

    @Override
    public List<String> getValues(String nodeName) throws RepresentationException {

        //the current object MUST be an array
        if (!(json instanceof JSONArray)) {
            throw new RepresentationException("The current object must be a JSONArray. The method will pick '" + nodeName + "' values on each object in the array");
        }

        List<String> objects = new ArrayList<String>();

        JSONArray array = ((JSONArray) json);
        for (Object o : array) {
            if (!(o instanceof JSONObject)) {
                throw new RepresentationException("Every object in the array MUST be a JsonObject");
            } else {
                JSONObject obj = (JSONObject) o;
                String value = (String) obj.get(nodeName);
                objects.add(String.valueOf(value));
            }
        }

        return objects;
    }


    @Override
    public List<Long> getNumbers(String nodeName) throws RepresentationException, NumberFormatException {
        //the current object MUST be an array
        if (!(json instanceof JSONArray)) {
            throw new RepresentationException("The current object must be a JSONArray. The method will pick '" + nodeName + "' values on each object in the array");
        }

        List<Long> numbers = new ArrayList<Long>();

        JSONArray array = ((JSONArray) json);
        for (Object o : array) {
            if (!(o instanceof JSONObject)) {
                throw new RepresentationException("Every object in the array MUST be a JsonObject");
            } else {
                JSONObject obj = (JSONObject) o;
                numbers.add((Long) obj.get(nodeName));
            }
        }
        return numbers;
    }

    @Override
    public <T extends Number> List<T> getNumbers(String nodeName, T exemple) throws RepresentationException, NumberFormatException {
        JSONArray array = toArray(nodeName);
        List<T> list = new ArrayList<T>(array.size());
        for (Object o : array) {
            list.add(MathUtils.convert(o.toString().trim(), exemple));
        }
        return list;
    }

    @Override
    public JsonSimpleRepresentation add(String nodeName, String nodeValue) {
        toObject().put(nodeName, nodeValue);
        return this;
    }

    @Override
    public JsonSimpleRepresentation add(Resource resource, boolean eager) {
        if (resource == null) {
            return this;
        }
        Object value = eager ? resource.getRepresentation() : resource.getId();
        toObject().put(resource.getPrefix(), value);
        return this;
    }


    @Override
    public JsonSimpleRepresentation addAll(String listName, List values) {
        JSONArray array = new JSONArray();
        array.addAll(values);
        toObject().put(listName, array);
        return this;
    }


    @Override
    public JsonSimpleRepresentation addAll(ResourceList resources, boolean eager) {
        if (resources == null || resources.isEmpty()) {
            return this;
        }

        String listName = resources.get(0).getListPrefix();

        JSONArray array = new JSONArray();
        for (int i = 0; i < resources.size(); i++) {
            Resource resource = resources.get(i);
            Object value = eager ? resource.getRepresentation() : resource.getId();
            array.add(value);
        }
        toObject().put(listName, array);
        return this;
    }

    @Override
    public Representation remove(String nodeName) throws RepresentationException {
        toObject().remove(nodeName);
        return this;
    }

    @Override
    public JsonSimpleRepresentation fetch(String nodeName) {
        Object newJson = toObject().get(nodeName);
        return new JsonSimpleRepresentation(newJson);
    }

    @Override
    public JsonSimpleRepresentation copy() {
        return new JsonSimpleRepresentation(((JSONObject) json).clone());
    }


    @Override
    public List<String> getValuesFromArray() throws RepresentationException {
        JSONArray array = toArray();
        List<String> result = new ArrayList<String>();
        for (Object o : array) {
            String val = o == null ? getEmptyValue() : o.toString();
            result.add(val);
        }
        return result;
    }

    @Override
    public List<Long> getNumbersFromArray() throws RepresentationException, NumberFormatException {
        JSONArray array = toArray();
        List<Long> result = new ArrayList<Long>();
        for (Object o : array) {
            if (o == null || !(o instanceof Number)) {
                throw new NumberFormatException(o + " is not a Number");
            }
            result.add((Long) o);
        }
        return result;
    }

    @Override
    public <T extends Number> List<T> getNumbersFromArray(T exemple) throws RepresentationException, NumberFormatException {
        JSONArray array = toArray();
        List<T> result = new ArrayList<T>();
        for (Object o : array) {
            if (o == null || !(o instanceof Number)) {
                throw new NumberFormatException(o + " is not a Number");
            }
            result.add((T) o);
        }
        return result;
    }

    @Override
    public List<String> pluck(String key) throws RepresentationException {
        JSONArray array = this.toArray();
        List<String> result = new ArrayList<String>();

        for (int i = 0; i < array.size(); i++) {
            Object o = array.get(i);
            JSONObject object;
            try {
                object = (JSONObject) o;
            } catch (Exception e) {
                throw new RepresentationException("The pluck() method require that all members of the JsonArray are a JSONObject. Object " + o + " is not (index " + i + ").");
            }

            if (object.containsKey(key)) {
                Object pluckItem = object.get(key);
                if (pluckItem instanceof String) {
                    result.add((String) pluckItem);
                } else {
                    throw new RepresentationException("The current object " + object + " contains the '" + key + "' key, but result :'" + pluckItem + "' is not a String (index " + i + ").");
                }
            } else {
                throw new RepresentationException("The current object " + object + " does not contains the '" + key + "' key needed for pluck() method (index " + i + ").");
            }
        }

        return result;
    }

    @Override
    public List<Long> pluckNumbers(String key) throws RepresentationException, NumberFormatException {
        JSONArray array = this.toArray();
        List<Long> result = new ArrayList<Long>();

        for (int i = 0; i < array.size(); i++) {
            Object o = array.get(i);
            JSONObject object;
            try {
                object = (JSONObject) o;
            } catch (Exception e) {
                throw new RepresentationException("The pluck() method require that all members of the JsonArray are a JSONObject. Object " + o + " is not (index " + i + ").");
            }

            if (object.containsKey(key)) {
                Object pluckItem = object.get(key);
                if (pluckItem instanceof Long) {
                    result.add((Long) pluckItem);
                } else {
                    throw new RepresentationException("The current object " + object + " contains the '" + key + "' key, but result :'" + pluckItem + "' is not a Long (index " + i + ").");
                }
            } else {
                throw new RepresentationException("The current object " + object + " does not contains the '" + key + "' key needed for pluck() method (index " + i + ").");
            }
        }

        return result;
    }

    @Override
    public <T extends Number> List<T> pluckNumbers(String key, T exemple) throws RepresentationException, NumberFormatException {
        JSONArray array = this.toArray();
        List<T> result = new ArrayList<T>();

        for (int i = 0; i < array.size(); i++) {
            Object o = array.get(i);
            JSONObject object;
            try {
                object = (JSONObject) o;
            } catch (Exception e) {
                throw new RepresentationException("The pluck() method require that all members of the JsonArray are a JSONObject. Object " + o + " is not (index " + i + ").");
            }

            if (object.containsKey(key)) {
                Object pluckItem = object.get(key);
                if (pluckItem instanceof Number) { //TODO : when we will use the Class variable, this will be more precise
                    result.add((T) pluckItem);
                } else {
                    throw new RepresentationException("The current object " + object + " contains the '" + key + "' key, but result :'" + pluckItem + "' is not a Number (index " + i + ").");//TODO : when we will use the Class variable, Number will be more precise
                }
            } else {
                throw new RepresentationException("The current object " + object + " does not contains the '" + key + "' key needed for pluck() method (index " + i + ").");
            }
        }

        return result;
    }

    @Override
    public JsonSimpleRepresentation getRepresentation(Object newObject) {
        return new JsonSimpleRepresentation(newObject);
    }


    public String emptyValue = "";

    @Override
    public void setEmptyValue(String value) {
        this.emptyValue = value;
    }

    @Override
    public String getEmptyValue() {
        return this.emptyValue;
    }

    @Override
    public JsonType getTypeof() {
        if (this.json == null) {
            return JsonType.NULL;
        } else if (this.json instanceof String) {
            return JsonType.STRING;
        } else if (this.json instanceof Boolean) {
            return JsonType.BOOLEAN;
        } else if (this.json instanceof JSONArray) {
            return JsonType.ARRAY;
        } else if (this.json instanceof JSONObject) {
            return JsonType.OBJECT;
        } else if (this.json instanceof Number) {
            return JsonType.NUMBER;
        } else {
            throw new RepresentationException("Can't find a correct Json type for " + this.json);
        }
    }

    @Override
    public boolean isObject() {
        return (this.getTypeof() == JsonType.OBJECT);
    }

    @Override
    public boolean isBoolean() {
        return (this.getTypeof() == JsonType.BOOLEAN);
    }

    @Override
    public boolean isString() {
        return (this.getTypeof() == JsonType.STRING);
    }

    @Override
    public boolean isNumber() {
        return (this.getTypeof() == JsonType.NUMBER);
    }

    @Override
    public boolean isArray() {
        return (this.getTypeof() == JsonType.ARRAY);
    }

    @Override
    public boolean isNull() {
        return (this.getTypeof() == JsonType.NULL);
    }

    public boolean isPrimitive() {
        return this.isString() || this.isBoolean() || this.isNull() || this.isNumber();
    }


    @Override
    public String toString() {
        return String.valueOf(this.json);
    }

}
