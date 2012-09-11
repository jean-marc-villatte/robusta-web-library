package com.robustaweb.library.rest.representation.implementation;

import com.google.gson.Gson;
import com.robustaweb.library.commons.util.SerializationUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas Zozol
 * Date: 19/08/12
 */
public class GsonRepresentationTest {


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetItems() {

        Student johnDoe = new Student(1L, "John", "Doe");
        Student jack = new Student(2L, "Jack", "X");
        Student jim = new Student(3L, "Jim", "Y");
        johnDoe.addFriend(jack);
        johnDoe.addFriend(jim);

        jack.addFriend(jim);

        GsonRepresentation representation = new GsonRepresentation(SerializationUtils.serialize(johnDoe));
        System.out.println(representation.getDocument().toString());

    }

    @Test
    public void testIsXxx(){
        String _0 = "0", _11 = "11", _00="00";
        String _null="null";
        String _false = "false", _true = "true";
        String str0 = "'0'", str0bis = "\"0\"", strFalse = "'false'",  strTrue = "\"false\"";

        GsonRepresentation rep = new GsonRepresentation(_0);
        assertTrue(rep.isNumber());
        assertFalse(rep.isBoolean());
        assertFalse(rep.isString());
        assertFalse(rep.isNull());

        rep = new GsonRepresentation(_11);
        assertTrue(rep.isNumber());
        assertFalse(rep.isBoolean());
        assertFalse(rep.isString());
        assertFalse(rep.isNull());

        rep = new GsonRepresentation(_00);
        assertTrue(rep.isNumber());
        assertFalse(rep.isBoolean());
        assertFalse(rep.isString());
        assertFalse(rep.isNull());
    }

}



class Student {
    Long id;
    String firstName;
    String lastName;
    List<Student> friends = new ArrayList<Student>();

    Student(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public void addFriend(Student s){
        this.friends.add(s);
    }
}