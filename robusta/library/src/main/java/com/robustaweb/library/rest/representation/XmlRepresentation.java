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

import com.robustaweb.library.commons.exception.XmlException;
import com.robustaweb.library.commons.util.CoupleList;

import java.util.List;

/**
 * 
 * @author n.zozol
 */
public interface XmlRepresentation extends Representation {



    public static final String headers = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";


    String getAttribute(String attribute) throws XmlException;
    XmlRepresentation setAttribute(String attribute, String value) throws XmlException;
    boolean hasAttribute(String attribute);


    String getAttribute(String nodeName, String attribute) throws XmlException;
    XmlRepresentation setAttribute(String nodeName, String attribute, String value) throws XmlException;
    boolean hasAttribute(String nodeName, String attribute);


    /**
     * TODO: did not undesrtood
     * Grab all elements with leftNodeName, all elements with rightNodeName, and put them along to create
     * a CoupleList
     * Left and right nodeNames MUST be differents, and numbers of left nodes must equals numbers of right nodes
     * @param leftNodeName left Element
     * @param rightNodeName right Element
     * @deprecated
     */
    public CoupleList<String, String> getCouples(String leftNodeName, String rightNodeName);


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
     *
     */
    public XmlRepresentation addAll(String nodeName, String listName, List<Object> values);
    

}
