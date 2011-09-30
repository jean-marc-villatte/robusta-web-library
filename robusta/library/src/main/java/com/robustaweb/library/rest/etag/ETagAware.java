package com.robustaweb.library.rest.etag;

public interface ETagAware {

	EtagStore getEtagStore();
	

    
    public void addETag(String ETag, String version);
}
