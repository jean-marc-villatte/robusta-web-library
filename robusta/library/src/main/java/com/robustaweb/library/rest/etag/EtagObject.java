package com.robustaweb.library.rest.etag;

import java.util.Date;

import com.robustaweb.library.rest.representation.Representation;

/**
 * This class is used by the client to store Etags
 * @author robusta web
 *
 */
public class EtagObject {

	public static final String IF_NONE_MATCH="If-None-Match"; 
	
	Representation representation;
	String response;
	Date lastModified;
	String etagValue;
	
	public EtagObject(String etagValue) {
		this.etagValue = etagValue;
		this.lastModified = new Date();
	}
	
	
	
	public EtagObject(String etagValue, String response) {
		super();
		this.response = response;
		this.lastModified = new Date();
		this.etagValue = etagValue;
	}
	
	
	public Representation getRepresentation() {
		return representation;
	}
	public void setRepresentation(Representation representation) {
		this.representation = representation;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}


	public String getEtagValue() {
		return etagValue;
	}
	
	public void setEtagValue(String etagValue) {
		this.etagValue = etagValue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((etagValue == null) ? 0 : etagValue.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EtagObject other = (EtagObject) obj;
		if (etagValue == null) {
			if (other.etagValue != null)
				return false;
		} else if (!etagValue.equals(other.etagValue))
			return false;
		return true;
	}
	
	
}
