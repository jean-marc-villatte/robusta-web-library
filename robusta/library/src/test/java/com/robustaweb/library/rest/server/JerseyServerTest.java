package com.robustaweb.library.rest.server;

import com.robustaweb.library.rest.client.implementation.SunRestClient;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.TestConstants;
import org.junit.Test;

import java.util.logging.Level;

/**
 * User: Nicolas Zozol
 * Date: 23/10/12
 * Time: 22:52
 */
public class JerseyServerTest extends JerseyTest {

    protected String baseUri = "http://localhost:" + TestConstants.JERSEY_HTTP_PORT + "/";

    public JerseyServerTest() throws Exception {
        super("com.robustaweb.library.rest.controller");

        /*
        It's possible to NOT call the super() and the :
        1)  ApplicationDescriptor appDescriptor = new ApplicationDescriptor()
                .setRootResourcePackageName(resourcePackageName) // resource packages
                .setContextPath(contextPath) //context of app
                .setServletPath(servletPath); // context of spi servlet
        2)setupTestEnvironment(appDescriptor);
         */
    }

    @Test
    public void testHelloWorldRequest() {
        SunRestClient client = new SunRestClient(baseUri + "root");
        String result = client.GET("", null);
        System.out.println(result);
    }

    @Test
    public void testDeleteRequest() {
        SunRestClient client = new SunRestClient(baseUri + "root");
        String result = client.DELETE("john", null);
        System.out.println(result);
    }
}
