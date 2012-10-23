package com.robustaweb.library.rest.server;

import com.robustaweb.library.rest.client.implementation.SunRestClient;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * User: Nicolas
 * Date: 23/10/12
 * Time: 22:52
 */
public class JerseyServer {

    protected HttpServer server;
    Thread killerThread;
    protected  String baseUri = "http://localhost:8080/";
    /**
     * Maximum duration of the server
     */
    public static long duration = 10*1000;

    protected ResourceConfig  getResources(){
        ResourceConfig rc = new PackagesResourceConfig("javax.servlet.http","com.robustaweb.library.rest.controller");
        return rc;
    }

    protected URI getBaseUri() throws URISyntaxException {
        return new URI(baseUri);
    }

    @Before
    public void startServer(){

        try {
            this.server = HttpServerFactory.create(getBaseUri(), getResources());
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.out.println("Can't start server : URI is no good");
            e.printStackTrace();
        }

        killerThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(duration);
                    server.stop(0);
                } catch (InterruptedException e) {
                    System.out.println("Interrupting killerThread ; no problem");
                }
            }
        };

    }

    @After
    public void stopServer(){
        killerThread.interrupt();
        server.stop(0);
        System.out.println("Server stopped");
    }


    @Test
    public void testServerStart() throws InterruptedException {
        //startServer();
        Thread.sleep(2000);
        System.out.println("Stopping the server");
        //stopServer();
    }


    @Test
    public void testHelloWorldRequest(){
        SunRestClient client = new SunRestClient(baseUri+"root");
        String result = client.GET("", null);
        System.out.println(result);
    }

    @Test
    public void testDeleteRequest(){
        SunRestClient client = new SunRestClient(baseUri+"root");
        String result = client.DELETE("john", null);
        System.out.println(result);
    }

}
