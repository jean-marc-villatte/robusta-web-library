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
import com.robustaweb.library.commons.exception.RestException;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.rest.HttpMethod;
import com.robustaweb.library.rest.client.AsynchronousRestClient;
import com.robustaweb.library.rest.client.Callback;

/**
 *
 * @author n.zozol
 */
public abstract class AbstractAsynchronousRestClient<Client> extends AbstractRestClient<Client> implements AsynchronousRestClient<Client> {

    @Override
    public void GET(String relativePath, CoupleList<String, Object> parameters, Callback callback) throws HttpException {
        prepareMethod();

        String url = ;


        executeMethod(HttpMethod.GET, url, body, callback);
    }

    @Override
    public void POST(String relativePath, CoupleList<String, Object> parameters, Callback callback) throws HttpException {
        String[] obj = prepareMethod(HttpMethod.GET, relativePath, parameters);
        assert obj.length == 2;
        String url = obj[0], body = obj[1];


        executeMethod(HttpMethod.POST, url, body, callback);
    }

    @Override
    public void PUT(String relativePath, CoupleList<String, Object> parameters, Callback callback) throws HttpException {
        String[] obj = prepareMethod(HttpMethod.GET, relativePath, parameters);
        assert obj.length == 2;
        String url = obj[0], body = obj[1];


        executeMethod(HttpMethod.PUT, url, body, callback);
    }

    @Override
    public void DELETE(String relativePath, CoupleList<String, Object> parameters, Callback callback) throws HttpException {
        String[] obj = prepareMethod(HttpMethod.GET, relativePath, parameters);
        assert obj.length == 2;
        String url = obj[0], body = obj[1];

        executeMethod(HttpMethod.DELETE, url, body, callback);
    }
    
    @Override
    public void OTHER(String method, String relativePath,
    		CoupleList<String, Object> parameters, Callback callback)
    		throws HttpException {
    	String[] obj = prepareMethod(HttpMethod.OTHER.setMethod(method), relativePath, parameters);
        assert obj.length == 2;
        String url = obj[0], body = obj[1];

        executeMethod(HttpMethod.OTHER.setMethod(method), url, body, callback);
    	
    }

   

    //TODO : add the method getResponse
    @Override
    @Deprecated
    public abstract void join();

    protected abstract void executeMethod(final HttpMethod method, final String url, final String requestBody, final Callback callback) throws HttpException;

    /**
     * calls the callback succes or failure, using the httpcode
     * @param httpCode
     * @param response
     * @param callback
     */
    protected void callCallback(Callback callback, int httpCode, String response) {

        if (httpCode >= 200 && httpCode < 300) {
            callback.onSuccess(response);
        } else if (httpCode >= 300 && httpCode < 400) {
            //no success
        } else if (httpCode >= 400 && httpCode < 600) {
        } else if (httpCode < 600) {
            callback.onFailure(new RestException(httpCode, response));
        }
    }
}
