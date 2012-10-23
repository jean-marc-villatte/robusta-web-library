/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.robustaweb.library.commons.util;

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

    String path = System.getProperty("user.dir");


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
    public void testReadFile() throws Exception {
    }

    @Test
    public void testSaveFile() throws Exception {

        String filePath = path+"/unit-test";
        String content = "RWL FileUtilities";
        String testLines="<a>\nUnit test for "+content+"\n</a>";
        FileUtils.saveFile(filePath, testLines);
        String readContent=FileUtils.readFile(filePath);
        assertTrue(readContent.contains(content));
        FileUtils.deleteFile(filePath);
        assertFalse(FileUtils.fileExists(filePath));

    }

    @Test
    public void testCreateDirectory() throws Exception {
        String dirName = "unit-test";
        String dirPath = path+"/"+dirName;

        FileUtils.createDirectory(path, dirName);
        File f = new File(dirPath);
        assertTrue(f.isDirectory());
        assertTrue(FileUtils.fileExists(dirPath) );
        FileUtils.deleteDirectory(f);
        f = new File(dirPath);
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