package com.robustaweb.library.rest.etag;

import java.util.List;

import com.robustaweb.library.rest.representation.Representation;

public class EtagStore {
	
	List<EtagObject> etagObjects;
	
	public void addEtag(EtagObject etagObject) {
		etagObjects.add(etagObject);
	}

	public void removeEtag(EtagObject etagObject) {
		etagObjects.remove(etagObject);
	}

	public void removeEtag(String etagValue) {
		int index = 0;
		for (int i = 0; i < etagObjects.size(); i++) {
			EtagObject etagObject = etagObjects.get(i);
			if (etagObject.getEtagValue() == etagValue) {
				index = i;
				break;
			}
		}
		etagObjects.remove(index);
	}

	/**
	 * 
	 * @param representation
	 */
	public void removeEtag(Representation representation) {
		int index = 0;
		for (int i = 0; i < etagObjects.size(); i++) {
			EtagObject etagObject = etagObjects.get(i);
			if (etagObject.getRepresentation() == representation /*|| etagObject.getEtagValue().equals(representation.getEtagValue())*/) {
				index = i;
				break;
			}
		}
		etagObjects.remove(index);
	}
	
}
