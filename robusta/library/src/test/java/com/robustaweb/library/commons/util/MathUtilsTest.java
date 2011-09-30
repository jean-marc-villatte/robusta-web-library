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
public class MathUtilsTest {

    public MathUtilsTest() {
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
    public void testEquals() {

        assertFalse(MathUtils.approximativelyEquals(27, 31.1, 3));

        assertTrue(MathUtils.approximativelyEquals(27.444445, 27.444444444444, 3));

        assertTrue(MathUtils.approximativelyEquals(27, 27.000000, 3));

        assertTrue(MathUtils.approximativelyEquals(27.0000002, 27.000000, 3));

        boolean b=MathUtils.approximativelyEquals(1.2352, 1.2357, 3);
        assertTrue(b==true );

    }

    @Test
    public void testApproximate() {

        System.out.println("round .5:" + Math.round(0.5));

        String s1 = MathUtils.approximate(12.2233, 2);
        assertTrue(s1, s1.equals("12.22"));


        String s2 = MathUtils.approximate(12.0, 2);
        assertTrue(s2, s2.equals("12"));

        /* More difficult with dangerous approximation */
        String s3 = MathUtils.approximate(12.2283, 2);
        assertTrue(s3, s3.equals("12.23"));


        String s4 = MathUtils.approximate(.2283, 2);
        assertTrue(s4, s4.equals("0.23"));

        s4 = MathUtils.approximate(0, 2);
        assertTrue(s4, s4.equals("0"));

        s4 = MathUtils.approximate(0., 2);
        assertTrue(s4, s4.equals("0"));


        s4 = MathUtils.approximate(19, 2);
        assertTrue(s4, s4.equals("19"));

        s4 = MathUtils.approximate(19.6, 0);
        assertTrue(s4, s4.equals("20"));

        s4 = MathUtils.approximate(19.5, 0);
        assertTrue(s4, s4.equals("20"));

        s4 = MathUtils.approximate(19.5, 1);
        assertTrue(s4, s4.equals("19.5"));

        s4 = MathUtils.approximate(19.6, 1);
        assertTrue(s4, s4.equals("19.6"));


    }


}