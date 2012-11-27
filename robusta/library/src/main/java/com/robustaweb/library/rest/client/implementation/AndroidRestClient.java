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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.robustaweb.library.commons.exception.HttpException;
import com.robustaweb.library.rest.HttpMethod;

/**
 * Rest Http Client for Android. It uses Apache Http Client 4 in asynchronous
 * way. The ApacheRestClient can also be used in synchronous way TODO : test in
 * real Android smartphone
 * 
 * @author n.zozol
 */
public class AndroidRestClient<TResponse> extends AbstractAsynchronousRestClient<DefaultHttpClient, TResponse, Callback> {

	DefaultHttpClient client;
	Thread requestThread;




	public AndroidRestClient(String applicationUri) {
        super(applicationUri);
		AbstractRestClient.applicationUri = applicationUri;
	}

    @Override
    protected void executeMethod(HttpMethod method, String url, Callback callback) throws HttpException {
        //TODO Defualt implementation

    }

    @Override
    protected void executeMethod(final HttpMethod method, final String url, String requestBody, final Callback callback) throws HttpException {
        requestThread = new Thread() {

            @Override
            public void run() {

                HttpRequestBase meth = null;
                try {
                    switch (method) {
                        case GET:
                            meth = new HttpGet(url);

                            break;
                        case POST:
                            meth = new HttpPost(url);
                            break;
                        case DELETE:
                            meth = new HttpDelete(url);
                            break;
                        case PUT:
                            meth = new HttpPut(url);
                            break;
                        default:
                            meth = new HttpEntityEnclosingRequestBase() {
                                @Override
                                public String getMethod() {
                                    return method.getMethod();
                                }
                            };
                            break;
                    }
                    // this.builder = new RequestBuilder(meth, url);

                    if (contentType != null && !contentType.isEmpty()) {

                        meth.addHeader("Content-Type", contentType);
                    }
                    if (AndroidRestClient.authorizationValue != null
                            && AndroidRestClient.authorizationValue.length() > 0) {
                        meth.addHeader("Authorization",
                                AndroidRestClient.authorizationValue);
                    }

                    HttpContext localContext = new BasicHttpContext();
                    HttpResponse response = client.execute(meth, localContext);
                    callback.onSuccess(response.toString(), response.getStatusLine().getStatusCode());//TODO : to be tested

                    // headers response
                    HeaderIterator it = response.headerIterator();
                    while (it.hasNext()) {
                        Header header = it.nextHeader();
                        responseHeaders
                                .put(header.getName(), header.getValue());
                    }

                } catch (Exception ex) {
                    callback.onException(ex);
                } finally {
                    clean();
                }
            }
        };

    }

    /**
	 * {@inheritDoc }
	 */
	public void join() {
		try {
			requestThread.join();
		} catch (InterruptedException ex) {
			throw new HttpException("Can't join the client's thread : "
					+ ex.getMessage(), ex);
		}
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
	 * {@inheritDoc }
	 */
	@Override
	public DefaultHttpClient getUnderlyingClient() {
		return this.client;
	}

}
