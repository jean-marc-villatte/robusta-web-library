package com.robustaweb.library.rest.representation.implementation;

import com.robustaweb.library.commons.util.FileUtils;
import org.json.simple.JSONArray;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;


/**
 * User: Nicolas
 * Date: 05/08/12
 * Time: 20:24
 */
public class JsonSimpleRepresentationTest {

    static String jsonHouses, fetch, singers;
    JsonSimpleRepresentation representation;

    @BeforeClass
    public static void setUpClass() throws Exception {
        String userDir = System.getProperty("user.dir");
        String mavenPath="/src/test/java";
        String packagePath = JsonSimpleRepresentationTest.class.getPackage().getName().replaceAll("\\.", "/");
        String here = userDir+mavenPath+"/"+packagePath+"/jsonFiles/";


        jsonHouses = FileUtils.readFile(here+"houses.json");
        fetch = FileUtils.readFile(here+"fetch.json");
        //jsonHouses = FileUtils.readFile(here+"houses.json");

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
    public void testHouses(){
        representation = new JsonSimpleRepresentation(jsonHouses);
       // System.out.println(representation.getNumbers("size"));
    }

    @Test
    public void testFetch(){
        representation = new JsonSimpleRepresentation(fetch);
        representation = representation.fetch("main").fetch("under").fetch("houses");
        assertTrue(representation.json instanceof JSONArray);


    }

    @Test
    public void testNumbers(){
        representation = new JsonSimpleRepresentation(fetch);
        representation = representation.fetch("main").fetch("under").fetch("houses");
        List<Long> numbers =representation.getNumbers("size");
        assertTrue(numbers.size()== 4);
        assertTrue(numbers.contains(24L));
    }

    @Test
    public void testValues(){
        representation = new JsonSimpleRepresentation(fetch);
        representation = representation.fetch("main").fetch("under").fetch("houses");
        List<Long> numbers =representation.getNumbers("size");
        assertTrue(numbers.size()== 4);
    }


}
