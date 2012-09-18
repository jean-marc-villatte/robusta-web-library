package com.robustaweb.library.rest.etag;

public class EtagClientDecorator {

	EtagStore store = new EtagStore();
	
	void addEtag(String etagValue, String response){
		EtagObject etagObject = new EtagObject(etagValue, response );
		//store.addEtag(etagObject);
		store.addEtag(etagObject);
	}
	
	
	
}
