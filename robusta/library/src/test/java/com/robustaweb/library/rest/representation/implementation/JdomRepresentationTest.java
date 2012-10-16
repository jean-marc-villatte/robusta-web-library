package com.robustaweb.library.rest.representation.implementation;

import com.robustaweb.library.commons.MyRobusta;
import com.robustaweb.library.rest.representation.RepresentationTest;
import com.robustaweb.library.rest.representation.XmlRepresentationTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Nicolas Zozol
 * Date: 05/10/12
 */
public class JdomRepresentationTest extends XmlRepresentationTest {

    @Before
    public void setUp() {
        this.isJson = false;
        this.representation = new JdomRepresentation(this.xml);
        MyRobusta.setDefaultRepresentation(new JdomRepresentation());
    }




}
