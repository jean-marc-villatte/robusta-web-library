/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.robustaweb.library.security.implementation;


import com.robustaweb.library.security.implementation.CodecImpl;
import com.robustaweb.library.security.implementation.Base64Converter;
import junit.framework.TestCase;


/**
 *
 * @author Moi
 */
public class Base64ConverterTest extends TestCase {
    
    public Base64ConverterTest(String testName) {
        super(testName);
    }

    /**
     * Test of encode method, of class Base64Converter.
     */
    public void testEncode_String() {
        
        String result=new CodecImpl().encodeBase64("nzozol:peinture");
        
        System.out.println("encoded : "+result);
        System.out.println("decoded : "+Base64Converter.decodeToString(result));
        
    }

 

    /**
     * Test of decode method, of class Base64Converter.
     */
    public void testDecode_String() {
    }


}
