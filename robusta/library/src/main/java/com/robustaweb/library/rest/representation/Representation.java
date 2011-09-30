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
import com.robustaweb.library.commons.util.CoupleList;
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
     * Returns a node value, or throw a RepresentationException if it's not found
     * @param nodeName name of the searched node
     * @return the node value
     * @throws RepresentationException if the nodeName is not found
     * @see Representation#getOptionalValue(java.lang.String) 
     */
    public String get(String nodeName) throws RepresentationException;

    /**
     * Update a node with its value, or create a new node
     * @param nodeName name of the node
     * @param value value set
     */
    public Representation set(String nodeName, String value);

    /**
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
     * Returns a node value, or null if it's not found
     * @param nodeName name of the searched node
     * @return the node value or null
     */
    public String getOptionalValue(String nodeName);

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
     * Add a list of objects to the Representation. A Teacher Representation can embbed a list of objects even if the 
     * Teacher Java object does not contains explicitely some attribute slots.
     * Note that nodeName is not necessary in, for exemple, a Json Representation. The method contract is essentialy based on 
     * retrieving the values.
     * If list is empty, an empty node <strong>is</strong> created.
     * @param listName
     * @param nodeName
     * @param values
     * @return the updated representation
     */
    public Representation addList(String listName, String nodeName, List<Object>values);

    /**
     * Add a list of resources to the Representation. A Teacher Representation can embbed a List&lt;Student> even if
     * the Java Teacher object does NOT contain a List &lt;Student>students attribute.
     * The Representation will add id <strong>AND</strong> a toString representation of the Resource.
     * If list is empty, an empty node <strong>is</strong> created.
     * @param resources
     * @return the updated Representation
     */
    public Representation addList(ResourceList resources, String prefixIfListIsEmpty);
    
    
    /**
     * Removes the <strong>first</strong>  element found with this nodeName, or the <strong>last</strong> element of a JSON array
     * @param nodeName
     * @return the new representation
     * @throws RepresentationException if the struncture is irrelevant for the removal
     */
    public Representation remove(String nodeName) throws RepresentationException;

    /**
     * Overrides the copy() method. Each descending elements of the new structure must have differents memory adresses.
     * @return
     */
    public Representation copy();


    /**
     * Returns a new empty representation . This current object is NOT affected.
     * @return  a new empty representation
     */
    public Representation reset();

    /**
     * Construct a Representation from a resource. This method should directly call a constructor.
     * @param resource
     * @return  a Representation of the resource
     */
    public Representation construct(Resource resource);

    /**
     * Construct a Representation from a alternate property/value array. This method should directly call a constructor.
     * @param prefix prefix of the representation
     * @param nodesAndValues alternate property/value array, such as "id", 12, "name", "John Doe"
     * @return
     */
    public Representation construct(String prefix, CoupleList<String, Object> nodesAndValues);
    
    /**
     * Etags are Representation Specific ; 
     * By default, returns this MD5 of the representation, wich may take some time for heavy representations.
     * If you use seriously Etags, you should overwrite this method. hashcode() method may also return your Etag value.  
     * @return EtagValue
     */
    public String getEtagValue();
    
    public Date getLastModified();
    
    public void setLastModified(Date date);
}