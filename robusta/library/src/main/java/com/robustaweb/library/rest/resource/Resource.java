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
package com.robustaweb.library.rest.resource;

import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.rest.representation.Representation;

import java.util.HashMap;

/**
 * In Rest, everything is a Resource. In Robusta Web Library, a Resource will typically be Domain objects stored in database,
 * whom representation will be set in XML or JSON
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public interface Resource<IdType> {

    /**
     * available for int on 11 bits on the database
     */
    public static final Long UNKNOWN_ID = -99999L;

    /**
     * Returns the id of the Resource.
     * <p>
     * In REST, a Resource might have an id as a Number, UUID String, CompositeKey or even an url
     * </p>
     * @return the Resource id
     */
    public IdType getId();

    /**
     * @todo2 : change to Long object
     */
    public void setId(IdType id);




    /**
     * Name of the prefix used to represent a resource.
     * For exemple, if the getPrefix() returns "house" and getListPrefix() returns "houses" for a resource House, a ResourceList<House>.getRepresentation() will return :
     * <houses>
     *  <house><id>1</id><name>House 1</name></house>
     *  <house><id>2</id><name>Small House</name></house>
     *  <house><id>3</id><name>Big House</name></house>
     * </houses>
     * Or : {houses:[{id:"1", name:"House 1"}, {id:"2", name:"Small House"}, {id:"3", name:"Big House"}]}
     */
    public String getPrefix();

    /**
     * Name of the prefix used to represent a list of this resource.
     * For exemple, if the function returns "houses" for a resource House, a ResourceList<House>.getRepresentation() will return :
     * <houses>
     *  <house><id>1</id><name>House 1</name></house>
     *  <house><id>2</id><name>Small House</name></house>
     *  <house><id>3</id><name>Big House</name></house>
     * </houses>
     * Or : {houses:[{id:"1", name:"House 1"}, {id:"2", name:"Small House"}, {id:"3", name:"Big House"}]}
     */
    public String getListPrefix();


    /**
     * Serialize this Resource
     * @return all name/values of this object.
     * @throws RepresentationException
     */
    public HashMap<String, Object> serialize() throws RepresentationException;

    /**
     * Return default representation
     * @return a default representation for this Resource
     */
    public Representation getRepresentation();


}
