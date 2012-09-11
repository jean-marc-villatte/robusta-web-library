package com.robustaweb.library.mapper.implementation;

import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by Nicolas Zozol
 * Date: 12/08/12
 */
public class GsonMapperTest {
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetItems() {
        Gson gson = new Gson();
        Student johnDoe = new Student(1L, "John", "Doe");
        String str = gson.toJson(johnDoe);
        System.out.println(str);
    }



}


class Student {
    Long id;
    String firstName;
    String lastName;

    Student(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}