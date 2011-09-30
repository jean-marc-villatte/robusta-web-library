/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.robustaweb.library.commons.util;

import com.robustaweb.library.commons.util.FileUtils;
import java.io.File;
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
public class FileUtilsTest {

    public FileUtilsTest() {
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
    public void testSetInTheClouds() {
    }

    @Test
    public void testReadFile() throws Exception {
    }

    @Test
    public void testSaveFile() throws Exception {

        String testLines="<a>\nUnit test for RWT FileUtilities\n</a>";
        FileUtils.saveFile("c:/unit-test", testLines);
        String content=FileUtils.readFile("c:/unit-test");
        System.out.println("content :"+content);
        
        assertTrue( content.contains("RWT FileUtilities\n"));
        FileUtils.deleteFile("c:/unit-test");

    }

    @Test
    public void testCreateDirectory() throws Exception {

        FileUtils.createDirectory("c:/", "unit-test");
        File f = new File("c:/unit-test");
        assertTrue(f.isDirectory());
        assertTrue(FileUtils.pathExists("c:/unit-test") );
        FileUtils.deleteDirectory(f);
        f = new File("c:/unit-test");
        assertFalse(f.exists());

    }

    @Test
    public void testDeleteDirectory() {
    }

    @Test
    public void testDeleteFile() throws Exception {
    }

    @Test
    public void testPathExists() {
    }

}