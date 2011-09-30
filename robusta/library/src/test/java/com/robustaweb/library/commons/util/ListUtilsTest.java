/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.robustaweb.library.commons.util;

import com.robustaweb.library.commons.util.ListUtils;
import java.util.ArrayList;
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
public class ListUtilsTest {

    public ListUtilsTest() {
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
    public void testSetUnicity() {

        ArrayList list = new ArrayList();
        list.add("3");
        list.add(3);
        list.add(4);
        list.add("3");
        list.add("Three");
        list.add("4");
        list.add("3");
        list.add("11");
        list.add(4);
        list.add(list);
        
         ListUtils.setUnicity(list);
         assertTrue(list.size() == 7 );
         assertTrue(list.indexOf(3)==1 );
         assertTrue(list.indexOf(4)==2 );


    }

    @Test
    public void testListedeLignesVide_int() {
    }

    @Test
    public void testListedeLignesVide_0args() {
    }

    @Test
    public void testListeVide() {
    }

    @Test
    public void testConvertArrayToArrayList() {
    }

}