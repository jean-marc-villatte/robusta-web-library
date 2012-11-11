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

import com.robustaweb.library.commons.exception.HttpException;
import com.robustaweb.library.commons.util.FileUtils;
import com.robustaweb.library.rest.HttpMethod;
import com.robustaweb.library.rest.client.Callback;

/**
 * Create some REST requests with the classic Sun's Http client. It opens a connection with :<br/>
 * <pre>http = (HttpURLConnection) u.openConnection(AsynchronousRestClientSun.proxy);</pre>
 * 
 * After each request, contentType is reset to defaultContentType and requestBody is empty
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public class AsynchronousRestClientSun extends AbstractAsynchronousRestClient<HttpURLConnection> {

    static Proxy proxy;
    Thread requestThread;
    HttpURLConnection http;

    /**
     * Create a client with it's application uri. If applicationUri :
     * <ul>
     *  <li>starts with http : a classic Http connexion will be used</li>
     * <li>starts with https : the client will attempt a Https connexion</li>
     * <li>doesn't starts with http* : an exception is thrown ; there is no relative path possible here</li>
     * </ul>
     * @param applicationUri : URI of the application
     */
    public AsynchronousRestClientSun(String applicationUri) {
        checkConstructorUri(applicationUri);
        AsynchronousRestClientSun.applicationUri = applicationUri;
    }

    /**
     * @todo1 : should go in a Responsability class
     * @param method
     * @param relativePath
     * @param parameters
     * @param callback
     * @throws HttpException
     */
    @Override
    protected void executeMethod(final HttpMethod method, final String url, final String requestBody, final Callback callback) throws HttpException {

        requestThread = new Thread() {

            @Override
            public void run() {

                assert url.startsWith("http");

                URL u;
                try {
                    u = new URL(url);
                } catch (MalformedURLException ex) {
                    throw new HttpException("malformedURI", ex);
                }

                try {

                    http = (HttpURLConnection) u.openConnection();

                    http.addRequestProperty("Content-type", contentType);
                    if (authorizationValue != null) {
                        http.addRequestProperty("Authorization", authorizationValue);
                    }
                    http.setRequestMethod(method.toString());
                    http.setDoInput(true);
                    //TODO : all

                    switch (method) {
                        case PUT:
                        case POST:
                            http.setDoOutput(true);
                            /* if there is something to put in the requestBody*/
                            if (requestBody != null && requestBody.length() >= 0) {
                                DataOutputStream wr = new DataOutputStream(http.getOutputStream());
                                wr.writeBytes(requestBody);
                                wr.flush();
                                wr.close();
                            }
                            break;
                    }
                    response = FileUtils.readInputStream(http.getInputStream());

                    //handling the callback
                    callCallback(callback, httpCode, response);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new HttpException(ex.getMessage(), ex);
                } finally {
                    clean();
                }
            }
        };
        requestThread.start();


    }

    /**
     * @{@inheritDoc }
     */
    @Override
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

    /**
     * This will change the defaultContentType of SunRestClient
     * @param contentType
     */
    public static void setDefaultContentType(String contentType) {
        AsynchronousRestClientSun.setDefaultContentType(defaultContentType);
    }

    /**
     * Because the call is asynchronous, you may use the join() method 
     * @return
     */
    @Override
    public HttpURLConnection getUnderlyingClient() {
        return this.http;
    }
}
