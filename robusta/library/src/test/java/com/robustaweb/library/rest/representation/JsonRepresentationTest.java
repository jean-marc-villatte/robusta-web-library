package com.robustaweb.library.rest.representation;

import com.robustaweb.library.commons.MyRobusta;
import com.robustaweb.library.rest.representation.implementation.JsonSimpleRepresentation;
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
    public void testAddAllResources(){

    }

    @Test
    public void testGetValuesFromArray(){

    }

    @Test
    public void testGetNumbersFromArray(){

    }


    @Test
    public void testGetNumbersFromArrayWithClass(){

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
        List<Integer> ids =this.getRepresentation().pluckNumbers("id", (Integer)1);
        assertTrue(ids.size() == 4);
        assertTrue(ids.get(3) instanceof Integer);
        assertTrue(ids.get(3) == 4);


    }



}
