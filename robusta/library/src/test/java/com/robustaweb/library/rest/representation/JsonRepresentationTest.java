package com.robustaweb.library.rest.representation;

import com.robustaweb.library.commons.MyRobusta;
import com.robustaweb.library.rest.representation.implementation.JdomRepresentation;
import com.robustaweb.library.rest.representation.implementation.JsonSimpleRepresentation;
import org.json.simple.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * User: Nicolas
 * Date: 03/10/12
 * Time: 23:12
 */
public class JsonRepresentationTest extends RepresentationTest{


    @Before
    public void setUp() {
        this.isJson = true;
        this.representation = new JsonSimpleRepresentation(this.jsonContent, true);
        MyRobusta.setDefaultRepresentation(this.representation);
    }



    JsonRepresentation getRepresentation(){
        return (JsonRepresentation) this.representation;
    }

    @Test
    public void testAddAll(){
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, "one", "two", "three");
        this.representation =this.getRepresentation().fetch("school");
        this.getRepresentation().addAll("more",list);
        this.representation = this.getRepresentation().fetch("more");
        assertTrue(this.getRepresentation().isArray());
        assertTrue(this.getRepresentation().has("two"));

    }

    @Test
    public void testAddAllIfExists(){
        /* Adding a list of objects should work */
        List<SomeClass> list = SomeClass.createList();
        int listSize = 5;
        assertTrue(list.size() == listSize);

        /* Adds if extists */
        this.representation = this.getRepresentation().fetch("school");
        assertTrue(this.representation.has("teachers"));

        this.getRepresentation().addAll("teachers", list);

        assertTrue(this.representation.has("teachers"));
        this.representation = this.getRepresentation().fetch("teachers");
        assertTrue(this.getRepresentation().isArray());

        JSONArray array = (JSONArray) this.getRepresentation().getDocument();
        assertTrue(array.size() == 3 + listSize);



        /* Create if not exists*/

    }

    @Test
    public void testGetValuesFromArray(){

        List<String> values = this.getRepresentation().fetch("school").fetch("teachers").getValuesFromArray();
        assertTrue(values.contains("Brian"));
    }

    @Test
    public void testGetNumbersFromArray(){
        List<Long> values = this.getRepresentation().fetch("school").fetch("years").getNumbersFromArray();
        assertTrue(values.contains(1992L));
    }


    @Test
    public void testGetNumbersFromArrayWithClass(){
        JsonRepresentation rep = this.getRepresentation().fetch("school").fetch("prices");
        List<Double> values = rep.getNumbersFromArray(Double.class);
        assertTrue(values.contains(18100.55236));
    }


    @Test
    public void testPluck(){
        this.representation = this.getRepresentation().fetch("school").fetch("students");
        List<String> firstNames =this.getRepresentation().pluck("firstname");
        assertTrue(firstNames.size() == 4);
        assertTrue(firstNames.get(2).equals("Jane"));

    }


    @Test
    public void testPluckNumbers(){

        this.representation = this.getRepresentation().fetch("school").fetch("students");
        List<Long> ages =this.getRepresentation().pluckNumbers("age");
        assertTrue(ages.size() == 4);
        assertTrue(ages.get(1) == 12L);
    }

    @Test
    public void testPluckNumbersWithClass(){

        this.representation = this.getRepresentation().fetch("school").fetch("students");
        List<Long> ids =this.getRepresentation().pluckNumbers("id", Long.class);
        assertTrue(ids.size() == 4);
        assertTrue(ids.get(3) instanceof Long);
        assertTrue(ids.get(3) == 4);


    }



}

