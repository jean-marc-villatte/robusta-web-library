package com.robustaweb.library.rest.etag;

public class EtagClientDecorator {

	EtagStore store = new EtagStore();
	
	void addEtag(String etagValue, String response){
		Etag etag = new Etag(etagValue, response );
		//store.addEtag(etag);
		store.addEtag(etag);
	}
	
	
	
}
