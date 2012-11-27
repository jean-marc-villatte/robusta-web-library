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
package com.robustaweb.library.gwt;

import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.robustaweb.library.commons.exception.HttpException;
import com.robustaweb.library.rest.HttpMethod;
import com.robustaweb.library.rest.client.implementation.Callback;
import com.robustaweb.library.rest.client.implementation.AbstractAsynchronousRestClient;

/**
 *
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public class GwtRestClient extends AbstractAsynchronousRestClient<RequestBuilder> {

    RequestBuilder builder;

    /**
     * Creates a client wich will use relative uris (set the applicationUri to '/')
     */
    public GwtRestClient() {
        this("");
    }

    /**
     * If no applicationUri is set, the client will use "/" as base URI
     */
    public GwtRestClient(String applicationUri) {
        if (applicationUri == null) {
            applicationUri = "/";
        }
        GwtRestClient.applicationUri = applicationUri;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public RequestBuilder getUnderlyingClient() {
        return this.builder;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected void executeMethod(HttpMethod method, String url, String requestBody, Callback callback) throws HttpException {

        try {
            RequestBuilder.Method meth = null;
            switch (method) {
                case GET:
                    meth = RequestBuilder.GET;
                    break;
                case POST:
                    meth = RequestBuilder.POST;
                    break;
                case DELETE:
                    meth = RequestBuilder.DELETE;
                    break;
                case PUT:
                    meth = RequestBuilder.PUT;
                    break;
                default:
                    throw new IllegalStateException("No method valid for : " + method);
            }
            this.builder = new RequestBuilder(meth, url);

            if (this.contentType != null && !this.contentType.isEmpty()) {
                this.builder.setHeader("Content-Type", this.contentType);
            }
            if (GwtRestClient.authorizationValue != null && GwtRestClient.authorizationValue.length() > 0) {
                this.builder.setHeader("Authorization", GwtRestClient.authorizationValue);
            }

            RestRequestCallback cb = new RestRequestCallback(callback);
            this.builder.sendRequest(this.requestBody, cb);
            
        } catch (Exception ex) {
            callback.onException(ex);
        } finally {
            clean();
        }


    }

    @Override
    public void join() {
        throw new UnsupportedOperationException("Not supported yet for GWT. Javascript doesn't use Threads.");
    }

    /**
     * Encoding is done automatically with the client. The method will so return the exactly same value
     * @param nameOrValue
     * @return the exactly same value
     */
    @Override
    protected String encodeParameter(String nameOrValue) {
        return URL.encode(nameOrValue);
    }

    private class RestRequestCallback implements RequestCallback {

        Callback callback;
        int httpCode;
        String response;

        public RestRequestCallback(Callback callback) {
            System.out.println("Creating RequestCallback");
            this.callback = callback;
        }


        
        /**
         * {@inheritDoc }
         */
        @Override
        public void onResponseReceived(Request request, Response response) {
            System.out.println("response received :"+response.getStatusText());
            this.httpCode = response.getStatusCode();
            this.response = response.getText();
            Header[] headers =response.getHeaders();
            for(Header header : headers){
            	GwtRestClient.this.responseHeaders.put(header.getName(), header.getValue());
            }
            try{
            callCallback(callback, httpCode, this.response);
            }catch (RuntimeException ex ){
                ex.printStackTrace();
                throw ex;
            }
            
        }

        /**
         * @{@inheritDoc }
         */
        @Override
        public void onError(Request request, Throwable exception) {
            System.out.println("error:"+exception.getMessage());
            callback.onException(new HttpException("Unable to execute request : " + exception.getMessage(), exception));
        }
    }
}
