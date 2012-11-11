package com.robustaweb.library.rest.client.future;

import com.robustaweb.library.commons.exception.HttpException;
import com.robustaweb.library.commons.util.CoupleList;

import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Robusta Code Library
 * User: Nicolas
 * Date: 03/11/12
 * Time: 22:04
 */
public class JdkFutureRestClient extends AbstractFutureRestClient<HttpURLConnection> {


    HttpURLConnection http;

    /**
     * Constructor
     *
     * @param applicationPath default path of the request
     */
    public JdkFutureRestClient(String applicationPath) {
        if (!applicationPath.startsWith("http")) {
            throw new IllegalArgumentException("Application URI should start with http !");
        }
        applicationUri = applicationPath;
    }

    @Override
    protected String encodeParameter(String nameOrValue) {
        //TODO Defualt implementation
        return null;
    }

    @Override
    public Future<String> POST(String relativePath, CoupleList<String, Object> parameters) throws HttpException {
        //TODO Defualt implementation
        return null;
    }

    @Override
    public Future<String> PUT(String relativePath, CoupleList<String, Object> parameters) throws HttpException {
        //TODO Defualt implementation
        return null;
    }

    @Override
    public Future<String> DELETE(String relativePath, CoupleList<String, Object> parameters) throws HttpException {
        //TODO Defualt implementation
        return null;
    }

    @Override
    public Future<String> OTHER(String method, String relativePath, CoupleList<String, Object> parameters) throws HttpException {
        //TODO Defualt implementation
        return null;
    }

    @Override
    public HttpURLConnection getUnderlyingClient() {
        return http;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String uri = "http://www.bing.com/search";
        JdkFutureRestClient client = new JdkFutureRestClient(uri);
        final Future<String> f = client.GET("", CoupleList.build("q", "john"));
        final long t0 = System.currentTimeMillis();

        Thread th = new Thread() {
            @Override
            public void run() {
                String str = null;
                try {
                    str = f.get();
                    final long t1 = System.currentTimeMillis() - t0;
                    System.out.println(t1 + " : =>" + str.substring(0, 350));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        th.start();


    }
}
