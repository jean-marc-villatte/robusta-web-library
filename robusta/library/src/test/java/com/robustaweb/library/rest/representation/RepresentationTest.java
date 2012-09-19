package com.robustaweb.library.rest.representation;

import com.robustaweb.library.commons.util.FileUtils;
import com.robustaweb.library.rest.representation.implementation.JdomRepresentation;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import com.robustaweb.library.rest.resource.Resource;
import com.robustaweb.library.rest.resource.implementation.UserImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author n.zozol
 */
public abstract class RepresentationTest {

    static String json;
    static String xml;
    boolean isJson;
    Representation representation;


    @BeforeClass
    public static void setUpClass() throws Exception {
        String userDir = System.getProperty("user.dir");
        String mavenPath="/src/test/java";
        String packagePath = RepresentationTest.class.getPackage().getName().replaceAll("\\.", "/");
        String filePlace = userDir+mavenPath+"/"+packagePath+"/files/";


        json = FileUtils.readFile(filePlace + "representation.json");
        xml = FileUtils.readFile(filePlace + "representation.xml");

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws FileNotFoundException {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testToString() {

        String res = representation.toString();
        String errorMessage = "toString () is not working";

        // Rep shows a tag
        assertTrue(errorMessage, res.contains("classes"));
        // Rep shows a value
        assertTrue(errorMessage, res.contains("Boston School"));
    }

    @Test
    public void testGet() {

        //Fetching the school name
        Representation rep = representation.fetch("school");
        assertTrue(rep.get("name").equals("Boston School"));

    }

    @Test
    public void testHas() {

        //Fetching the school name
        representation = representation.fetch("school");
        assertTrue(representation.has("name"));
        assertFalse(representation.has("student"));

    }

    @Test
    public void testSet() {
        Representation rep = representation.fetch("school");
        rep.set("name", "New York School");
        assertTrue(rep.get("name").equals("New York School"));
    }

    @Test
    public void testGetDocument() {
        assertTrue(representation.getDocument() != null);
    }

    @Test
    public void testGetNumber() {

            representation = representation.fetch("school");
            Long size = representation.getNumber("size");
            assertTrue(size == 4);

    }

    @Test
    public void testGetNumberWithClass() {

        representation = representation.fetch("school");
        Float price = representation.<Float>getNumber("size", 2.0f);
        assertTrue(price == 12300.5);

    }


  

    @Test
    public void testGetOptionalValue() {
        /*
        for (Representation rp : representations) {
            String expected = "johndoe";
            String res = rp.getOptionalValue("username");
            String errorMessage = "getting " + res + " for representation " + rp.getClass().getSimpleName() + " instead of " + expected;
            assertEquals(errorMessage, expected, res);

            expected = null;
            res = rp.getOptionalValue("usernameThatIsNotATag");
            errorMessage = "getting " + res + " for representation " + rp.getClass().getSimpleName() + " instead of " + expected;
            assertNull(errorMessage, res);
        }
        */
    }

    @Test
    public void testGetValues() {
        List<String> teachers;

        if (isJson){
            representation = representation.fetch("school");
            teachers = representation.getValues("teachers");
        }else{
            representation = representation.fetch("teachers");
            teachers = representation.getValues("teacher");
        }

        assertTrue(teachers.size() == 3);
        assertTrue(teachers.contains("Brian"));
        assertTrue(teachers.indexOf("Brian") == 2);

    }


    @Test
    public void testGetNumbers() {
        List<Long> years;

        if (isJson){
            representation = representation.fetch("school");
            years = representation.getNumbers("years");
        }else{
            representation = representation.fetch("years");
            years = representation.getNumbers("year");
        }

        assertTrue(years.size() == 8);
        assertTrue(years.contains(1995L));
        assertTrue(years.indexOf(1998L) == 7);

    }

    @Test
    public void testGetNumbersWithClass() {
        List<Integer> years;

        if (isJson){
            representation = representation.fetch("school");
            years = representation.<Integer>getNumbers("years", 1);
        }else{
            representation = representation.fetch("years");
            years = representation.<Integer>getNumbers("year", 1);
        }

        assertTrue(years.size() == 8);
        assertTrue(years.contains(1995L));
        assertTrue(years.indexOf(1998L) == 7);

    }

    @Test
    public void testAdd(){
        representation = representation.fetch("school");
        representation.add("director", "Bobby Lapointe");
        assertTrue(representation.get("director").equals("Bobby Lapointe"));
    }


    @Test
    public void testAddResourceEager(){
        representation = representation.fetch("teachers");
        String email = "nz@gmail.com";
        Resource nicolas = new UserImpl(24L, email, "nicolas", "zozol");

        //this will add an object "user" with nicolas representation
        representation.add(nicolas, true);

        //Fetching nicolas
        String prefix =nicolas.getPrefix();
        assertTrue( prefix .equals("user"));
        representation = representation.fetch(prefix);
        //Finding values
        assertTrue(representation.getNumber("id").equals(24L));
        assertTrue(representation.get("email").equals(email));
    }


    @Test
    public void testAddResourceNoEager(){
        representation = representation.fetch("teachers");
        String email = "nz@gmail.com";
        Resource nicolas = new UserImpl(24L, email, "nicolas", "zozol");

        //this will add an object "user" with only id value
        representation.add(nicolas, true);

        //Nothing to fetch here
        String prefix =nicolas.getPrefix();
        assertTrue( prefix.equals("user"));

        //Finding values : the prefix node has only the id value
        assertTrue(representation.getNumber(prefix).equals(24L));
    }


    class Student {
        Long id;
        String firstname;
        String lastname;
        int age;



        Student(Long id, String firstname, String lastname, int age) {
            this.id = id;
            this.firstname = firstname;
            this.lastname = lastname;
            this.age = age;
        }
    }

}