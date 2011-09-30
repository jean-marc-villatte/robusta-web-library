package com.robustaweb.library.rest.etag;

public enum ETagEnum {

	IF_NONE_MATCH("If-None-Match"), IF_DIFFERENT("If-Different");

	String value;

	private ETagEnum(String value) {
		this.value = value;
	}
}
