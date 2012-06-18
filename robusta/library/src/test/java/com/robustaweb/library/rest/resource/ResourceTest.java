/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.robustaweb.library.rest.resource;

import com.robustaweb.library.commons.MyRobusta;
import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.resource.implementation.UserImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author n.zozol
 */
public class ResourceTest {

    House house = new House( "Chateau de Versailles");
    UserImpl johnDoe = UserImpl.johnDoe;

    public ResourceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetId() {
        assertTrue (johnDoe.getId()>0);
        assertTrue (this.house.getId().length()>0);
    }

    @Test
    public void testSetId() {
        String formerId = house.getId();
        house.setId("White House");
        assertFalse(house.getId().equals(formerId) );
    }

    @Test
    public void testGetRepresentation() throws Exception {
        Representation representation = house.getRepresentation();
        assertTrue(representation != null);
        assertTrue(representation.get("id").equals(house.getId()));


        representation = johnDoe.getRepresentation();
        assertTrue(representation != null);
        assertTrue(representation.get("id").equals(johnDoe.getId().toString()));
        assertTrue(representation.getNumber("id") == johnDoe.getId());

    }

    @Test
    public void testGetRelativeURI() {
        String expected = "house/"+this.house.getId();

        //assertTrue("",);
    }

  

}


class House implements Resource<String>{

    String id;

    public House(String id) {
        this.id = id;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Representation getRepresentation() throws RepresentationException {
        return MyRobusta.getDefaultRepresentation(this);
    }



    @Override
    public String toString() {
        return "House "+id;
    }

    @Override
    public String getPrefix() {
        return "house";
    }

    @Override
    public String getListPrefix() {
        return "houses";
    }

    @Override
    public CoupleList<String, Object> serialize() throws RepresentationException {
        return CoupleList.<String, Object>build("id", id);
    }


}
