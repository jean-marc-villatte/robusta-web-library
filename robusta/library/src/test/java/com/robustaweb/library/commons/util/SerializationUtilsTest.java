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
public class SerializationUtilsTest {

    UserImpl johnDoe = new UserImpl(1L, "john.doe@gmail.com", "John", "Doe");

    SerializationUtils serializationUtils = new SerializationUtils();
    public SerializationUtilsTest() {

    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetItems() {
        UserImpl user = johnDoe;
        int expected = new String[]{"id", "email", "firstName", "lastName"}.length;
        CoupleList serialization = serializationUtils.serialize(user);
        int serializedSize = serialization.size();
        assertEquals(expected, serializedSize);

        SuperUser user2 = new SuperUser(2L, "jj@gmail.com", "Jane", "Doe");
        expected ++;
        serializedSize = serializationUtils.serialize(user2).size();
        assertEquals(expected, serializedSize);
    }

}


class SuperUser extends UserImpl{

    String username;
    public SuperUser(long id, String email, String firstname, String lastname) {
        super(id, email, firstname, lastname);
        this.username = email;
    }

}