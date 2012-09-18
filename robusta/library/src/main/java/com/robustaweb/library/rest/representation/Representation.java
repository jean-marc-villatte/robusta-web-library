/*
 * Copyright 2007-2011 Nicolas Zozol
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robustaweb.library.rest.representation;

import java.util.Date;
import java.util.List;

import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.rest.resource.Resource;
import com.robustaweb.library.rest.resource.ResourceList;

/**
 * This interfaces explains how a Resource can be represented. A Representatation object contains the data structures AND methods to change it.
 * A constructor should be able to accept a Resource object and built a default representation
 * @author n.zozol
 */
public interface Representation {

    /**
     * Returns the representation usually written in http tubes
     * @return
     */
    @Override
    public String toString();

    /**
     * Returns a node value, or throw a RepresentationException if it's not found, or if it's not a String
     * @param nodeName name of the searched node
     * @return the node value
     * @throws RepresentationException if the nodeName is not found
     */
    public String get(String nodeName) throws RepresentationException;

    /**
     * Returns true if the Representation has the nodeName
     * @param nodeName
     * @return
     */
    public boolean has(String nodeName);

    /**
     * Update a node with its value, or create a new node
     * @param nodeName name of the node
     * @param value value set
     */
    public Representation set(String nodeName, String value);

    /**
     * TODO : Not Json complient. Document has no need here. Not good for SAX neither
     * Returns the underlying structure, or a String representation
     * @return
     */
    public Object getDocument() ;

    /**
     * Returns a number in the node as a Long. Throws a RepresentationException if the node is not found,
     * or a NumberFormatException if the value can't be cast as a Long
     * @param nodeName
     */
    public Long getNumber(String nodeName) throws RepresentationException, NumberFormatException;

    /**
     *  Returns a number in the node, that will have the same &lt;T> type as exemple. Throws a RepresentationException if the node is not found,
     * or a NumberFormatException if the value can't be cast as a Long<br/>
     * Exemple :
     * <pre>
     * float asFloat = 0f;
     * float result = representation.getNumber("income", asFloat);
     * assert result == 12.05f;
     * </pre>
     * The available Types Number are Double, Float, Integer, Long, Short or Byte. Other will throw an IllegalArgumentException.
     * @param <T> exact type of the Number
     * @param nodeName
     * @param exemple
     * @return
     * @throws RepresentationException
     * @throws NumberFormatException
     */
    public <T extends Number> T getNumber(String nodeName, T exemple) throws RepresentationException, NumberFormatException;


    /**
     * 
     * @param nodeName
     */
    public List<String> getValues(String nodeName) throws RepresentationException;

    /**
     *
     * @param nodeName
     */
    public List<Long> getNumbers(String nodeName) throws RepresentationException, NumberFormatException;

    
    public <T extends Number> List<T> getNumbers(String nodeName, T exemple) throws RepresentationException, NumberFormatException;

    /**
     * Add a new element, even if one of the same nodeName exists or a value to a Json array.
     * @param nodeName
     * @param nodeValue
     * @return the updated representation
     */
    public Representation add(String nodeName, String nodeValue);

    /**
     * Add a new element, even if one of the same nodeName exists or a value to a Json array.
     * @param nodeName
     * @param nodeValue
     * @return the updated representation
     */
    public Representation add(Resource resource, boolean eager);


    public Representation addAll(ResourceList resources, boolean eager);




    /**
     * Removes the <strong>first</strong>  element found with this nodeName, or the <strong>last</strong> element of a JSON array
     * @param nodeName
     * @return the new representation
     * @throws RepresentationException if the struncture is irrelevant for the removal
     */
    public Representation remove(String nodeName) throws RepresentationException;

    public Representation fetch(String nodeName);

    /**
     * Overrides the copy() method. Each descending elements of the new structure must have differents memory adresses.
     * @return
     */
    public Representation copy();


    /**
     * Returns a new Representation object of newObject. This current Representation object is NOT affected.
     * @return  a Representation of newObject
     */
    public Representation getRepresentation(Object newObject);




    public void setEmptyValue(String value);
    public String getEmptyValue();



}
