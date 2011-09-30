/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robustaweb.library.security.implementation;

import com.robustaweb.library.security.implementation.CodecImpl;
import junit.framework.TestCase;

/**
 *
 * @author Moi
 */
public class CodecTest extends TestCase {

    public CodecTest(String testName) {
        super(testName);
    }

    /**
     * Test of encodeBase64 method, of class Codec.
     */
    public void testEncodeBase64() {
    }

    /**
     * Test of decodeBase64 method, of class Codec.
     */
    public void testDecodeBase64() {
    }

    /**
     * Test of encodeMD5 method, of class Codec.
     */
    public void testEncodeMD5() {
    }

    /**
     * Test of getUsername method, of class Codec.
     */
    public void testGetUsername() throws Exception {
        String original = "kslater:mypassword";
        String b64 = new CodecImpl().encodeBase64(original);
        assertTrue(new CodecImpl().getUsername(b64).equals("kslater"));

        /* with strange strings */
        original = ":mypassword";
        b64 = new CodecImpl().encodeBase64(original);
        assertTrue(new CodecImpl().getUsername(b64).equals(""));

        

        original = " :mypassword";
        b64 = new CodecImpl().encodeBase64(original);
        assertTrue(new CodecImpl().getUsername(b64).equals(" "));

        original = " :";
        b64 = new CodecImpl().encodeBase64(original);
        assertTrue(new CodecImpl().getUsername(b64).equals(" "));
    }

    /**
     * Test of getPassword method, of class Codec.
     */
    public void testGetPassword() throws Exception {
        String original = "kslater:mypassword";
        String b64 = new CodecImpl().encodeBase64(original);
        assertTrue(new CodecImpl().getPassword(b64).equals("mypassword"));

        /* with strange strings */
        original = "kslater:";
        b64 = new CodecImpl().encodeBase64(original);
        assertTrue(new CodecImpl().getPassword(b64).equals(""));
        
        original = "kslater: ";
        b64 = new CodecImpl().encodeBase64(original);
        assertTrue(new CodecImpl().getPassword(b64).equals(" "));
                
        original = ": ";
        b64 = new CodecImpl().encodeBase64(original);
        assertTrue(new CodecImpl().getPassword(b64).equals(" "));
        
        original = "kslater: dsfk:";
        b64 = new CodecImpl().encodeBase64(original);
        assertTrue(new CodecImpl().getPassword(b64).equals(" dsfk:"));
    }
}






