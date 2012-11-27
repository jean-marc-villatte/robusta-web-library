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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.robustaweb.library.commons.exception.HttpException;
import com.robustaweb.library.commons.exception.RestException;
import com.robustaweb.library.commons.util.FileUtils;
import com.robustaweb.library.commons.util.ListUtils;
import com.robustaweb.library.rest.HttpMethod;
import com.robustaweb.library.rest.representation.Representation;

/**
 * Create some REST requests with the classic Sun's Http client. It opens a connection with :<br/>
 * <pre>http = (HttpURLConnection) u.openConnection(AsynchronousRestClientSun.proxy);</pre>
 * 
 * After each request, contentType is reset to defaultContentType and requestBody is empty
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public class AsynchronousRestClientSun<TResponse> extends AbstractAsynchronousRestClient<HttpURLConnection, TResponse, Callback> {

    static Proxy proxy;
    Thread requestThread;
    HttpURLConnection http;
    String [] acceptedResponseAbstractions = new String[]{"STRING", "REPRESENTATION", "INPUTSTREAM"};


    String acceptedResponse = "STRING";
    Representation responseRepresentation = null;
    InputStream inputStream;

    public <T extends TResponse>AsynchronousRestClientSun(String applicationUri, Class<T> responseClazz){
        super(applicationUri);
        /*if (responseClazz.equals(String.class)){
            acceptedResponse = AcceptedResponse.STRING;
        }else if (InputStream.class.isAssignableFrom(responseClazz)){
            acceptedResponse = AcceptedResponse.INPUTSTREAM;
        }else if (Representation.class.isAssignableFrom(responseClazz)){
            acceptedResponse = AcceptedResponse.REPRESENTATION;
            throw new IllegalArgumentException("Use the constructor with Representation parameter, and an instance of it");
        }else {
            throw new IllegalArgumentException("Only String, InputStream or Representations are accepted");
        }*/
    }

    public AsynchronousRestClientSun(String applicationUri, Representation representation){
        super(applicationUri);
        acceptedResponse = ACCEPTING_REPRESENTATION;
        this.responseRepresentation = representation;
    }




    /**
     * Configure the connection with url and optional proxy. Content-type and Autorization headers will be added later.
     * Override this method if you want to add custom headers or configuration
     *
     * @param url
     * @return
     * @throws IOException
     */
    public HttpURLConnection getConnection(final String url) throws IOException {
        if (url == null || !url.startsWith("http")) {
            throw new HttpException("Url should start with http or https");
        }

        URL u;
        try {
            u = new URL(url);
        } catch (MalformedURLException ex) {
            throw new HttpException("malformed URI with : "+url, ex);
        }


        if (proxy != null) {
            http = (HttpURLConnection) u.openConnection(proxy);
        } else {
            http = (HttpURLConnection) u.openConnection();
        }

        return http;

    }


    @Override
    protected void executeMethod(HttpMethod method, String url, Callback callback) throws HttpException {
        HttpURLConnection http;
        InputStream httpInputStream = null;

        String response = "";

        try {

            http = getConnection(url);
            addRequestHeaders();
            http.setRequestMethod(method.toString());
            http.setDoInput(false);

            this.httpCode = http.getResponseCode();
            httpInputStream = http.getInputStream();
            response = FileUtils.readInputStream(httpInputStream);
            this.responseHeaders = readHeaders(http);

            checkUuids();
            callCallback(callback, this.httpCode, response);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new HttpException(ex.getMessage(), ex);
        } finally {
            //cleaning for next request
            clean();
            //closing streams
            try {
                if (httpInputStream != null) {
                    httpInputStream.close();
                }
            } catch (IOException e) {
                throw new HttpException("Can't close the stream", e);
            }
        }
    }



    /**
     * {@inheritDoc }
     */
    @Override
    protected void executeMethod(final HttpMethod method, final String url, final String requestBody, Callback callback) throws HttpException {

        switch (method) {
            case GET:
            case DELETE:
                throw new IllegalArgumentException("Can't send a body on GET or DELETE method. Use directly getUnderlyingClient() if you really want to do this.");
        }

        InputStream httpInputStream = null;
        OutputStream httpOutputStream = null;
        DataOutputStream wr = null;
        String response = "";
        try {

            http = getConnection(url);
            addRequestHeaders();
            http.setRequestMethod(method.toString());
            http.setDoInput(true);

            /* if there is something to put in the requestBody*/
            if (requestBody != null && requestBody.length() >= 0) {
                httpOutputStream = http.getOutputStream();
                wr = new DataOutputStream(httpOutputStream);
                wr.writeBytes(requestBody);
                wr.flush();
            }

            httpInputStream = http.getInputStream();
            response = FileUtils.readInputStream(httpInputStream);
            this.responseHeaders = readHeaders(http);

            checkUuids();

           callCallback(callback, httpCode, response);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new HttpException(ex.getMessage(), ex);
        } finally {
            //cleaning for next request
            clean();
            //closing streams
            try {
                if (wr != null) {
                    wr.close();
                }
                if (httpOutputStream != null){
                    httpOutputStream.close();
                }
                if (httpInputStream != null) {
                    httpInputStream.close();
                }
            } catch (IOException e) {
                throw new HttpException("Can't close the stream", e);
            }
        }
    }

    private Map<String, String> readHeaders(HttpURLConnection http) {
        Map<String, List<String>> maps = http.getHeaderFields();
        Map<String, String> map = new HashMap<String, String>();
        for (String key : maps.keySet()) {
            map.put(key, ListUtils.join(";", maps.get(key)));
        }
        return map;
    }


    /**
     * @{@inheritDoc }
     */
    public void join() {
        try {
            requestThread.join();
        } catch (InterruptedException ex) {
            throw new HttpException("Can't join the client's thread : " + ex.getMessage(), ex);
        }
    }

    /**
     * Set the proxy
     * @param proxy java.net Proxy
     * @see Proxy
     */
    public static void setProxy(Proxy proxy) {
        AsynchronousRestClientSun.proxy = proxy;
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


    @Override
    public void setHeader(String name, String value) {
        //TODO Defualt implementation

    }

    /**
     * Because the call is asynchronous, you may use the join() method 
     * @return
     */
    @Override
    public HttpURLConnection getUnderlyingClient() {
        return this.http;
    }

    /**
     * calls the callback succes or failure, using the httpcode
     * @param httpCode
     * @param response
     * @param callback
     */
    protected void callCallback(Callback callback, int httpCode, String response) {

        if (httpCode >= 200 && httpCode < 300) {
            callback.onSuccess(response, httpCode);
        } else if (httpCode >= 300 && httpCode < 400) {
            //no success
            //TODO : Everything to do here : redirection and caching
            //Does the client auto follow ?
        } else if (httpCode >= 400 && httpCode < 600) {
            callback.onFailure(new RestException(httpCode, response));
        } else if (httpCode < 600) {
            callback.onFailure(new RestException(httpCode, response));
        }
        callback.onComplete();
    }
}
