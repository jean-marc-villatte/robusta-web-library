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

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.robustaweb.library.commons.exception.HttpException;
import com.robustaweb.library.commons.util.FileUtils;
import com.robustaweb.library.commons.util.ListUtils;
import com.robustaweb.library.rest.HttpMethod;

/** 
 * Simple REST Http client wrapping the Sun Client. Compare to Apache, many JVM have this client.
 * @author Nicolas Zozol - Edupassion.com - Robusta Web nzozol@edupassion.com
 */
public class SunRestClient extends AbstractSynchronousRestClient<HttpURLConnection> {


    static Proxy proxy;
    HttpURLConnection http;

    /**
     * Constructor
     * @param applicationPath default path of the request
     */
    public SunRestClient(String applicationPath) {
        if (!applicationPath.startsWith("http")) {
            throw new IllegalArgumentException("Application URI should start with http !");
        }
        SunRestClient.applicationUri = applicationPath;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String executeMethod(final HttpMethod method, final String url, final String requestBody) throws HttpException {

        assert url.startsWith("http");

        URL u;
        try {
            u = new URL(url);
        } catch (MalformedURLException ex) {
            throw new HttpException("malformedURI", ex);
        }

        try {

            
            if (SunRestClient.proxy != null){
                http = (HttpURLConnection) u.openConnection(SunRestClient.proxy);
            }else{
                http = (HttpURLConnection) u.openConnection();
            }
             

            http.addRequestProperty("Content-type", this.contentType);
            if (authorizationValue != null) {
                http.addRequestProperty("Authorization", SunRestClient.authorizationValue);
            }

            http.setRequestMethod(method.toString());
            http.setDoInput(true);
            switch (method) {
                case PUT:
                case POST:
                    http.setDoOutput(true);
                    /* if there is something to put in the requestBody*/
                    if (this.requestBody != null && this.requestBody.length() >= 0) {

                        DataOutputStream wr = new DataOutputStream(http.getOutputStream());
                        wr.writeBytes(requestBody);
                        wr.flush();
                        wr.close();
                    }
                    break;
            }
            this.response = FileUtils.readInputStream(http.getInputStream());
            this.responseHeaders = readHeaders(http);
            
            
           
            return this.response;
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new HttpException(ex.getMessage(), ex);
        } finally {
            clean();
        }        
    }
    
    private Map<String, String> readHeaders(HttpURLConnection http){
    	Map<String, List<String>> maps = http.getHeaderFields();
    	Map<String, String> map = new HashMap<String, String>();
    	for(String key : maps.keySet()){
    		map.put(key, ListUtils.join(";",maps.get(key)));
    	}
    	return map;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String encodeParameter(String nameOrValue) {
        try {
            return URLEncoder.encode(nameOrValue, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Can't encode " + nameOrValue);
        }
    }

    /**
     * Set a proxy for the class
     * @param proxy
     */
    public static void setProxy(Proxy proxy) {
        SunRestClient.proxy = proxy;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public HttpURLConnection getUnderlyingClient() {
        return http;
    }

    

}
