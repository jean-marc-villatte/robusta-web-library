/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robustaweb.library.commons.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.robustaweb.library.security.implementation.CodecImpl;
import static org.junit.Assert.*;
import com.robustaweb.library.commons.exception.ValidationException;
import com.robustaweb.library.rest.HttpMethod;

/**
 *
 * @author Moi
 */
public class StringUtilsTest {

    public StringUtilsTest() {
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
    public void testOriginalTrim() {
        System.out.println("--- start testOriginalTrim ----");
        String original = "\n\n  This is the String with \\n and a few spaces     ";
        System.out.println(original);
        System.out.println("--- middle testOriginalTrim ----");
        System.out.println(original.trim());
        System.out.println("end middle testOriginalTrim ----");
    }

    /**
     * Test of validateEmail method, of class StringUtilities.
     */
    @Test
    public void testValidateEmail() {
        String email = "@riji.fdkl.com";
        assertFalse(StringUtils.validateEmail(email));

        email = "nzozol@gmail.com";
        assertTrue(StringUtils.validateEmail(email));

        email = "nzozol@gmail.";
        assertFalse(StringUtils.validateEmail(email));

        email = "nzozolgmail.com";
        assertFalse(StringUtils.validateEmail(email));

        /* it will trim */
        email = " nzozol@gmail.com";
        assertTrue(StringUtils.validateEmail(email));


        email = " n.zozol@gmail.com";
        assertTrue(StringUtils.validateEmail(email));


        email = "n.zozol-machine@gmail-online.com";
        assertTrue(StringUtils.validateEmail(email));

        email = "n.zozol-machine@gmail-online.united-kingdom.com";
        assertTrue(StringUtils.validateEmail(email));

        email = "nzozol56@gmail.com";
        assertTrue(StringUtils.validateEmail(email));

        email = "Bart.@Simpsons.com";
        assertTrue(StringUtils.validateEmail(email));



    }

    /**
     * Test of removeCharacter method, of class StringUtilities.
     */
    @Test
    public void testRemoveCharacter() {
        String boss = "Bruce Springsteen";
        String result = StringUtils.removeCharacter(boss, 'e');
        /* The original Stirng doesn't change */
        assertTrue(boss, boss.equals("Bruce Springsteen"));
        assertTrue(result, result.equals("Bruc Springstn"));
    }

    /**
     * Test of removeCharacters method, of class StringUtilities.
     */
    @Test
    public void testRemoveCharacters() {
        String boss = "Bruce Springsteen";
        String result = StringUtils.removeCharacters(boss, "se");
        /* The original Stirng doesn't change */
        assertTrue(boss, boss.equals("Bruce Springsteen"));
        assertTrue(result, result.equals("Bruc Springtn"));
    }

    /*@Test
    public void trimReturnCharacters() {

    String s = "Yoohoo Papi Mambo !";
    assertTrue(StringUtilities.trimReturnCharacters(s).equals(s));

    s = "";
    assertTrue(StringUtilities.trimReturnCharacters(s).equals(s));

    s = "\n";
    assertTrue(StringUtilities.trimReturnCharacters(s).equals(""));

    s = " \n";
    assertTrue(StringUtilities.trimReturnCharacters(s).equals(""));

    s = " \n\n Yoohoo Papi Mambo !   \n ";
    String newString = StringUtilities.trimReturnCharacters(s);
    System.out.println("new : " + newString);
    assertTrue(newString, StringUtilities.trimReturnCharacters(s).equals("Yoohoo Papi Mambo !"));

    }*/
    @Test
    public void testReplaceSequence() {

        String original = "//cooluri//living.js///";
        System.out.println(StringUtils.replaceSequence(original, "//", "-"));

        original = "Côte d'ivoire";
        System.out.println(StringUtils.replaceSequence(original, "'", " "));

    }

    @Test
    public void testTruncate() {

        String original = "Hernandez";
        String result = StringUtils.truncate(original, 8);
        assertTrue(result, result.equals("Hernande"));

        result = StringUtils.truncate(original, 4);
        assertTrue(result, result.equals("Hern"));

        result = StringUtils.truncate(original, 18);
        assertTrue(result, result.equals("Hernandez"));

        result = StringUtils.truncate(original, 9);
        assertTrue(result, result.equals("Hernandez"));

        result = StringUtils.truncate(original, 10);
        assertTrue(result, result.equals("Hernandez"));

        result = StringUtils.truncate(original, 0);
        assertTrue(result, result.equals(""));



    }

    //@Test
    public void readInputStream() throws Exception {

        URL u = new URL("http://www.google.com");

        HttpURLConnection http = (HttpURLConnection) u.openConnection();

        String result = FileUtils.readInputStream(http.getInputStream());
        //System.out.println("result google :"+result);
        assertTrue(result.toLowerCase().contains("google"));

    }

    @Test
    public void testRemoveLastCharacter() throws Exception {

        String s1 = "", s2 = "Madagascar";

        assertTrue(StringUtils.removeLastCharacter(s1).equals(s1));
        assertTrue(StringUtils.removeLastCharacter(s2).equals("Madagasca"));

    }

    @Test
    public void testRemoveTrailingCharacters() throws Exception {
        String s1 = "", s2 = "Madagascar", s3 = "Malagaaaaaaaa", s4 = "0000000000";

        assertTrue(StringUtils.removeTrailingCharacters(s1, 'r').equals(s1));
        assertTrue(StringUtils.removeTrailingCharacters(s2, 'r').equals("Madagasca"));
        assertTrue(StringUtils.removeTrailingCharacters(s2, 'j').equals("Madagascar"));
        String res = StringUtils.removeTrailingCharacters(s3, 'a');
        assertTrue(res, res.equals("Malag"));

        String res2 = StringUtils.removeTrailingCharacters(s4, '0');
        assertTrue(res2.equals(""));


    }

    @Test
    public void testGetEmailPrefix() throws ValidationException {
        String email = "nzozol@gmail.com";
        assertTrue(StringUtils.getEmailPrefix(email).equals("nzozol"));
    }

    @Test
    public void testIsEmailSplitInTwoParts() throws ValidationException {
        String email = "nzozol@gmail.com";
        assertTrue(StringUtils.getEmailPrefix(email).equals("nzozol"));

        assertFalse(StringUtils.isEmailSplitInTwoParts(email));

        email = "nicolas.zozol@gmail.com";
        assertTrue(StringUtils.isEmailSplitInTwoParts(email));

        email = "nicolas.@gmail.com";
        assertTrue(StringUtils.isEmailSplitInTwoParts(email));
    }

    @Test
    public void testValidateMD5() {

        String email = "jfkjlk@ggg.bobjh";
        assertFalse(StringUtils.validateMD5(email));

        String str32 = "jfkjlk@ggg.bobjhjfkjlk@ggg.bobjh";
        assertFalse(StringUtils.validateMD5(str32));

        String ok = new CodecImpl().encodeMD5("Jo le Tacos");
        assertTrue(StringUtils.validateMD5(ok));

    }

    @Test
    public void testContainsCharacter() {

        boolean b = StringUtils.containsCharacter("Bob", "<>=}");
        assertTrue(!b);

    }

    @Test
    public void testSplit() {

        String string = "ab de fg";
        String[] expected = new String[]{"ab", "de", "fg"};
        String[] result = StringUtils.split(string, " ");
        assertTrue("found : " + Arrays.asList(result) + " instead of expected : " + Arrays.asList(expected), Arrays.equals(expected, result));

        string = "ab   de fg";
        expected = new String[]{"ab", "de", "fg"};
        result = StringUtils.split(string, " ");
        assertTrue("found : " + Arrays.asList(result) + " instead of expected : " + Arrays.asList(expected), Arrays.equals(expected, result));

        string = "ab  : : de : fg :ij:kl";
        expected = new String[]{"ab", "de", "fg", "ij", "kl"};
        result = StringUtils.split(string, " :");
        assertTrue("found : " + Arrays.asList(result) + " instead of expected : " + Arrays.asList(expected), Arrays.equals(expected, result));
    }

    @Test
    public void testJoin() {
        Object [] objects = new String[]{"jo", "jack", "jim"};
        String expected ="jojackjim";
        String actual = StringUtils.join(objects, "");
        assertEquals(expected, actual);

        //catch exception if joint is null
        boolean found = false;
        try {
            StringUtils.join(objects, null);
        } catch (IllegalArgumentException ex) {
            found = true;
        }
        assertTrue(found);

        //joint with other way
        expected = "jo - jack - jim";
        actual = StringUtils.join(objects, " - ");
        assertEquals(expected, actual);
        
    }

   

    @Test
    public void testNormalize() {
        String string = "Chateau De Versailles";
        String expected = "ChateauDeVersailles";
        String result = StringUtils.normalize(string, null);
        assertTrue("found : " + result + " instead of expected : " + expected, expected.equals(result));

        string = "white_house";
        expected = "whiteHouse";
        result = StringUtils.normalize(string, null);
        assertTrue("found : " + result + " instead of expected : " + expected, expected.equals(result));

        string = "groovyStyle_grails";
        expected = "groovyStyleGrails";
        result = StringUtils.normalize(string, null);
        assertTrue("found : " + result + " instead of expected : " + expected, expected.equals(result));
    }

    @Test
    public void testReplaceAny() {
        String string = "john jack jim";
        String expected = "tohn tack ttm";
        String result = StringUtils.replaceAny(string, "ji", "t");
        assertTrue("found : " + result + " instead of expected : " + expected, expected.equals(result));
    }

    @Test
    public void testAddPath() {
        String abs = "http://jo.com/";
        String rel = "doe";
        String expected = "http://jo.com/doe";
        String actual = StringUtils.addPath(abs, rel);

        assertEquals(expected, actual);

        abs = "http://jo.com";
        actual = StringUtils.addPath(abs, rel);
        assertEquals(expected, actual);

        rel = "/doe";
        actual = StringUtils.addPath(abs, rel);
        assertEquals(expected, actual);

        abs = "http://jo.com/";
        actual = StringUtils.addPath(abs, rel);
        assertEquals(expected, actual);

        actual = StringUtils.addPath(abs, "/");
        expected = "http://jo.com/";
        assertEquals(expected, expected);

        abs = "http://jo.com";
        actual = StringUtils.addPath(abs, "/");
        assertEquals(expected, expected);

    }

    @Test
    public void testAddProtocol() {
        String expected = "http://joe.com";
        String actual = StringUtils.addProtocol("http", "/joe.com");
        assertEquals(expected, actual);
        
        //Exemple StringUtils.addProtocol("http://", "/joe.com" will create "http://joe.com")
        //=> this could cause problem if stuff is secured !!!!
    }

    @Test
    public void testGetSimpleClassName() {
        String expected ="String";
        String actual = StringUtils.getSimpleClassName("this is a java.lang.String object");
        assertEquals(expected, actual);
    }

    @Test
    public void testExtract(){
        String classic = "I want to ##remove some stuff - there";
        String expected = "remove some stuff";
        String actual = StringUtils.extractBetweenSeparators(classic, "##", " -");
        assertEquals(expected, actual);
        actual = StringUtils.extractBetweenSeparators(classic, "#", " -");
        assertNotSame(expected, actual);

        String noLeft = "remove some stuff-> on the right";
        actual = StringUtils.extractBetweenSeparators(noLeft, null, "->");
        assertEquals(expected, actual);
        actual = StringUtils.extractBetweenSeparators(noLeft, "", "->");
        assertEquals(expected, actual);

        String noRight = "On the left<=remove some stuff";
        actual = StringUtils.extractBetweenSeparators(noRight, "<=", null);
        assertEquals(expected, actual);
        actual = StringUtils.extractBetweenSeparators(noRight, "<=", "");
        assertEquals(expected, actual);

        String same = "left#remove some stuff#right";
        actual = StringUtils.extractBetweenSeparators(same, "#", "#");
        assertEquals(expected, actual);

        String before = "left[#remove some stuff[right";
        actual = StringUtils.extractBetweenSeparators(before, "#", "[");
        assertEquals(expected, actual);

        String empty = "left[#[right";
        expected="";
        actual = StringUtils.extractBetweenSeparators(empty, "#", "[");
        assertEquals(expected, actual);

        String notFound ="On the left<=remove some stuff";
        boolean foundError = false;
        try{
            StringUtils.extractBetweenSeparators(notFound, "#", "[");
        }catch (IllegalArgumentException ex){
            foundError = true;
        }
        assertTrue(foundError);

    }

    @Test
    public void testStrictCapitalize() {
        String expected = "Johndoe";
        String actual = StringUtils.strictCapitalize("johnDoe");
        assertEquals(expected, actual);

        expected = "Johndoe";
        actual = StringUtils.strictCapitalize("JohnDoe");
        assertEquals(expected, actual);
    }

    @Test
    public void testSimpleCapitalize() {
        String expected = "JohnDoe";
        String actual = StringUtils.simpleCapitalize("johnDoe");
        assertEquals(expected, actual);

        expected = "JohnDoe";
        actual = StringUtils.simpleCapitalize("JohnDoe");
        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveStartingCharacters() {
        String expected = "Malagi";
        String actual = StringUtils.removeStartingCharacters("iiiiMalagi", 'i');
        assertEquals(expected, actual);

        expected = "oakland";
        actual = StringUtils.removeStartingCharacters("oakland", 'i');
        assertEquals(expected, actual);

        expected = "oakland";
        actual = StringUtils.removeStartingCharacters("  oakland", ' ');
        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveFirstCharacter() {
        String expected = "iiiMalagi";
        String actual = StringUtils.removeFirstCharacter("iiiiMalagi");
        assertEquals(expected, actual);
    }
}
