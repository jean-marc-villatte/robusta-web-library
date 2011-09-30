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
package com.robustaweb.library.rest.client.implementation;

import java.util.HashMap;
import java.util.Map;

import com.robustaweb.library.commons.exception.HttpException;
import com.robustaweb.library.commons.util.Couple;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.commons.util.StringUtils;
import com.robustaweb.library.rest.HttpMethod;
import com.robustaweb.library.rest.client.RestClient;
import com.robustaweb.library.rest.client.SynchronousRestClient;

/**
 * This class is used both for Synchronous and Asynchronous client
 * @author n.zozol
 */
public abstract class AbstractRestClient<Client> implements RestClient<Client>{

	 protected Map<String, String> responseHeaders = new HashMap<String, String>();
	
      /**
     * For non-gwt application, it will check that uri starts with http or https
     * @param applicationUri
     * @throws IllegalArgumentException if uri does not start with http:// or https://
     */
    protected void checkConstructorUri(String applicationUri) throws IllegalArgumentException {
        if (!applicationUri.startsWith("http://") && !applicationUri.startsWith("https://")) {
            throw new IllegalArgumentException("applicationUri must start with http:// or https://");
        }
    }



    /**
     * Returns the full uri and the request Body in a String[2] array
     * If content is Form, and Method is POST or PUT, parameters will go in the RequestBody
     * For all other cases, it will go to the url, such as ?param1=Jo&param2=Bob
     * @param method Http method (or Other)
     * @param relativePath
     * @param parameters  
     * @return result[0] is the full URI, result[1] is the requestBody
     * @throws HttpException
     */
    protected String[] prepareMethod(HttpMethod method, String relativePath, CoupleList<String, Object> parameters) throws HttpException {

    	this.responseHeaders = new HashMap<String, String>();
        String body = requestBody;
        //Making a clear uri
        String url = StringUtils.addPath(applicationUri, relativePath);

        //Dealing with parameters
        if (parameters != null && parameters.size() > 0) {

            //Special case of forms && POST/PUT methods
            if (contentTypeIsForm() && (method == HttpMethod.POST || method == HttpMethod.PUT)) {
                //params will go in the requestBody
                if (requestBody != null && requestBody.length() > 0) {
                    throw new HttpException("Can't do a POST or PUT method with both parameters with 'x-www-form-urlencoded', and a non-null requestBody");
                } else {
                    //adding parameters to the requestBody
                    assert requestBody == null || requestBody.length() == 0;
                    body = encodeFormRequestBody(parameters);
                }
            } else {
                url = encodeUrl(applicationUri, relativePath, parameters);
            }
        }

        return new String[]{url, body};
    }
    /**
     * can be easily configured outside
     */
    protected static String applicationUri;
    /**
     *
     */
    protected String requestBody = "";
    /**
     *
     */
    protected String credentials = "";
    /**
     *
     */
    protected int httpCode;
    /**
     *
     */
    protected String response;
    protected static String defaultContentType = SynchronousRestClient.xmlContentType;
    /**
     *
     */
    protected String contentType = SynchronousRestClient.xmlContentType;
    /**
     *
     */
    protected static String authorizationValue = "";

    /**
     * Set autorizationValue as a static method
     * @param authorizationValue
     */
    public void setAuthorizationValue(String authorizationValue) {
        AbstractRestClient.authorizationValue = authorizationValue;
    }

    protected boolean contentTypeIsForm() {
        return this.contentType.contains("x-www-form-urlencoded");
    }

    public static void setDefaultContentType(String contentType) {
        defaultContentType = contentType;
        if (contentType.equals("xml")) {
            defaultContentType = SynchronousRestClient.xmlContentType;
        }
        if (contentType.equals("html")) {
            defaultContentType = SynchronousRestClient.htmlContentType;
        }
        if (contentType.equals("form")) {
            defaultContentType = SynchronousRestClient.formContentType;
        }
        if (contentType.equals("json")) {
            defaultContentType = SynchronousRestClient.jsonContentType;
        }
    }

    /**
     * Once the contentType is set, it will be used as long as the object lives.
     * default is "application/x-www-form-urlencoded;charset=utf-8".
     * If you contentType="" or "form", it will be set to default. If you just put contentType="xml", it will be "application/xml;charset=utf-8"
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
        if (contentType.equals("xml")) {
            this.contentType = SynchronousRestClient.xmlContentType;
        }
        if (contentType.equals("html")) {
            this.contentType = SynchronousRestClient.htmlContentType;
        }
        if (contentType.equals("form")) {
            this.contentType = SynchronousRestClient.formContentType;
        }
        if (contentType.equals("json")) {
            this.contentType = SynchronousRestClient.jsonContentType;
        }
    }

    /**
     * May be done only once, as the application path is static
     * @param applicationUri
     */
    public void setApplicationUri(String applicationUri) {
        AbstractRestClient.applicationUri = applicationUri;
    }

    public void setNextRequestBody(String postBody) {
        this.requestBody = postBody;
    }

    public int getHttpCode() {
        return httpCode;
    }

    //TODO change with getResponse
    public String getResponse() {
        return response;
    }

    public void clean() {    	
        setNextRequestBody("");
        this.contentType = defaultContentType;
    }

     /**
     * Encode an URL parameter with UTF-8, as asked by wwc
     * Exemple : encodeParameter("A B C $%" returns "A+B+C %24%25"
     * @param param
     * @param paramValue
     * @return
     */
    protected abstract String encodeParameter(String nameOrValue);

    protected String encodeUrl(String applicationUri, String relativePath, CoupleList<String, Object> parameters) {
        String url = StringUtils.addPath(applicationUri, relativePath);
        StringBuilder result = new StringBuilder(url);
        //throw new HttpException("Can't do a POST method with both parameters and postbody");
        if (! parameters.isEmpty()){
            result.append("?");
        }
        for (int i = 0; i < parameters.size(); i++) {

            Couple<String, Object> c = parameters.get(i);
            String paramName = encodeParameter(c.getLeft());
            String paramValue = encodeParameter(c.getRight().toString());

            result.append(paramName);
            result.append("=");
            result.append(paramValue);
            //StringUtilities.replaceAll(value, "+","%20");
            if (i != parameters.size() - 1) {
                result.append("&");
            }
        }
        return result.toString();
    }

    protected String encodeFormRequestBody(CoupleList<String, Object> parameters) {
        StringBuilder result = new StringBuilder();

        boolean firstLine = true;
        for (int i = 0; i < parameters.size(); i++) {

            if (firstLine) {
                firstLine = false;
            } else {
                result.append("\n");
            }
            Couple<String, Object> c = parameters.get(i);
            String paramName = encodeParameter(c.getLeft());
            String paramValue = encodeParameter(c.getRight().toString());

            result.append(paramName);
            result.append("=");
            result.append(paramValue);
        }
        return result.toString();
    }
    

	@Override
	public Map<String, String> getHeaderResponse() {
		return this.responseHeaders;
	}
}
