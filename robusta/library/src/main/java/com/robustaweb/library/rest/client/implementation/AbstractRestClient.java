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

import com.robustaweb.library.commons.exception.HttpException;
import com.robustaweb.library.commons.exception.HttpUuidException;
import com.robustaweb.library.commons.util.Couple;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.commons.util.StringUtils;
import com.robustaweb.library.rest.client.RestClient;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used both for Synchronous and Asynchronous client
 *
 * @author n.zozol
 */
public abstract class AbstractRestClient<Client> implements RestClient<Client> {


    /* -- BEFORE Requests--*/

    /**
     * can be easily configured outside
     */
    protected static String applicationUri;

    /**
     * Usually, authorizationValue is set once for all
     */
    protected static String authorizationValue = "";

    /**
     * After each request, the content type is reset to this default contentType
     */
    protected static String defaultContentType = formContentType;

    /**
     * Content type sent to next request
     */
    protected String contentType = defaultContentType;


    /* -- AFTER Requests -- */
    protected static final int INVALID_HTTP_CODE = -1;
    protected int httpCode = INVALID_HTTP_CODE;
    boolean validateUuids = false;
    protected Map<String, String> responseHeaders = new HashMap<String, String>();
    String requestUuid = null;
    public boolean authorizationOnlyOnce = false;

    /**
     * For non-gwt application, it will check that uri starts with http or https
     *
     * @param applicationUri
     * @throws IllegalArgumentException if uri does not start with http:// or https://
     */
    protected void checkConstructorUri() throws IllegalArgumentException {
        if (!applicationUri.startsWith("http://") && !applicationUri.startsWith("https://")) {
            throw new IllegalArgumentException("applicationUri must start with http:// or https:// ;  currently there is : " + applicationUri);
        }
    }


    /**
     * @param method
     */
    protected void prepareMethod() {
        checkConstructorUri();
        requestUuid = null;
        httpCode = INVALID_HTTP_CODE;
        if (this.contentType == null) {
            this.contentType = defaultContentType;
        }
        this.responseHeaders = new HashMap<String, String>();
    }


    @Override
    public void validateRequestAndResponseUuids(boolean validate) {
        this.validateUuids = validate;
    }

    /**
     * Set authorizationValue as a static method
     *
     * @param authorizationValue
     */
    @Override
    public void setAuthorizationValue(String authorizationValue, boolean once) {
        AbstractRestClient.authorizationValue = authorizationValue;
        authorizationOnlyOnce = once;
    }

    protected boolean contentTypeIsForm() {
        return this.contentType.toLowerCase().contains("x-www-form-urlencoded");
    }

    protected String getContentTypeFromShortCut(String shortCut) {
        if (contentType.toLowerCase().equals("xml")) {
            return xmlContentType;
        }
        if (contentType.toLowerCase().equals("text")) {
            return textContentType;
        }
        if (contentType.toLowerCase().equals("html")) {
            return htmlContentType;
        }
        if (contentType.toLowerCase().equals("form")) {
            return formContentType;
        }
        if (contentType.toLowerCase().equals("json")) {
            return jsonContentType;
        }
        return null;
    }

    @Override
    public void setDefaultContentType(String contentType) {
        String fromShort = getContentTypeFromShortCut(contentType);
        if (fromShort != null) {
            defaultContentType = fromShort;
        } else {
            defaultContentType = contentType;
        }
    }

    /**
     * Once the contentType is set, it will be used as long as the object lives.
     * default is "application/x-www-form-urlencoded;charset=utf-8".
     * You can use 'form', 'xml', 'json', 'html', 'text' for shortcuts
     *
     * @param contentType
     */
    @Override
    public void setContentType(String contentType) {
        String fromShort = getContentTypeFromShortCut(contentType);
        if (fromShort != null) {
            this.contentType = fromShort;
        } else {
            this.contentType = contentType;
        }
    }

    /**
     * May be done only once, as the application path is static
     *
     * @param applicationUri
     */
    @Override
    public void setApplicationUri(String applicationUri) throws HttpException {

        AbstractRestClient.applicationUri = applicationUri;
        checkConstructorUri();//TODO not logic : should be checked before setting invalid value
    }


    @Override
    public int getHttpCode() {
        if (httpCode == INVALID_HTTP_CODE) {
            throw new IllegalStateException("The previous http request was not sent");
        }
        return httpCode;
    }


    public void clean() {
        this.contentType = defaultContentType;
        if (authorizationOnlyOnce) {
            authorizationValue = null;
        }
    }

    /**
     * Encode an URL parameter with UTF-8, as asked by wwc
     * Exemple : encodeParameter("A B C $%" returns "A+B+C %24%25"
     * Can't use java.net here tp stay GWT compatible
     *
     * @param param
     * @param paramValue
     * @return
     */
    protected abstract String encodeParameter(String nameOrValue);


    protected String encodeUrl(String applicationUri, String relativePath, CoupleList<String, Object> parameters) {
        String result = StringUtils.addPath(applicationUri, relativePath);

        if (!parameters.isEmpty()) {
            result += "?";
            result += encodeParameters(parameters);
        }

        return result;
    }

    protected String encodeParameters(CoupleList<String, Object> parameters) {
        StringBuilder builder = new StringBuilder();
        for (Couple<String, Object> couple : parameters) {
            builder.append(encodeParameter(couple.getLeft())).append("=")
                    .append(String.valueOf(couple.getRight())).append("&");
        }
        builder.setLength(builder.length() - 1); //remove last '&'
        return builder.toString();
    }


    protected void addRequestHeaders() {
        setHeader("Content-type", this.contentType);
        if (authorizationValue != null) {
            setHeader("Authorization", SunRestClient.authorizationValue);
        }
        setHeader("request-uuid", java.util.UUID.randomUUID().toString());
    }

    /**
     * Check request and response uuids and throw a HttpUuidException if needed
     *
     * @throws HttpUuidException
     * @see #validateRequestAndResponseUuids(boolean)
     */
    public void checkUuids() throws HttpUuidException {
        String actualUuid = this.responseHeaders.get("response-uuid");
        if (validateUuids && !actualUuid.equals(this.requestUuid)) {
            throw new HttpUuidException(this.requestUuid, actualUuid);
        }
    }

    @Override
    public Map<String, String> getResponseHeaders() {
        return this.responseHeaders;
    }
}
