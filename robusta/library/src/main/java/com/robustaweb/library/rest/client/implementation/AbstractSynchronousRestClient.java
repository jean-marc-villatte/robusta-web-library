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
import com.robustaweb.library.rest.client.SynchronousRestClient;

/**
 *
 * @author n.zozol
 */
public abstract class AbstractSynchronousRestClient<Client> extends AbstractRestClient<Client> implements SynchronousRestClient<Client> {

    /**
     * {@inheritDoc }
     */
    @Override
    public String POST(String relativeFileWithNoParam, CoupleList<String, Object> parameters) throws HttpException {
        String[] obj = prepareMethod(HttpMethod.POST, relativeFileWithNoParam, parameters);
        assert obj.length == 2;
        return executeMethod(HttpMethod.POST, obj[0], obj[1]);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String GET(String relativeFileWithNoParam, CoupleList<String, Object> parameters) throws HttpException {
        String[] obj = prepareMethod(HttpMethod.GET, relativeFileWithNoParam, parameters);
        assert obj.length == 2;
        return executeMethod(HttpMethod.GET, obj[0], obj[1]);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String PUT(String relativeFileWithNoParam, CoupleList<String, Object> parameters) throws HttpException {
        String[] obj = prepareMethod(HttpMethod.PUT, relativeFileWithNoParam, parameters);
        assert obj.length == 2;
        return executeMethod(HttpMethod.PUT, obj[0], obj[1]);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String DELETE(String relativeFileWithNoParam, CoupleList<String, Object> parameters) throws HttpException {
        String[] obj = prepareMethod(HttpMethod.DELETE, relativeFileWithNoParam, parameters);
        assert obj.length == 2;
        return executeMethod(HttpMethod.DELETE, obj[0], obj[1]);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String OTHER(String method, String relativeFileWithNoParam, CoupleList<String, Object> parameters) throws HttpException {
        String[] obj = prepareMethod(HttpMethod.OTHER.setMethod(method), relativeFileWithNoParam, parameters);
        assert obj.length == 2;
        return executeMethod(HttpMethod.OTHER.setMethod(method), obj[0], obj[1]);
    }

    /**
     * This method has the responsability do make the correct call with a specific implementation
     * @param method
     * @param relativePathAndParams
     * @param requestBody
     * @return
     * @throws HttpException
     * @throws RestException
     */
    protected abstract String executeMethod(final HttpMethod method, final String url, final String requestBody) throws HttpException;
}


