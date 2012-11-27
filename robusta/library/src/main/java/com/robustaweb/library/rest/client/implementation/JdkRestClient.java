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
import com.robustaweb.library.commons.util.FileUtils;
import com.robustaweb.library.commons.util.ListUtils;
import com.robustaweb.library.rest.HttpMethod;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple REST Http client wrapping the Jdk HttpURLConnection Client. Compare to Apache, many JVM have this client, and is now recommended by Android
 * @author Nicolas Zozol - Edupassion.com - Robusta Web nzozol@edupassion.com
 */
public class JdkRestClient extends AbstractSynchronousRestClient<HttpURLConnection, String> {


    static Proxy proxy;
    HttpURLConnection http;

    /**
     * Constructor
     * @param applicationPath default path of the request
     */
    public JdkRestClient(String applicationPath) {
       super(applicationPath);
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
    public void setHeader(String name, String value) {
        http.setRequestProperty(name, value);

    }

    @Override
    protected String executeMethod(HttpMethod method, String url) throws HttpException {
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

            return response;
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
    protected String executeMethod(final HttpMethod method, final String url, final String requestBody) throws HttpException {

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

            this.httpCode = http.getResponseCode();
            httpInputStream = http.getInputStream();
            response = FileUtils.readInputStream(httpInputStream);
            this.responseHeaders = readHeaders(http);

            checkUuids();

            return response;
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
     *
     * @param proxy
     */
    public static void setProxy(Proxy proxy) {
        JdkRestClient.proxy = proxy;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public HttpURLConnection getUnderlyingClient() {
        return http;
    }


}
