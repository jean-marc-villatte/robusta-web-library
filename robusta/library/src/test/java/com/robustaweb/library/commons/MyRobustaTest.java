/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.robustaweb.library.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.robustaweb.library.gwt.CodecGwt;
import com.robustaweb.library.security.Codec;

/**
 *
 * @author n.zozol
 */
public class MyRobustaTest {

    public MyRobustaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetGwtModuleName() {
        String expected = "";
        assertEquals("Bad Module name", expected, MyRobusta.getGwtModuleName());
    }

    @Test
    public void testGetApplicationUri() {
        assertTrue("application URI not empty", MyRobusta.applicationUri == null);
    }

    
    @Test
    public void testGetCodec() {

        //if isGwt
        Codec expected = null;
        assertFalse(MyRobusta.isGwt());
        assertEquals("bad codec when nothing set", expected, MyRobusta.getCodec());
        MyRobusta.setGwt(true, true);

        assertTrue("bad instance", MyRobusta.getCodec() instanceof CodecGwt);


    }
    

    

    @Test
    public void testIsGwt() {
        assertFalse("bad default set", MyRobusta.isGwt());
    }

    
    @Test
    public void testGetI18nProcess() {
        fail("I18nProcess not handle yet");
    }

    @Test
    public void testSetI18nProcess() {
        fail("I18nProcess not handle yet");
    }

    @Test
    public void testGetVsx() {
        fail ("representation support not well designed yet");
    }

    @Test
    public void testSetVsx() {
        fail ("representation support not well designed yet");
    }

    @Test
    public void testGetLazyCookie() {
        fail ("ArmyLazy should change Lazy by something else");
    }

    @Test
    public void testSetLazyCookie() {
        fail ("ArmyLazy should change Lazy by something else");
    }

}