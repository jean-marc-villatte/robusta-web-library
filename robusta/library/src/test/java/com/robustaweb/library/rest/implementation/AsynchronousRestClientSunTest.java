/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robustaweb.library.rest.implementation;

import com.robustaweb.library.rest.client.implementation.AsynchronousRestClientSun;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.robustaweb.library.commons.exception.RestException;
import com.robustaweb.library.rest.client.AsynchronousRestClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.robustaweb.library.rest.client.SimpleCallback;
import com.robustaweb.library.rest.client.Callback;
import static org.junit.Assert.*;

/**
 *
 * @author robusta web
 */
public class AsynchronousRestClientSunTest {

    AsynchronousRestClientSun client;

    public AsynchronousRestClientSunTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        client = new AsynchronousRestClientSun("http://www.robustaweb.com/library/tests/");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSetApplicationUri() {
    }

    @Test
    public void testSetAuthorizationValue() throws Exception{
        client.setAuthorizationValue("James Bond");

        AsyncCallback<String> cb1 = new AsyncCallback<String>(){

            public void onFailure(Throwable caught) {
                System.out.println("fail : "+client.getHttpCode());
            }

            public void onSuccess(String result) {
                System.out.println("success");
            }
            
        };

        //@todo2 : use Logger rather than sout
        Callback cb = new SimpleCallback(client) {

            @Override
            public void onSuccess(String response) {
                System.out.println("success");
            }

            @Override
            public void onFailure(RestException ex) {
                ex.printStackTrace();
                System.out.println("fail : "+client.getHttpCode());
            }           

            
            @Override
            public void onComplete() {
                System.out.println("complete");
            }
        };

        client.GET("xml.xml", null, cb);
        client.join();
        assertTrue(client.getHttpCode()<300);
        System.out.println("finished");
        System.out.println("result :\n"+client.getResponse());


    }

    @Test
    public void testSetContentType() {
    }

    @Test
    public void testSetRequestBody() {
    }

    @Test
    public void testExecuteGet() throws Exception {
    }

    @Test
    public void testExecuteMethod() {
    }

    @Test
    public void testExecutePost() throws Exception {
    }

    @Test
    public void testExecutePut() throws Exception {
    }

    @Test
    public void testExecuteDelete() throws Exception {
    }

    @Test
    public void testGetLastStatusCode() {
    }

    @Test
    public void testGetLastResponse() {
    }
}
