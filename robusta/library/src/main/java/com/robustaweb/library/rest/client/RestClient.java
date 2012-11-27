package com.robustaweb.library.rest.client;

import java.util.List;
import java.util.Map;

/**
 * Simple Rest client that can wrap many Http Clients
 * The general idea is : you can make simple and useful stuff ; if you want complicated things, use the original client grabbing #getUnderlyingClient method
 * @param <Client>
 */
public interface RestClient<Client> {

	  /**
     * if not modified, value is "application/xml;charset=utf-8"
     */
     public static  String xmlContentType = "application/xml; charset=utf-8";
     /**
      * if not modified, value is "text/html;charset=utf-8"
      */
    public static String htmlContentType = "text/html; charset=utf-8";

    public static String textContentType = "text/plain; charset=utf-8";


    /**
      * if not modified, value is  "application/x-www-form-urlencoded; charset=utf-8"
      */
    public static  String formContentType = "application/x-www-form-urlencoded; charset=utf-8";

    public static String jsonContentType = "application/json; charset=utf-8";

    public static final String ACCEPTING_STRING = "STRING";
    public static final String ACCEPTING_REPRESENTATION = "REPRESENTATION";
    public static final String ACCEPTING_RESOURCE = "RESOURCE";
    public static final String ACCEPTING_INPUT_STREAM = "INPUT STREAM";


    public void setApplicationUri(String uri);

    /**
     * Set authorization value for next or all requests, depending on <i>once</i> parameter.
     * Set <i>once</i> to true if you want to send the authorizationValue only once - previous value is abandoned. If not, it will be kept for all further requests
     * @param authorizationValue
     * @param once : set once to true if you want to send the authorizationValue only once. If not, it will be kept for all further requests
     */
    public void setAuthorizationValue(String authorizationValue, boolean once);


    /**
     * Set the contentType for all further requests ; Default content-type is "application/x-www-form-urlencoded; charset=utf-8"
     * @param contentType
     * @see #setContentType(String)
     */
    public void setDefaultContentType(String contentType);

    /**
     * Set the Content-Type for next request only.
     * @param contentType
     * @see #setDefaultContentType(String)
     */
    public void setContentType(String contentType);


    /**
     * Set the header to the request (delete previous setted value)
     * @param name
     * @param value
     */
    public void setHeader(String name, String value);

    /**
     * This feature is to prevent collisions with proxies/routers that could return falsified responses. You'll find such disasters
     * in public wifis.
     * The Request send a request-uuid header and the Response must have the same value in its response-uuid header.
     * If it's not correct, the Client throw a HttpUuidException. By default, this feature is disabled
     * @param validate true for activate the feature
     */
    public void validateRequestAndResponseUuids(boolean validate);



    
    /**
     * Returns last request's Http Status Code. If no request have been made -  or if last request has failed before even beeing launched -
     * the function will send an IllegalStateException
     * @return last Status Code
     * @throws IllegalStateException if no request has been made, or last request did not complete
     */
    public int getHttpCode() throws IllegalStateException;
    

    
    public Map<String, String> getResponseHeaders();

    /**
     * Returns the wrapped client
     * @return the wrapped client
     */
    public Client getUnderlyingClient();



}
