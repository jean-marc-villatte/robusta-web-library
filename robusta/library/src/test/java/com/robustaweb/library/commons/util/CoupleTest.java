/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.robustaweb.library.commons.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robusta web
 */
public class CoupleTest {

    public CoupleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testToString() {
        Couple <Integer, String> cp = new Couple<Integer, String>(3, "Three");
        assertTrue(cp.toString().equals("(3, Three)") );


        cp = new Couple<Integer, String>(null, "Three");
        assertTrue(cp.toString().contains("null"));
    }

    @Test
    public  void testEquelas(){
        Couple <Integer, String> cp = new Couple<Integer, String>(3, "Three");
        Couple  cp2 = new Couple(3, "Three");
        assertTrue(cp.equals(cp2) );
    }


    @Test
    public void testGetReversedCouple() {
        Couple <Integer, String> cp = new Couple<Integer, String>(3, "Three");
        assertTrue(cp.reverse().toString().equals("(Three, 3)") );

    }

}