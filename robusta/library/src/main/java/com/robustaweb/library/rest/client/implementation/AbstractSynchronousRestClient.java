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
import com.robustaweb.library.rest.client.SynchronousRestClient;

/**
 *
 * @author n.zozol
 */
public abstract class AbstractSynchronousRestClient<Client, Response> extends AbstractRestClient<Client> implements SynchronousRestClient<Client, Response> {




    @Override
    public Response GET(String path, CoupleList<String, Object> parameters) throws HttpException {
        prepareMethod();
        String url = encodeUrl(applicationUri, path, parameters);
        return executeMethod(HttpMethod.GET, url);
    }


    @Override
    public Response POST(String path, CoupleList<String, Object> parameters) throws HttpException {
        prepareMethod();
        String url = StringUtils.addPath(applicationUri, path);
        String body = encodeParameters(parameters);
        return executeMethod(HttpMethod.POST, url, body);
    }

    @Override
    public Response POST(String path, String body) throws HttpException {
        prepareMethod();
        String url = StringUtils.addPath(applicationUri, path);
        return executeMethod(HttpMethod.POST, url, body);
    }


    @Override
    public Response PUT(String path, CoupleList<String, Object> parameters) throws HttpException {
        prepareMethod();
        String url = StringUtils.addPath(applicationUri, path);
        String body = encodeParameters(parameters);
        return executeMethod(HttpMethod.PUT, url, body);
    }

    @Override
    public Response PUT(String path, String body) throws HttpException {
        prepareMethod();
        String url = StringUtils.addPath(applicationUri, path);
        return executeMethod(HttpMethod.PUT, url, body);
    }



    @Override
    public Response DELETE(String path, CoupleList<String, Object> parameters) throws HttpException {
        prepareMethod();
        String url = encodeUrl(applicationUri, path, parameters);
        return executeMethod(HttpMethod.DELETE, url);
    }


    /**
     * Execute the request with a Body, without url params
     */
    protected abstract Response executeMethod(final HttpMethod method, final String url, final String requestBody) throws HttpException;

    /**
     * Executes the request with url params, without a body
     */
    protected abstract Response executeMethod(final HttpMethod method, final String url) throws HttpException;

}


