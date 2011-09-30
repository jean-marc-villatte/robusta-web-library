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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.robustaweb.library.commons.exception.HttpException;
import com.robustaweb.library.commons.util.FileUtils;
import com.robustaweb.library.rest.HttpMethod;
import com.robustaweb.library.rest.client.SynchronousRestClient;

/**
 * Simple REST Http client wrapping the very popular Apache Client. Check the Apache Client librairies are in the ClassPath.
 * @author Nicolas Zozol - Edupassion.com - Robusta Web nzozol@edupassion.com
 */
public class ApacheRestClient extends AbstractSynchronousRestClient<DefaultHttpClient> {

    DefaultHttpClient client ;
   

    public ApacheRestClient(String applicationUri) {
        checkConstructorUri(applicationUri);
        setApplicationUri(applicationUri);
    }


    /**
     * Create the Apache Http Client ; overwrite this method to create a client with your own caracteristics, including SSL
     * @return
     */
    protected DefaultHttpClient createClient(){
        return new DefaultHttpClient();
    }


    @Override
    protected String encodeParameter(String nameOrValue) {
        try {
            return URLEncoder.encode(nameOrValue, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Can't encode " + nameOrValue);
        }
    }

    @Override
    protected String executeMethod(HttpMethod method, String url, String requestBody) throws HttpException {
        assert url.startsWith("http");


        try {
            client = createClient();
            HttpUriRequest httpMethod = null;
            
            switch (method) {
                case GET:
                    httpMethod = new HttpGet(url);
                    break;
                case DELETE:
                    httpMethod = new HttpDelete(url);
                    break;

                case POST:
                    httpMethod = new HttpPost(url);
                    ((HttpPost) httpMethod).setEntity(new StringEntity(this.requestBody));
                    break;
                case PUT:
                    httpMethod = new HttpPut(url);
                    ((HttpPut) httpMethod).setEntity(new StringEntity(this.requestBody));
                    break;
                default:
                    throw new IllegalStateException("Can't execute this method : " + method);
            }

            //Adding headers
            if (this.contentType == null){
                this.contentType = SynchronousRestClient.xmlContentType;
            }
            httpMethod.addHeader("Content-type", this.contentType);
            if (authorizationValue != null) {
                httpMethod.addHeader("Authorization", ApacheRestClient.authorizationValue);
            }

            //Executing
            HttpResponse httpResponse = client.execute(httpMethod);

            //parsing responseHeaders
           
            HeaderIterator it = httpResponse.headerIterator();
            while (it.hasNext()){
            	Header header = it.nextHeader();
            	responseHeaders.put(header.getName(), header.getValue());
            }
            
            //Parsing response
            this.response = FileUtils.readInputStream(httpResponse.getEntity().getContent());
            
            
            return this.response;

        } catch (IOException ex) {
            throw new HttpException("IO Exception : "+ex.getMessage(), ex);
        }finally{
            clear();
        }
    }

    private void clear() {
        this.setNextRequestBody("");
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DefaultHttpClient getUnderlyingClient() {
        return client;
    }



        

}


