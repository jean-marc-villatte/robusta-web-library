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
package com.robustaweb.library.rest;

/**
 * @todo1 : put this package protected, in implementation package
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public enum HttpMethod {

    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");
    
    String method;

    private HttpMethod(String method) {
		this.method = method;
	}
    
	public String getMethod() {
		if (method == null){
			throw new IllegalArgumentException(
					"No method set for a OTHER method ; use HttpMethod.OTHER.setMethod(\"COPY\") to assign the method");
		}
		return method;
	}

	/**
	 * Set the method AND returns the object
	 * @param method
	 * @return
	 */
	public HttpMethod setMethod(String method) {
		this.method = method;
		return this;
	}
    
    
    	
    
}
