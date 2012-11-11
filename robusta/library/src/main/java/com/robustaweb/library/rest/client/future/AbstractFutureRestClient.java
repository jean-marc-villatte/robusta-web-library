package com.robustaweb.library.rest.client.future;

import com.robustaweb.library.commons.exception.HttpException;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.commons.util.FileUtils;
import com.robustaweb.library.rest.HttpMethod;
import com.robustaweb.library.rest.client.Callback;
import com.robustaweb.library.rest.client.RunnableCallback;
import com.robustaweb.library.rest.client.implementation.AbstractRestClient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;

/**
 * Robusta Code Library
 * User: Nicolas
 * Date: 03/11/12
 * Time: 15:24
 */
public abstract class AbstractFutureRestClient<Client> extends AbstractRestClient<Client> implements FutureRestClient<Client, Future<String>> {

    Thread requestThread;
    HttpURLConnection http;
    private static final int NTHREDS = 10;
    ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);




    @Override
    public Future<String> GET(String path, CoupleList<String, Object> parameters) throws HttpException {
        prepareMethod();
        String url = encodeUrl(applicationUri, path, parameters);

        //TODO : extends AbstractSynchronousRestClient<Client, Response> and work on executeMethod
        MethodCallable callable = new MethodCallable(HttpMethod.GET, url, "");
        return executor.submit(callable);

    }


    public void GET(String path, CoupleList<String, Object> parameters, Callback callback) throws HttpException {
        prepareMethod();
        String url = encodeUrl(applicationUri, path, parameters);

        //TODO : extends AbstractSynchronousRestClient<Client, Response> and work on executeMethod
        MethodCallable callable = new MethodCallable(HttpMethod.GET, url, "");

        Future<String> future = executor.submit(callable);
        try {
            String response = future.get();
            int code = callable.getCode();
            callback.onSuccess(response, code);
        } catch (Exception e) {
            callback.onException(e);
        }

    }







    class MethodCallable implements Callable<String>{
        HttpMethod method;
        String url;
        String requestBody;
        String response;


        MethodCallable(HttpMethod method, String url, String requestBody) {
            this.method = method;
            this.url = url;
            this.requestBody = requestBody;
        }

        @Override
        public String call( ){
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
                return response;

            } catch (IOException ex) {
                ex.printStackTrace();
                throw new HttpException(ex.getMessage(), ex);
            } finally {
                clean();
            }
        }

        public String getResponse(){
            if (response == null){
                throw new IllegalStateException("Response has not arrived yet");
            }
            return response;
        }

        public int getCode() throws IOException {
            if (response == null){
                throw new IllegalStateException("Response has not arrived yet");
            }
            return http.getResponseCode();
        }

    }


}
