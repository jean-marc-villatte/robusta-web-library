package com.robustaweb.library.rest.client;

import java.util.Map;

public interface RestClient<Client> {

	  /**
     * if not modified, value is "application/xml;charset=utf-8"
     */
     public static  String xmlContentType = "application/xml; charset=utf-8";
     /**
      * if not modified, value is "text/html;charset=utf-8"
      */
    public static String htmlContentType = "text/html; charset=utf-8";
    /**
      * if not modified, value is  "application/x-www-form-urlencoded; charset=utf-8"
      */
    public static  String formContentType = "application/x-www-form-urlencoded; charset=utf-8";

    public static String jsonContentType = "application/json; charset=utf-8";

    public void setApplicationUri(String uri);

    /**
     * Set authorization value for next or all requests, depending on the implemntation
     * @param authorizationValue
     */
    public void setAuthorizationValue(String authorizationValue);

    /**
     * Set the Content-Type for all next requests. Defautl content-type is "application/xml; charset=utf-8"
     * @param contentType
     */
    public void setContentType(String contentType);

    /**
     * Set the RequestBody of the next request.
     * @param requestBody
     */
    public void setNextRequestBody(String requestBody);
    
    /**
     * Returns last request's Http Status Code. If no request have been made, or if an exception is thrown (ex : the request hast not even been launched),
     * the function will
     * send an IllegalStateException
     * @return last Status Code
     * @throws IllegalStateException if no request has been made, or last request did not complete
     */
    public int getHttpCode() throws IllegalStateException;
    
    /**
     * Returns last response sent by the server
     * @return last response
     */
    public String getResponse();
    
    public Map<String, String> getHeaderResponse();

    /**
     * Returns the wrapped client
     * @return the wrapped client
     */
    public Client getUnderlyingClient();

}
