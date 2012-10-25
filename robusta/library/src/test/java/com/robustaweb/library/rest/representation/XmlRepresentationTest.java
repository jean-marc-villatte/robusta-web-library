package com.robustaweb.library.rest.representation;

import static org.junit.Assert.*;

/**
 * Created by Nicolas Zozol
 * Date: 16/10/12
 */
public abstract class XmlRepresentationTest extends RepresentationTest {

    protected XmlRepresentation getSchoolRepresentation() {
        return (XmlRepresentation) this.representation;
    }


    public void testGetAttribute() {

        //check that there is no id on school
        assertNull(this.getSchoolRepresentation().getAttribute("id") );
        assertEquals("toomuch", this.getSchoolRepresentation().getAttribute("price", "id") );

        this.getSchoolRepresentation().setAttribute("id", "bsu");
        assertEquals("bsu", getSchoolRepresentation().getAttribute("id"));



    }
}
