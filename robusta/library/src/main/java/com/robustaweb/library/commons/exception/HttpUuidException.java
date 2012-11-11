package com.robustaweb.library.commons.exception;

/**
 * Robusta Code Library
 * User: Nicolas
 * Date: 06/11/12
 * Time: 23:04
 */
public class HttpUuidException extends HttpException{

    public HttpUuidException(String expected, String actual){
        super("The Http request expected uuid :"+expected+" but received "+actual);
    }

}
