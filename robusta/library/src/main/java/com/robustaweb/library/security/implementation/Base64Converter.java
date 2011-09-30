
package com.robustaweb.library.security.implementation;


// ===========================================================================
// CONTENT  : CLASS Base64Converter
// AUTHOR   : M.Duchrow
// VERSION  : 1.1 - 22/05/2004
// HISTORY  :
//  22/05/2004  mdu  CREATED
//	11/06/2005	mdu		changed	-->	Due to changed underlying Base64 class
//
// Copyright (c) 2004-2005, by M.Duchrow. All rights reserved.
// ===========================================================================


import java.io.UnsupportedEncodingException;

// ===========================================================================
// IMPORTS
// ===========================================================================

/**
 * A converter that allows to encode strings, byte arrays and char arrays to
 * BASE64 and vice versa.<p>
 * <b> 
 * Currently it is based on the class Base64 from Robert Harder 
 * (http://iharder.sourceforge.net/base64).
 * Thanks to him that he published his implementation as open source.
 * </b>
 * This class mainly adds some convenience methods for string handling.
 * 
 * @author M.Duchrow
 * @version 1.1
 *
 * TODO for Robusta Web Library : check compatibility of the licence
 */
class Base64Converter
{
  // =========================================================================
  // CONSTANTS
  // =========================================================================

  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================

  // =========================================================================
  // PUBLIC CLASS METHODS
  // =========================================================================
	/**
	 * Returns a BASE64 encoded version of the given string
	 */
	public static String encode( String unencoded ) 
	{
		byte[] bytes ;
		
		bytes = unencoded.getBytes() ;
		return encodeToString( bytes ) ;
	} // encode() 

	// -------------------------------------------------------------------------

	/**
	 * Returns a BASE64 encoded version of the given character array
	 */
	public static char[] encode( char[] unencoded ) 
	{
		String str ;
		
		str = new String( unencoded ) ;
		return encode( str.getBytes() ) ;
	} // encode() 

	// -------------------------------------------------------------------------

	/**
	 * Returns a BASE64 encoded version of the given byte array
	 */
	public static char[] encode( byte[] unencoded ) 
	{
		return encodeToString( unencoded ).toCharArray() ;
	} // encode() 

	// -------------------------------------------------------------------------

	/**
	 * Returns a BASE64 encoded version of the given byte array as String
	 */
	public static String encodeToString( byte[] unencoded ) 
	{
		return Base64.encodeBytes( unencoded ) ;
	} // encodeToString() 

	// -------------------------------------------------------------------------

	/**
	 * Returns a BASE64 encoded version of the given char array as String
	 */
	public static String encodeToString( char[] unencoded ) 
	{
		char[] chars ;
		
		chars = encode( unencoded ) ;
		return new String(chars) ;
	} // encodeToString() 

	// -------------------------------------------------------------------------

	/**
	 * Returns a byte array decoded from the given BASE64 encoded char array
	 */
	public static byte[] decode( char[] encoded ) 
	{
		return decode( new String(encoded) ) ;
	} // decode() 

	// -------------------------------------------------------------------------

	/**
	 * Returns a byte array decoded from the given BASE64 encoded String
	 */
	public static byte[] decode( String encoded ) 
	{
		return Base64.decode( encoded ) ;
	} // decode() 

	// -------------------------------------------------------------------------

	/**
	 * Returns a string decoded from the given BASE64 encoded String
	 * 
	 * @param encoded The BASE64 encoded string
	 */
	public static String decodeToString( String encoded )
	{
		byte[] bytes ;
		
		bytes = decode( encoded ) ;
		return new String( bytes ) ;
	} // decode() 

	// -------------------------------------------------------------------------
	
	/**
	 * Returns a string decoded from the given BASE64 encoded String
	 * 
	 * @param encoded The BASE64 encoded string
	 * @param encoding The name of the reult string's encoding (e.g. "UTF-8"). 
	 */
	public static String decodeToString( String encoded, String encoding )
		throws UnsupportedEncodingException
	{
		byte[] bytes ;
		
		bytes = decode( encoded ) ;
		return new String( bytes, encoding ) ;
	} // decode() 

	// -------------------------------------------------------------------------

	// =========================================================================
  // CONSTRUCTORS
  // =========================================================================
  /**
   * Initialize the new instance with default values.
   */
  private Base64Converter()
  {
    super() ;
  } // Base64Converter() 

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================

  // =========================================================================
  // PROTECTED INSTANCE METHODS
  // =========================================================================

} // class Base64Converter 
