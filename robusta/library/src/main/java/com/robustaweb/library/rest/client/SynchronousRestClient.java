/*
 * Copyright 2007-2011 Nicolas Zozol
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robustaweb.library.rest.client;

import com.robustaweb.library.commons.exception.HttpException;
import com.robustaweb.library.commons.util.CoupleList;

/**
 * Designs a Http Client simplified and specialized for REST & Java. For exemple, no need here for cookies, as you can use Java objects for that.
 * Nevertheless, a GwtClient handling cookies will be designed.
 * <p>
 * RestfulClient are wrappers, with simplified API, and you can use the full-functional Client with getCLient()
 * </p>
 * @author robusta web
 */
public interface SynchronousRestClient<Client> extends RestClient<Client>{

   /**
     * if not modified, value is "application/xml;charset=utf-8" ; abbreviation is xml
     */
     public static  String xmlContentType = "application/xml;charset=utf-8";
     /**
      * if not modified, value is "text/html;charset=utf-8" ; abbreviation is html
      */
    public static String htmlContentType = "text/html;charset=utf-8";
    /**
      * if not modified, value is  "application/x-www-form-urlencoded; charset=utf-8" ; abbreviation is form
      */
    public static  String formContentType = "application/x-www-form-urlencoded; charset=utf-8";

    /**
      * if not modified, value is  "application/json; charset=utf-8" ; abbreviation is json
      */
    public static  String jsonContentType = "application/json; charset=utf-8";

    /**
     * Set the Root Uri of the Web Application, for exemple http://localhost:8080/mywebapp/ ; Depending on the implementation, it might be enough to use this function just once, or you may need
     * to set Application Path for each request. Of course it's very probable that you need to execute this function only once.
     * @param address
     */
    public void setApplicationUri(String uri);

    /**
     * Authorization header sent in the request. Depending on the implementation, it might be enough to use this function just once, or you may need
     * to set Authorization for each request
     * @param authorizationValue
     */
    public void setAuthorizationValue(String authorizationValue);

    /**
     * Set the Content-type of the request
     * @param contentType
     */
    public void setContentType(String contentType);

    /**
     * Set the PostBody of the Request. It's usually used with POST or PUT, but some implementations might accept PostBody with GET or DELETE.
     * @param requestBody : body content of the next Request ; <strong>It will be used only once !</strong>
     * @param contentTypeIsXml : if true, the Content-Type will be set to application/xml
     * @todo1 : remove the boolean stuff
     * @todo1 : rename as setRequestBody -
     */
    public void setNextRequestBody(String postBody);

    /**
     * <p>Execute a POST request toward a specified Resource, with evenutal parameters.</p>
     * <p>
     * If ApplicationUri is set to <strong>http://localhost:8080/mywebapp/</strong> and <strong>relativeFileWithNoParam</strong> is set to <strong>user/register.jsp</strong>,
     * then the request is sent to <strong>http://localhost:8080/mywebapp/user/register.jsp</strong></p>
     * <p>Params are set by a CoupleList ; null is accepted for no param</p>
     * <p>It's also possible to set the "pure postBody"  of the request by using setPostBodyToNextRequest() before using this function.
     * See the turorial for detailed exemple</p>
     * @param relativeFileWithNoParam
     * @param parameters Classic parameters added to the request.
     * @return The content of the Response
     * @throws robusta.commons.exceptions.HttpException : When we can't send the request (malformed Uri) or if there is no server response (no connection, no server, etc.)
     * @throws robusta.commons.exceptions.RESTException : When the server sends back an error described by a status code (401, 500, etc.)
     * @todo3 : rename executePOSTmethod by executePOST to make it smaller, or even get/post/put/delete for simplicity
     */
    public String POST(String relativeFileWithNoParam, CoupleList<String, Object> parameters) throws HttpException;

    /**
     * <p>Execute a POST request toward a specified Resource, with evenutal parameters.</p>
     * <p>
     * If ApplicationUri is set to <strong>http://localhost:8080/mywebapp/</strong> and <strong>relativeFileWithNoParam</strong> is set to <strong>user/register.jsp</strong>,
     * then the request is sent to <strong>http://localhost:8080/mywebapp/user/register.jsp</strong></p>
     * <p>Params are set by a CoupleList ; null is accepted for no param. Here is an easy way to set params :</p>
     * <p>URL params <strong>?firstname=James&city=London</strong> is set by CoupleList.builder("firstname", "James","city","London")</p>
     * <p>It's also possible to set the "pure postBody", but it"s not recommanded for a GET request.</p>
     * 
     * @param relativeFileWithNoParam
     * @param parameters Classic parameters added to the request.
     * @return The content of the Response
     * @throws robusta.commons.exceptions.client.HttpException
     * @throws robusta.commons.exceptions.RestException
     */
    public String GET(String relativeFileWithNoParam, CoupleList<String, Object> parameters) throws HttpException;

    /**
     * Execute a PUT request. The _method param is <strong>NOT</strong> used neither needed.
     * @param relativeFileWithNoParam
     * @param parameters
     * @return
     * @throws robusta.commons.exceptions.client.HttpException
     * @throws robusta.commons.exceptions.RestException
     */
    public String PUT(String relativeFileWithNoParam, CoupleList<String, Object> parameters) throws HttpException;

    /**
     * Execute a DELETE request. The _method param is <strong>NOT</strong> used neither needed.
     * @param relativeFileWithNoParam
     * @param parameters
     * @return
     * @throws robusta.commons.exceptions.client.HttpException
     * @throws robusta.commons.exceptions.RestException
     */
    public String DELETE(String relativeFileWithNoParam, CoupleList<String, Object> parameters) throws HttpException;
    
    /**
     * Execute a DELETE request. The _method param is <strong>NOT</strong> used neither needed.
     * @param relativeFileWithNoParam
     * @param parameters
     * @return
     * @throws robusta.commons.exceptions.client.HttpException
     * @throws robusta.commons.exceptions.RestException
     */
    public String OTHER(String method, String relativeFileWithNoParam, CoupleList<String, Object> parameters) throws HttpException;

   
    
}

