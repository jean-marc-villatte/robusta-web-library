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
package com.robustaweb.library.commons;

import com.robustaweb.library.gwt.CodecGwt;
import com.robustaweb.library.gwt.GwtRepresentation;
import com.robustaweb.library.rest.etag.EtagStore;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.resource.Resource;
import com.robustaweb.library.security.Codec;

import java.util.logging.Logger;

/**
 * Hot configuration class. This class is more important for GWT developpers.
 * This class replace the Injection mechanism that is not possible with GWT (or
 * not realistic).
 * 
 * @author Nicolas Zozol
 */
public class MyRobusta {

	/**
	 * Uri of the application
	 */
	static String applicationUri = null;

	/**
	 * Should be true in a GWT context, false if not
	 */
	static boolean gwt = false;

	/**
	 * Sets the right Xml library
	 */
	static Representation emptyRepresentation;
	/**
	 * Uses the right Codec library
	 */
	static Codec codec;

	
	static EtagStore etagStore;
	

	/**
	 * Return the GWT module name to import Robusta in a GWT project :
	 * com.robustaweb.library.Robusta
	 * 
	 * @return the GWT module name
	 */
	static public String getGwtModuleName() {
		return "com.robustaweb.library.Robusta";
	}

	/**
	 * Returns the application uri
	 * 
	 * @return the application Uri
	 * @throws IllegalStateException
	 *             if the uri has not been set yet
	 */
	public static String getApplicationUri() {

		return applicationUri;
	}

	/**
	 * Sets the application URI
	 * 
	 * @param applicationUri
	 */
	public static void setApplicationUri(String applicationUri) {
		MyRobusta.applicationUri = applicationUri;
	}

	/**
	 * Returns the correct Codec implementation
	 * 
	 * @return the correct Codec implementation
	 */
	public static Codec getCodec() {
		return codec;
	}

	/**
	 * Sets the correct Codec implementation
	 * 
	 * @param codec
	 */
	public static void setCodec(Codec codec) {
		MyRobusta.codec = codec;
	}

	/**
	 * Returns true if the application is in GWT <strong>Client</strong> mode
	 * 
	 * @return true if the application is in GWT client mode
	 */
	public static boolean isGwt() {
		return gwt;
	}

	/**
	 * Sets the GWT mode. It will enable Gwt Codecs, GWT Xml Representation and
	 * if enableGwtDefaults param is true
	 * 
	 * @param gwt
	 *            true if GWT mode, false if other mode
	 * @param enableGwtDefaults
	 *            enable Gwt Codecs, GWT Xml, GWT I18nProcess if true
	 */
	public static void setGwt(boolean gwt, boolean enableGwtDefaults) {
		MyRobusta.gwt = gwt;
		if (gwt && enableGwtDefaults) {
			if (emptyRepresentation == null) {
				MyRobusta.emptyRepresentation = new GwtRepresentation();
			}
			if (codec == null) {
				codec = new CodecGwt();
			}
		}
	}

	static Representation defaultRepresentation;

	/**
	 * Setting a default empty representation allow the use of the built-in
	 * representation engine for getDefaultRepresentation() methods
	 * 
	 * @param representation
	 *            the Representation engine
	 * @see #getDefaultRepresentation(Resource)
	 */
	public static void setDefaultRepresentation(Representation representation) {
		MyRobusta.defaultRepresentation = representation;
	}



	/**
	 * Returns an <strong>empty</strong> Representation using the default Engine
	 * 
	 * @return
	 */
	public static Representation getDefaultRepresentation(Object object) {
		if (defaultRepresentation == null) {
			throw new IllegalStateException(
                    "No defaultRepresentation engine set for MyRobusta ; When starting your application, use : 'MyRobusta.setDefaultRepresentation(new JdomRepresentation());' for exemple)");
		}
		return defaultRepresentation.getRepresentation(object);
	}

	public static void setEtagStore(EtagStore etagStore) {
		MyRobusta.etagStore = etagStore;
	}
	
	public static EtagStore getEtagStore() {
		return etagStore;
	}


    public static Logger logger = Logger.getLogger("com.robustaweb");

    public static void error(String message){
        logger.severe(message);
    }

    public static void warning(String message){
        logger.warning(message);
    }
	
}
