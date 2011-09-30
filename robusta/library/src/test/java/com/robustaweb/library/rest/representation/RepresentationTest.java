package com.robustaweb.library.rest.representation;

import com.robustaweb.library.rest.representation.implementation.JdomRepresentation;
import java.util.Arrays;
import java.util.List;
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
public class RepresentationTest {

    static Representation [] representations;
    String xml;
    String json;
    int asInt = 0;
    float asFloat = 0f;

    public RepresentationTest() {
          
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

     @Before
    public void setUp() {
          xml =
                "<resource>"
                + "<id>12</id>"
                + "<age>24</age>"
                + "<firstName>John</firstName>"
                + "<lastName>Doe</lastName>"
                + "<userName>johndoe</userName>"
                + "<numbers>1</numbers>"
                + "<numbers>2</numbers>"
                + "<numbers>3</numbers>"
                + "<numbers>4</numbers>"
                + "</resource>";

         json =
                "{resource : "
                + "id : 12, age : 24, firstName : 'John', lastName : 'Doe', userName : 'johndoe', "
                + " numbers : [1, 2, 3, 4],"
                + "}";

        //only JDOM now
        representations = new Representation[]{new JdomRepresentation(xml)};
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testToString() {
        for (Representation rp : representations) {
            String res = rp.toString();

            String errorMessage = "toString () is not working";
            assertTrue(errorMessage, res.contains("<numbers>2<"));
        }
    }

    @Test
    public void testGet() {
        for (Representation rp : representations) {
            String res = rp.get("lastName");
            String expected = "Doe";
            String errorMessage = "getting " + res + " for representation " + rp.getClass().getSimpleName() + " instead of " + expected;
            assertEquals(errorMessage, expected, res);
        }
    }

    @Test
    public void testSet() {
         for (Representation rp : representations) {
            String expected = "Timbler";
            rp.set("motherName", expected);
            String res = rp.get("motherName");
            String errorMessage = "getting " + res + " for representation " + rp.getClass().getSimpleName() + " instead of " + expected;
            assertEquals(errorMessage, expected, res);
        }
    }

    @Test
    public void testGetDocument() {
          for (Representation rp : representations) {
            String errorMessage = "getDocument() is null for representation " + rp.getClass().getSimpleName();
            assertNotNull(errorMessage, rp.getDocument());
        }
    }

    @Test
    public void testGetNumber() {
         for (Representation rp : representations) {
            Long expectedId = 12L;
            Long resId = rp.getNumber("id");
            String errorMessage = "getting " + resId + " for representation " + rp.getClass().getSimpleName() + " instead of " + expectedId;
            assertEquals(errorMessage, expectedId, resId);

            Representation clone = rp.copy();
            clone.set("float", "12.2");
            float expected = 12.2f;
            float result = clone.getNumber("float", asFloat);
            errorMessage = "getting " + result + " for representation " + clone.getClass().getSimpleName() + " instead of " + expected;
            assertTrue(errorMessage, expected == result);//no epsilon test here            
        }
    }
  

    @Test
    public void testGetOptionalValue() {
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
    }

    @Test
    public void testGetValues() {
          for (Representation rp : representations) {
            List<String> res = rp.getValues("numbers");
            List<String> expected = Arrays.asList(new String[]{"1", "2", "3", "4"});
            String errorMessage = "getting " + res + " for representation " + rp.getClass().getSimpleName() + " instead of " + expected;
            assertTrue(errorMessage, res.equals(expected));
        }
    }

    @Test
    public void testGetNumbers_String() {
         for (Representation rp : representations) {

            List<Long> resAsLong = rp.getNumbers("numbers");
            List<Integer> resAsInts = rp.getNumbers("numbers", asInt);
            List<Float> resAsFloats = rp.getNumbers("numbers", asFloat);
            List<Integer> expectedAsInts = Arrays.asList(new Integer[]{1, 2, 3, 4});
            List<Long> expectedAsLong = Arrays.asList(new Long[]{1L, 2L, 3L, 4L});
            List<Float> expectedAsFloat = Arrays.asList(new Float[]{1f, 2f, 3f, 4f});

            String errorMessage = "For Integers : getting " + resAsInts + " for representation " + rp.getClass().getSimpleName() + " instead of " + expectedAsInts;
            assertTrue(errorMessage, resAsInts.equals(expectedAsInts));

            errorMessage = "For Longs : getting " + resAsLong + " for representation " + rp.getClass().getSimpleName() + " instead of " + expectedAsLong;
            assertTrue(errorMessage, resAsLong.equals(expectedAsLong));

            errorMessage = "For Floats : getting " + resAsFloats + " for representation " + rp.getClass().getSimpleName() + " instead of " + expectedAsFloat;
            assertTrue(errorMessage, resAsFloats.equals(expectedAsFloat));
        }
    }   

}