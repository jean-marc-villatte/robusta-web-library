package com.robustaweb.library.rest.representation.implementation;

import com.robustaweb.library.commons.MyRobusta;
import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.util.FileUtils;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.representation.RepresentationTest;
import com.robustaweb.library.rest.resource.Resource;
import com.robustaweb.library.rest.resource.implementation.UserImpl;
import org.json.simple.JSONArray;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;


/**
 * User: Nicolas
 * Date: 05/08/12
 * Time: 20:24
 */
public class JsonSimpleRepresentationTest extends RepresentationTest {


    @Before
    public void setUp() {
        this.isJson = true;
        this.representation = new JsonSimpleRepresentation(this.jsonContent, true);
        MyRobusta.setDefaultRepresentation(new JsonSimpleRepresentation(null));
    }

    @After
    public void tearDown() {
    }



    @Test
    public void testAddResourceEagerExceptions(){
        representation = representation.fetch("school");
        Representation jsonRepresentation = new JsonSimpleRepresentation(null);
        Representation jdomRepresentation = new JdomRepresentation();

        //Getting a Jdom resource
        MyRobusta.setDefaultRepresentation(jdomRepresentation);
        Resource nicolas = new UserImpl(24L, "a@a.com", "nicolas", "zozol");
        assertTrue(nicolas.getRepresentation() instanceof JdomRepresentation);



        boolean catched = false;
        try{
            jsonRepresentation.add(nicolas, true);
        }catch (RepresentationException ex){
            catched = true;
        }
        assertTrue(catched);

        //setting back ; probably not needed
        MyRobusta.setDefaultRepresentation( new JsonSimpleRepresentation(null));
    }




}
