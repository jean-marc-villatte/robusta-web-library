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
package com.robustaweb.library.gwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;
import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.exception.XmlException;
import com.robustaweb.library.commons.util.*;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.representation.XmlDocumentRepresentation;
import com.robustaweb.library.rest.resource.Resource;
import com.robustaweb.library.rest.resource.ResourceList;

/**
 * todo1 : rename into GwtXmlRepresentation, and work on GwtJsonRepresentation
 * 
 * @author Nicolas Zozol
 */
public class GwtRepresentation implements
		XmlDocumentRepresentation<Document, Element> {

	Document document;
	 Date lastModified;
	
	/**
	 * Create an empty document with a <root> element
	 */
	public GwtRepresentation() {
		this.document = XMLParser.parse("<root></root>");
		assert this.document != null;
	}

	/**
	 * Creates a GWT XmlRepresentation from Xml string
	 * 
	 * @param xml
	 *            Xml String
	 */
	public GwtRepresentation(String xml) {

		this.document = createDocument(xml);
		assert this.document != null;
	}



	/**
	 * Creates an Xml Representation from nodes and values
	 * 
	 * @param rootName
	 * @param serialization
	 *            Alternate nodeNames and their values of Xml document
	 */
	public GwtRepresentation(String rootName,
			CoupleList<String, Object> serialization) {
		this(XmlUtils.build(null, rootName,serialization));
		assert this.document != null;
	}

	/**
	 * Creates a basic Xml Representation from a Resource
	 * 
	 * @param resource
	 *            the resource
	 */
	public GwtRepresentation(Resource resource) {
		this(XmlUtils.build(null, resource));
		assert this.document != null;
	}

	/**
	 * Creates the GWT XML Document
	 * 
	 * @param xml
	 * @return
	 */
	private Document createDocument(String xml) {
		this.document = XMLParser.parse(xml);
		return document;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public String toString() {
		return this.getRootElement().toString();
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public Document getDocument() throws XmlException {
		return this.document;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public Element getRootElement() throws XmlException {

		NodeList nodeList = this.document.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node n = nodeList.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element root = (Element) n;
				return root;
			}
		}

		throw new XmlException("Can't find any Root Element");
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public Element getElement(String nodeName) throws XmlException {
		Element elt = getOptionalElement(nodeName);
		if (elt == null) {
			throw new XmlException("Can't find element : " + nodeName);
		} else {
			return elt;
		}
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public List<Element> getElements(String nodeName) throws XmlException {

		ArrayList<Element> elements = new ArrayList<Element>();
		NodeList list = this.document.getElementsByTagName(nodeName);
		if (list != null) {
			for (int i = 0; i < list.getLength(); i++) {
				elements.add((Element) list.item(i));
			}
		}
		return elements;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public String getElementRepresentation(Element element) throws XmlException {
		return element.toString();
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public GwtRepresentation add(String nodeName, String nodeValue)
			throws XmlException {
		Element root = this.document.getDocumentElement();
		if (root == null) {
			throw new XmlException("The document has no Root Element");
		}

		Text textValue = this.document.createTextNode(nodeValue);
		Element elt = this.document.createElement(nodeName);
		elt.appendChild(textValue);
		root.appendChild(elt);
		return this;
	}


    @Override
    public Representation add(Resource resource, boolean eager) {
        //TODO Defualt implementation
        return null;
    }

    @Override
    public Representation addAll(ResourceList resources, boolean eager) {
        //TODO Defualt implementation
        return null;
    }

    /**
	 * {@inheritDoc }
	 */
	@Override
	public GwtRepresentation remove(String nodeName) throws XmlException {

		Element elt = this.getElement(nodeName);
		elt.getParentNode().removeChild(elt);
		return this;
	}

    @Override
    public Representation fetch(String nodeName) {
        //TODO Defualt implementation
        return null;
    }

    /**
	 * {@inheritDoc }
	 */
	@Override
	public String getAttribute(String nodeName, String attribute)
			throws XmlException {
		Element elt = getElement(nodeName);
		return elt.getAttribute(attribute);
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public XmlDocumentRepresentation setAttribute(String nodeName,
			String attribute, String value) throws XmlException {
		Element elt = getElement(nodeName);
		elt.setAttribute(attribute, value);
		return this;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public CoupleList<String, String> getCouples(String leftNodeName,
			String rightNodeName) {
		if (leftNodeName.equals(rightNodeName)) {
			throw new IllegalArgumentException(
					"Can't find couples with the same nodeName");
		}
		List<String> leftValues = getValues(leftNodeName);
		List<String> rightValues = getValues(rightNodeName);

		if (leftValues == null || rightValues == null) {
			throw new XmlException("Error : leftValues or rightValues is null");
		}
		if (leftValues.size() != rightValues.size()) {
			throw new XmlException(
					"Thee is a different ammount of leftValeus compare to rightValues");
		}

		CoupleList<String, String> couples = new CoupleList<String, String>();
		for (int i = 0; i < leftValues.size(); i++) {
			Couple<String, String> c = new Couple<String, String>(leftValues
					.get(i), rightValues.get(i));
			couples.add(c);
		}
		return couples;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public String get(String nodeName) throws RepresentationException {

		String val = getOptionalValue(nodeName);
		if (val == null) {
			throw new XmlException("Can't find tag " + nodeName
					+ " in document");
		} else {
			return val;
		}

	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public GwtRepresentation set(String nodeName, String value) {
		Element elt = getOptionalElement(nodeName);
		if (elt == null) {
			this.add(nodeName, value);
		} else {
			elt.setNodeValue(value);
		}
		return this;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public Long getNumber(String nodeName) throws RepresentationException,
			NumberFormatException {
		String val = get(nodeName);
		return new Long(val.trim());
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public <T extends Number> T getNumber(String nodeName, T exemple)
			throws RepresentationException, NumberFormatException {
		String s = get(nodeName);
		s = s.trim();
		T result = MathUtils.convert(s, exemple);
		return result;

	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public String getOptionalValue(String nodeName) {
		Element e = getOptionalElement(nodeName);
		if (e == null) {
			return null;
		} else {
			return getValue(e);
		}
	}

	private String getValue(Element e) {
		assert e != null : "element can't be null";
		if (e.getFirstChild() != null) {
			String value = e.getFirstChild().getNodeValue();
			if (value != null) {
				return value;
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public List<String> getValues(String nodeName)
			throws RepresentationException {
		List<Element> elements = getElements(nodeName);
		List<String> result = new ArrayList<String>();
		for (Element e : elements) {
			result.add(getValue(e));
		}
		return result;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public List<Long> getNumbers(String nodeName)
			throws RepresentationException, NumberFormatException {
		List<String> strValues = getValues(nodeName);
		ArrayList<Long> numbers = new ArrayList<Long>();
		for (String s : strValues) {
			numbers.add(new Long(s));
		}
		return numbers;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public <T extends Number> List<T> getNumbers(String nodeName, T exemple)
			throws RepresentationException, NumberFormatException {
		List<String> strValues = getValues(nodeName);
		ArrayList<T> numbers = new ArrayList<T>();
		for (String s : strValues) {
			numbers.add(MathUtils.convert(s, exemple));
		}
		return numbers;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public GwtRepresentation copy() {
		return new GwtRepresentation(this.toString());
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public GwtRepresentation getRepresentation(Object newObject) {
        String prefix;
        if (newObject instanceof Resource){
            prefix = ((Resource) newObject).getPrefix();
        }else{
            prefix = "root";
        }

        GwtRepresentation representation = new GwtRepresentation(prefix,  new CoupleList(SerializationUtils.serialize(newObject)));
        return representation;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override
	public Element getOptionalElement(String nodeName) {
		try {
			Node n = this.document.getElementsByTagName(nodeName).item(0);
			return (Element) n;
		} catch (Exception ex) {
			return null;
		}
	}



	/**
	 * {@inheritDoc }
	 */
	@Override
	public GwtRepresentation addAll(String nodeName, String listName,
                                 List<Object> values) {
		Element root = this.document.getDocumentElement();
		if (root == null) {
			throw new XmlException("The document has no Root Element");
		}

		Element list = this.document.createElement(listName);
		root.appendChild(list);

		for (Object o : values) {
			Element e = this.document.createElement(nodeName);
			list.appendChild(e);
			Text textValue = this.document.createTextNode(o.toString());
			e.appendChild(textValue);			
		}
		
		return this;
	}

	/**
     * TODO : incorrect implementation
	 * {@inheritDoc }
	 */
	//@Override
	public GwtRepresentation addAll(ResourceList resources, String prefixIfListIsEmpty) {
		if (resources == null){
			throw new IllegalArgumentException("Resources is null");
		}
		Element root = this.document.getDocumentElement();
		if (root == null) {
			throw new XmlException("The document has no Root Element");
		}
		
		if (resources.isEmpty()){
			Element emptyListElement = this.document.createElement(prefixIfListIsEmpty) ;
			root.appendChild(emptyListElement);
		}else{
			Resource r0 = resources.get(0);
			Element list = this.document.createElement(r0.getListPrefix());
			root.appendChild(list);
			for (Object r :  resources){
				
				Element elt = this.document.createElement(r0.getPrefix());
				list.appendChild(elt);
				
				Element id=this.document.createElement("id");
				elt.appendChild(id);
				id.appendChild(document.createTextNode((((Resource)r).getId().toString())));
								
				Element show = this.document.createElement("show");
				elt.appendChild(show);
				show.appendChild(document.createTextNode(r.toString()));
			}						
		}
		return this;		
	}

	


	/**
	 * {@inheritDoc }
	 */
	public Date getLastModified() {
		return lastModified;
	}


	/**
	 * {@inheritDoc }
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}


    public String emptyValue="";

    @Override
    public void setEmptyValue(String value) {
        this.emptyValue = value;
    }

    @Override
    public String getEmptyValue() {
        return this.emptyValue;
    }


}
