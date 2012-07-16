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
package com.robustaweb.library.rest.representation.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.robustaweb.library.commons.util.*;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.commons.util.MathUtils;
import com.robustaweb.library.commons.util.SerializationUtils;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.exception.XmlException;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.representation.XmlDocumentRepresentation;
import com.robustaweb.library.rest.resource.Resource;
import com.robustaweb.library.rest.resource.ResourceList;
import com.robustaweb.library.security.implementation.MD5;

/**
 * JDOM based Representation implementation. It also contains a few methods to read/save xml
 * files in a file.
 * @author n.zozol
 */
public class JdomRepresentation implements XmlDocumentRepresentation<Document, Element>{

    Document document;
    /**
     * Optional file path where the document may be read/saved
     */
    String path;
    
    Date lastModified;

    /**
     * Create an empty document with a <root> element
     */
    public JdomRepresentation() {
        this.document = new Document(new Element("root"));
        assert this.document != null;
    }


    public JdomRepresentation(String xml) {
        this.document = createDocument(xml);
    }

    
    
    
    /**
     * 
     * @param rootName
     * @param serialization
     */
    public JdomRepresentation(String rootName, CoupleList<String, Object> serialization) {
        Element root = new Element(rootName);
        for (Couple<String, Object> couple : serialization){
            Element elt = new Element(couple.getLeft());
            elt.setText(couple.getRight().toString());
            root.addContent(elt);
        }
        this.document = new Document(root);
    }


    public JdomRepresentation(Resource resource){

    }

    private Document createDocument(String xml){
           SAXBuilder sax = new SAXBuilder();
           try {
               this.document = sax.build(new StringReader(xml));
           } catch (JDOMException ex) {
               throw new XmlException(ex);
           } catch (IOException ex) {
               throw new XmlException(ex);
           }
           return document;
       }


    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        String s = out.outputString(this.document);
        return s;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Element getRootElement() throws XmlException {
        assert (this.document != null);
        return this.document.getRootElement();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Element getOptionalElement(final String nodeName) {
         Iterator it = this.document.getDescendants(new Filter() {

            @Override
            public boolean matches(Object o) {
                if (o instanceof Element) {
                    if (((Element) o).getName().equalsIgnoreCase(nodeName.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }
        });

        if (it.hasNext()) {
            Object obj = it.next();
            assert obj instanceof Element ;
            return (Element) obj;
        }else{
            return null;
        }

        
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public Element getElement(final String nodeName) throws XmlException {

        Element elt = getOptionalElement(nodeName);
        if (elt == null){
            throw new XmlException("Can't find element "+nodeName);
        }else{
            return elt;
        }
       
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Element> getElements(final String nodeName) throws XmlException {

        List<Element> result = new ArrayList<Element>();
        Iterator it = this.document.getDescendants(new Filter() {

            @Override
            public boolean matches(Object o) {
                if (o instanceof Element) {
                    if (((Element) o).getName().equalsIgnoreCase(nodeName.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }
        });

        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof Element) {
                result.add((Element) obj);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getElementRepresentation(Element element) throws XmlException {
        //Creating a new document 
        Element detached = (Element) element.detach();
        Document doc = new Document(detached);
        //output
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        String s = out.outputString(doc);
        return s;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public JdomRepresentation add(String nodeName, String nodeValue) throws XmlException {
        Element element = new Element(nodeName, this.document.getRootElement().getNamespace());
        element.setText(nodeValue);
        this.document.getRootElement().addContent(element);
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public JdomRepresentation remove(String nodeName) throws XmlException {

        Iterator it = this.document.getDescendants();
        Element candidate = getElement(nodeName);
        assert candidate != null : "getElement should have throw an execption";
        candidate.getParent().removeContent(candidate);
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getAttribute(String nodeName, String attribute) throws XmlException {
        Element element = getElement(nodeName);
        return element.getAttributeValue(attribute);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public JdomRepresentation setAttribute(String nodeName, String attribute, String value) {
        Element element = getElement(nodeName);
        element.setAttribute(attribute, value);
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public CoupleList<String, String> getCouples(String leftNodeName, String rightNodeName) {
        List<String> left = getValues(leftNodeName);
        List<String> right = getValues(rightNodeName);

        if (left.size() != right.size()) {
            throw new XmlException(
                    "xml is not well designed for the getCouples method ; "
                    + "leftNodeName size is different from rightNodeName size");
        }

        CoupleList<String, String> couples = new CoupleList<String, String>();
        for (int i = 0; i < left.size(); i++) {
            couples.addCouple(left.get(i), right.get(i));
        }

        return couples;
    }

    /**
     * Returns the index of a content from its Parent point of view, and using JDOM specifications.
     *
     * @param parent
     * @param content
     * @return The index of the Content
     * @throws robusta.commons.exceptions.XmlException
     */
    public int getContentIndex(Element parent, Content content) throws XmlException {

        List contents = parent.getContent();
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i) == content) {
                return i;
            }
        }

        throw new XmlException("can't find content " + content.getValue() + " in element " + parent.getName());

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String get(String nodeName) {
        Element elt = getElement(nodeName);

        //getElement() returns null if there is no such nodeName
        if (elt == null) {
            throw new XmlException("No nodeName '" + nodeName + "' found, or other exception");
        } else {
            // We check that it's VerySimpleXml
            if (elt.getChildren().size() > 0) {
                throw new XmlException("Bad use in RwtJdom.getValue() : The node :" + nodeName + " should not have any child ; it's not an expected Xml structure.");
            } else {
                return elt.getText();
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public JdomRepresentation set(String nodeName, String value) {
        try {
            Element elt = getElement(nodeName);
            elt.setText(value);
        } catch (XmlException ex) {
            add(nodeName, value);
        }
        return this;

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Document getDocument() {
        return this.document;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Long getNumber(String nodeName) {
        String val = get(nodeName);
        return new Long(val.trim());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T extends Number> T getNumber(String nodeName, T exemple) throws RepresentationException, NumberFormatException {
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
        Element elt = getOptionalElement(nodeName);
        if (elt == null) {
            return null;
        } else {
            return elt.getText();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<String> getValues(String nodeName) {
        List<Element> elements = getElements(nodeName);
        ArrayList<String> result = new ArrayList<String>();
        for (Element elt : elements) {
            if (elt.getChildren().size() > 0) {
                throw new XmlException("Illegal use : the node " + nodeName + "has " + elt.getChildren().size() + " Childrens");
            }
            result.add(elt.getText());
        }
        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Long> getNumbers(String nodeName) {
        List<String> strings = getValues(nodeName);
        List<Long> result = new ArrayList<Long>();
        for (String s : strings) {
            result.add(new Long(s.trim()));
        }
        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T extends Number> List<T> getNumbers(String nodeName, T exemple) {

        List<String> strings = getValues(nodeName);
        List<T> result = new ArrayList<T>();
        for (String s : strings) {
            result.add(MathUtils.convert(s.trim(), exemple));
        }
        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public JdomRepresentation copy() {
        JdomRepresentation result = new JdomRepresentation(this.toString());
        assert result.getDocument() != this.document : "In memory documents should be differents";
        return result;
    }

    /**
     * Shortcut for reading an Xml document
     * @param path
     * @return the document, or null if the file doesn't exist yet
     * @throws org.jdom.JDOMException
     * @throws java.io.IOException
     */
    public static Document readDocument(String path) throws JDOMException, IOException {

        File f = new File(path);
        SAXBuilder sxb = new SAXBuilder();
        Document jDomDocument = sxb.build(f);
        return jDomDocument;
    }

    /**
     * Saves the JDOM Document
     * @param doc
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static boolean saveDocument(Document doc, String path) throws FileNotFoundException, IOException {
        File f = new File(path);

        if (!f.exists()) {
            f.createNewFile();
        }
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        FileOutputStream fos = new FileOutputStream(f);
        sortie.output(doc, fos);
        fos.close();
        f = null;
        return true;
    }

    /**
     * Saves the Xml Document
     * @param doc
     * @param filePath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean save(String path) throws FileNotFoundException, IOException {
        File f = new File(path);

        if (!f.exists()) {
            f.createNewFile();
        }
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        FileOutputStream fos = new FileOutputStream(f);
        sortie.output(this.document, fos);
        fos.close();
        f = null;
        return true;
    }

    /**
     * Saves the Xml Document
     * @param doc
     * @param filePath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean save() throws IOException {
        if (this.path == null) {
            throw new IllegalStateException("No path has been set for this document");
        }
        File f = new File(path);

        if (!f.exists()) {
            f.createNewFile();
        }
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        try {
            FileOutputStream fos = new FileOutputStream(f);
            sortie.output(this.document, fos);
            fos.close();
        } catch (FileNotFoundException ex) {
            throw new IOException("File not found :" + ex.getMessage());
        }

        f = null;
        return true;
    }

   
    

    /**
     *
     * @param o
     * @return
     * @deprecated 
     */
    public static JdomRepresentation getRepresentation(Object o) {
        String prefix;
        if (o instanceof Resource){
            prefix = ((Resource) o).getPrefix();
        }else{
            prefix = "root";
        }

        JdomRepresentation representation = new JdomRepresentation(prefix, SerializationUtils.serialize(o));
        return representation;
    }

    /**
     * TODO : for every right object, we should check if there is an iterator, or an array
     * @param serialization
     * @return
     * TODO : test
     */
    @Override
    public Representation reset() {        
        return  new JdomRepresentation();
    }

    /**
     * Create a basic representation
     * @param resource Resource
     * @return
     */
    @Override
    public Representation construct(Resource resource) {
        CoupleList<String, Object> serialization = resource.serialize();
        return this.construct(resource.getPrefix(),  serialization);
    }

    /**
	 * {@inheritDoc }
	 */
	@Override
	public Representation construct(String prefix, CoupleList<String, Object> nodesAndValues) {
		// TODO Auto-generated method stub
		return new JdomRepresentation(prefix, nodesAndValues);
	}

	//TODO : to be tested !
	@Override
	public Representation addList(String listName, String nodeName,
			List<Object> values) {
		Element list = new Element(listName);
		for (Object o : values){
			Element e = new Element(nodeName);
			e.setText(o.toString());
			list.addContent(e);
		}
		this.document.getRootElement().addContent(list);
		return this;
	}

	//TODO : to be tested !
	@Override
	public Representation addList(ResourceList resources, String prefixIfListIsEmpty) {
		if (resources == null){
			throw new IllegalArgumentException("Resources is null");
		}
		if (resources.isEmpty()){
			this.document.getRootElement().addContent(new Element(prefixIfListIsEmpty));
		}else{
			Resource r0 = resources.get(0);
			Element list = new Element(r0.getListPrefix());
			for (Object r :  resources){
				
				Element e = new Element(r0.getPrefix());
				
				Element id=new Element("id");
				id.setText(((Resource)r).getId().toString());
				e.addContent(id);
				
				Element toString = new Element("show");
				toString.setText(r.toString());
				e.addContent(toString());
								
				list.addContent(e);
			}
			this.document.getRootElement().addContent(list);			
		}
		return this;
	}

	/**
	 * {@inheritDoc }
	 */
	@Override	
	public String getEtagValue() {
		return MD5.encodeMD5(this.toString());
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

	

}
