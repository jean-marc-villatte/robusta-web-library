package com.robustaweb.library.rest.etag;

import java.util.List;

import com.robustaweb.library.rest.representation.Representation;

public class EtagStore {
	
	List<Etag> etags;
	
	public void addEtag(Etag etag) {
		etags.add(etag);
	}

	public void removeEtag(Etag etag) {
		etags.remove(etag);
	}

	public void removeEtag(String etagValue) {
		int index = 0;
		for (int i = 0; i < etags.size(); i++) {
			Etag etag = etags.get(i);
			if (etag.getEtagValue() == etagValue) {
				index = i;
				break;
			}
		}
		etags.remove(index);
	}

	/**
	 * 
	 * @param representation
	 */
	public void removeEtag(Representation representation) {
		int index = 0;
		for (int i = 0; i < etags.size(); i++) {
			Etag etag = etags.get(i);
			if (etag.getRepresentation() == representation || etag.getEtagValue().equals(representation.getEtagValue())) {
				index = i;
				break;
			}
		}
		etags.remove(index);
	}
	
}
