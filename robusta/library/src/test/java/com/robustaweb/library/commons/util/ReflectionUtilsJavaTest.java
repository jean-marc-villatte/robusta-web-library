/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.robustaweb.library.commons.util;

import com.robustaweb.library.rest.resource.implementation.UserImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author n.zozol
 */
public class ReflectionUtilsJavaTest {

    SerializationUtils serializationUtils = new SerializationUtils();
    public ReflectionUtilsJavaTest() {

    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetItems() {
        UserImpl user = UserImpl.johnDoe;
        System.out.println("couples : " + serializationUtils.serialize(user));
        assertTrue (serializationUtils.serialize(user).size() == 5);

        SuperUser user2 = new SuperUser(2L, "jj@gmail.com", "Jane", "Doe");
        System.out.println("couples : "+ serializationUtils.serialize(user2));
        int expected = 6;
        assertEquals(expected, serializationUtils.serialize(user2).size());
    }

}


class SuperUser extends UserImpl{

    String username;
    public SuperUser(long id, String email, String firstname, String lastname) {
        super(id, email, firstname, lastname);
        this.username = email;
    }

}