package com.robustaweb.library.rest.server;

import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.rest.client.implementation.SunRestClient;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.TestConstants;
import com.sun.jersey.test.framework.util.ApplicationDescriptor;
import org.junit.Test;

import javax.ws.rs.core.UriInfo;
import java.util.logging.Level;

import static org.junit.Assert.assertTrue;

/**
 * User: Nicolas Zozol
 * Date: 23/10/12
 * Time: 22:52
 */
public class JerseyServerTest extends JerseyTest {

    protected String baseUri = "http://localhost:" + TestConstants.JERSEY_HTTP_PORT + "/";

    public JerseyServerTest() throws Exception {
        String resourcePackageName="com.robustaweb.library.rest.controller";
        //super("com.robustaweb.library.rest.controller");


        //It's possible to NOT call the super() and the :
        ///1)
        System.setProperty("container.type", TestConstants.EMBEDDED_GF_V3);
        ApplicationDescriptor appDescriptor = new ApplicationDescriptor()
                .setRootResourcePackageName(resourcePackageName) // resource packages
                .setContextPath(contextPath) //context of app
                .setServletPath(servletPath); // context of spi servlet


        //2)
        setupTestEnvironment(appDescriptor);
        // */
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
        String result = client.DELETE("jack", null);
        assertTrue(result, result.startsWith("1"));
    }




    @Test
    public void testMultiParams() throws InterruptedException {

        SunRestClient client = new SunRestClient(baseUri + "root");
        CoupleList<String, Object> params = CoupleList.<String, Object>build("multi","multi1", "multi", "multi2", "multi", "multi3");
        client.setAuthorizationValue("Johnnu");
        String result = client.POST("multi-http-params", params);
        System.out.println(result);

        System.out.println("Waiting....."+baseUri);

        Thread.sleep(6000);

    }

    @Test
    public void testVarious() throws InterruptedException {
        SunRestClient client = new SunRestClient(baseUri + "root");
        client.setAuthorizationValue("Jimob");

        String result = client.GET("various", null);
        System.out.println(result);
        Thread.sleep(2000);
    }

}
