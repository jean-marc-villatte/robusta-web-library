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
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.commons.util.StringUtils;
import com.robustaweb.library.rest.HttpMethod;
import com.robustaweb.library.rest.client.AsynchronousRestClient;

/**
 *
 * @author n.zozol
 */
public abstract class AbstractAsynchronousRestClient<Client, TResponse, TCallback> extends AbstractRestClient<Client> implements AsynchronousRestClient<TCallback>{

    String response;


    protected AbstractAsynchronousRestClient(String applicationUri) {
        super(applicationUri);
    }

    @Override
    public void GET(String path, CoupleList<String, Object> parameters, TCallback callback) throws HttpException {
        prepareMethod();
        String url = encodeUrl(applicationUri, path, parameters);
        executeMethod(HttpMethod.GET, url, callback);
    }

    @Override
    public void POST(String path, CoupleList<String, Object> parameters, TCallback callback) throws HttpException {
        prepareMethod();

        String url = StringUtils.addPath(applicationUri, path);
        String body = encodeParameters(parameters);
        executeMethod(HttpMethod.POST, url, body, callback);
    }

    @Override
    public void POST(String path, String body, TCallback callback) throws HttpException {
        prepareMethod();
        String url = StringUtils.addPath(applicationUri, path);
        executeMethod(HttpMethod.POST, url, body, callback);
    }

    @Override
    public void PUT(String path, CoupleList<String, Object> parameters, TCallback callback) throws HttpException {
        prepareMethod();
        String url = StringUtils.addPath(applicationUri, path);
        executeMethod(HttpMethod.PUT, url, callback);
    }

    @Override
    public void PUT(String path, String body, TCallback callback) throws HttpException {
        prepareMethod();
        String url = StringUtils.addPath(applicationUri, path);
        executeMethod(HttpMethod.PUT, url, body, callback);
    }

    @Override
    public void DELETE(String path, CoupleList<String, Object> parameters, TCallback callback) throws HttpException {
        prepareMethod();
        String url = encodeUrl(applicationUri, path, parameters);
        executeMethod(HttpMethod.DELETE, url, callback);
    }
    


   



    protected abstract void executeMethod(final HttpMethod method, final String url,  final TCallback callback) throws HttpException;


    protected abstract void executeMethod(final HttpMethod method, final String url, final String requestBody, final TCallback callback) throws HttpException;


}
