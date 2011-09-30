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

import java.util.List;

import com.robustaweb.library.commons.exception.XmlException;

/**
 * TODO : document methods
 * Add some capabilities
 * @author n.zozol
 */
public interface XmlDocumentRepresentation<D, E> extends XmlRepresentation{

    @Override
    public D getDocument() throws XmlException;
    public E getRootElement() throws XmlException;
    public E getElement(String nodeName) throws XmlException;
    public E getOptionalElement(String nodeName);
    public List<E> getElements(String nodeName) throws XmlException;
    public String getElementRepresentation(E element) throws XmlException;
   
}
