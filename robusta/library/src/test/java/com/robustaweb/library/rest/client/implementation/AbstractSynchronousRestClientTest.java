package com.robustaweb.library.rest.client.implementation;

import com.robustaweb.library.commons.util.CoupleList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author n.zozol
 */
public class AbstractSynchronousRestClientTest {

    String applicationUri = "http://localhost:8084/robusta/rest/";
    AbstractSynchronousRestClient[] clients = new AbstractSynchronousRestClient[]{
        new JdkRestClient(applicationUri),
        new ApacheRestClient(applicationUri)};
    String expected, actual;

    public AbstractSynchronousRestClientTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testPOST() {
    }

    @Test
    public void testGET() {
        expected = "ok\n";
        for (AbstractSynchronousRestClient client : clients) {
            actual = client.GET("generic", null);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testPUT() {
    }

    @Test
    public void testDELETE() {
    }

    @Test
    public void testEncodeParameter() {
         expected = "12\n";
        for (AbstractSynchronousRestClient client : clients) {
            actual = client.GET("generic", CoupleList.build("p1", 12));
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testExecuteMethod() {
    }
}
