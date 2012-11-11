package com.robustaweb.library.rest.representation.implementation;


import com.robustaweb.library.commons.exception.XmlException;

import org.jdom.Document;
import org.jdom.Element;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.robustaweb.library.commons.exception.CodecException;
import com.robustaweb.library.commons.util.CoupleList;
import java.util.List;

/**
 * These tests are old, but why not keep them ? New tests extends now RepresentationTest
 * @author Nicolas Zozol
 */
public class JdomRepresentationOldTests {

    

    public JdomRepresentationOldTests() {
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
    public void testGetRootElement() throws Exception {
        String xml = "<studentList>" +
                " <idSchool>3</idSchool><idClasse>13</idClasse><year>2009</year>" +
                "<student><lastName>Doe</lastName><firstName>John</firstName><middleName>Reuter</middleName></student>" +
                "<student><lastName>Dinar</lastName><firstName>Jim</firstName><middleName>Reuter</middleName></student>" +
                "</studentList>";

        JdomRepresentation vsx = new JdomRepresentation(xml);
        Element root = vsx.getRootElement();
        assertTrue(root.getName().equals("studentList"));
    }

    @Test
    public void testGetDocument() throws Exception {
        String xml = "<studentList>" +
                " <idSchool>3</idSchool><idClasse>13</idClasse><year>2009</year>" +
                "<student><lastName>Doe</lastName><firstName>John</firstName><middleName>Reuter</middleName></student>" +
                "<student><lastName>Dinar</lastName><firstName>Jim</firstName><middleName>Reuter</middleName></student>" +
                "</studentList>";

        Document doc = new JdomRepresentation(xml).getDocument();
        assertTrue(doc.getDescendants().hasNext());
    }

    @Test
    public void testGetElement() throws Exception {
        String xml = "<studentList>" +
                " <idSchool>3</idSchool><idClasse>13</idClasse><year>2009</year>" +
                "<student><lastName>Doe</lastName><firstName>John</firstName><middleName>Reuter</middleName></student>" +
                "<student><lastName>Dinar</lastName><firstName>Jim</firstName><middleName>Reuter</middleName></student>" +
                "</studentList>";


        Document doc = new JdomRepresentation(xml).getDocument();
        Element e = new JdomRepresentation(xml).getElement("idClasse");
        assertTrue(e.getParentElement().getName().equals("studentList"));
        assertTrue(e.getTextNormalize().equals("13"));
    }

    public void testGetElementWithDoc() throws CodecException, XmlException {

        String xml = "<studentList>" +
                " <idSchool>3</idSchool><idClasse>13</idClasse><year>2009</year>" +
                "<student><lastName>Doe</lastName><firstName>John</firstName><middleName>Reuter</middleName></student>" +
                "<student><lastName>Dinar</lastName><firstName>Jim</firstName><middleName>Reuter</middleName></student>" +
                "</studentList>";

        
        Element e = new JdomRepresentation(xml).getElement("lastName");
        assertTrue(e.getTextTrim().equals("Doe"));

    }

    @Test
    public void testGetSimpleInteger() throws Exception {
        String xml = "<studentList>" +
                " <idSchool>3</idSchool><idClasse>13</idClasse><year> " +
                "\n2009     </year>" +
                "<student><lastName>Doe</lastName><firstName>John</firstName><middleName>Reuter</middleName></student>" +
                "<student><lastName>Dinar</lastName><firstName>Jim</firstName><middleName>Reuter</middleName></student>" +
                "</studentList>";

        

        Long idClasse = new JdomRepresentation(xml).getNumber("idClasse");
        assertTrue(idClasse == 13);

        int year = new JdomRepresentation(xml).getNumber("year", Integer.class);
        assertTrue(year == 2009);


    }

    @Test
    public void testGetValue() throws Exception {
        String xml = "<studentList>" +
                " <idSchool>3</idSchool><idClasse>13</idClasse><year> " +
                "\n2009     </year>" +
                "<student><lastName>Doe</lastName><firstName>John</firstName><middleName>Reuter</middleName></student>" +
                "<student><lastName>Dinar</lastName><firstName>Jim</firstName><middleName>Reuter</middleName></student>" +
                "</studentList>";

        

        String v = new JdomRepresentation(xml).fetch("student").get("lastName");
        assertTrue(v.equals("Doe") || v.equals("Dinar"));

        boolean foundError = false;
        try {
            v = new JdomRepresentation(xml).get("student");
        } catch (XmlException ex) {
            foundError = true;
        }
        assertTrue(foundError);

        v = new JdomRepresentation(xml).get("year");
        assertFalse(v.equals("2009"));
        assertTrue(v.contains("2009"));

        //assertTrue(StringUtilities.trimReturnCharacters(v).equals("2009"));
        assertTrue(v.trim().equals("2009"));

        /* Null if not found */
        foundError = false;
        try {
            v = new JdomRepresentation(xml).get("NoPossibleSir");
        } catch (XmlException ex) {
            foundError = true;
        }

        assertTrue(foundError);


    }



    @Test
    public void testGetStringValues() throws Exception {
        String xml = " <root>" +
                "<object><data>Jambalaya</data><beans>75</beans></object>" +
                "<object><data>Cassoulet</data><beans>150</beans></object>" +
                "<object><data>Garbure</data><beans>15</beans></object>" +
                "</root>";

        List<String> list = new JdomRepresentation(xml).getValues("data");
        System.out.println("list :" + list);
        assertTrue(list.size() == 3);
        assertTrue(list.contains("Jambalaya"));

        list = new JdomRepresentation(xml).getValues("dataCantBeFound");
        assertTrue(list.size() == 0);
    }

    @Test
    public void testGetIntegerValues() throws Exception {
        String xml = " <root>" +
                "<object><data>Jambalaya</data><beans>75</beans></object>" +
                "<object><data>Cassoulet</data><beans>   \n" +
                "150</beans></object>" +
                "<object><data>Garbure</data><beans>15</beans></object>" +
                "</root>";

        List<Long> list = new JdomRepresentation(xml).getNumbers("beans");
        System.out.println("list :" + list);
        assertTrue(list.size() == 3);
        assertTrue(list.contains(150L));

        /* Error if not integers */
        boolean foundError = false;
        try {
            list = new JdomRepresentation(xml).getNumbers("data");
            System.out.println("list :" + list);
        } catch (NumberFormatException ex) {
            foundError = true;
        }
        assertTrue(foundError);

        list =new JdomRepresentation(xml).getNumbers("beansCantBeFound");
        assertTrue(list.size() == 0);
    }

    @Test
    public void testGetStringRepresentation_Element() {
    }

    /**
     * TODO à tester
     * @throws Exception
     */
    @Test
    public void testGetCDATA() throws Exception {
    }

    /**
     * TODO à tester
     * @throws Exception
     */
    @Test
    public void testReadDocumentOnDisk() throws Exception {
    }

    /**
     * TODO à tester
     * @throws Exception
     */
    @Test
    public void testSaveDocument() throws Exception {
    }

    /**
     * TODO à tester
     * @throws Exception
     */
    @Test
    public void testGetElements() throws Exception {
    }

   

    @Test
    public void getCouples() throws Exception {

        String xml = " <root>" +
                "<object><data>Jambalaya</data><beans>75</beans></object>" +
                "<object><data>Cassoulet</data><beans>150</beans></object>" +
                "<object><data>Garbure</data><beans>15</beans></object>" +
                "</root>";

        CoupleList<String, String> couples = new JdomRepresentation(xml).getCouples( "data", "beans");
        assertTrue(couples.size() == 3);
        assertTrue(couples.firstMatcher("75").equals("Jambalaya"));
        /*
         * will return [{"Jambalaya", "75"}, {"Cassoulet", "150"}, {"Garbure", "15"}]
         *
         * If the structure is not as clear as shown, the result might be hazardous.
         * */
    }

   

    @Test
    public void testAddContent() throws XmlException{
        String xml = " <root>" +
                "<object><data>Jambalaya</data><beans>75</beans></object>" +
                "<object><data>Cassoulet</data><beans>150</beans></object>" +
                "<object><data>Garbure</data><beans>15</beans></object>" +
                "</root>";       

        JdomRepresentation representation  = new JdomRepresentation(xml);
        representation.add("bob","spounge");
        assertTrue(representation.get("bob").equals("spounge"));
    }
    
    
     @Test
    public void testRemoveContent() throws XmlException{
        String xml = " <root>" +
                "<object><data>Jambalaya</data><beans>75</beans></object>" +
                "<object><data>Cassoulet</data><beans>150</beans></object>" +
                "<data>Garbure</data><beans>15</beans>" +
                "<bob>Eponge</bob>" +
                "<bob>Spounge</bob>" +
                "</root>";

        JdomRepresentation representation  = new JdomRepresentation(xml);
        assertTrue(representation.get("bob").equals("Eponge"));

        //Removes the first, keeps the second
        representation.remove("bob");
        assertTrue(representation.get("bob").equals("Spounge"));
    }




}



















