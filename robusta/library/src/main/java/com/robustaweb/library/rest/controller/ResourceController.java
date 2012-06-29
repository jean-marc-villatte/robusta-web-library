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
package com.robustaweb.library.rest.controller;

import com.robustaweb.library.commons.util.Couple;
import com.robustaweb.library.commons.util.CoupleList;

/**
 * This interface controls the data access to a Resource when a User wants to manipulate it. It allows or not the user, and execute his request.
 * <p>
 * Note that there is no reference to the robusta.rest.Resource, wich allow you to be much flexible.
 * </p>
 * <p>
 * <strong>If your Entry Point is a JSP</strong>, you could use the &lt;robusta:request/> Tag (see documentation on <a href="http://www.robustaweb.com/robusta">RobustaWeb.com</a>).
 * This little Tag will help you to catch datas from the request.<br>
 * The &lt;robusta:response/> Tag has less responsabilities, but will nevertheless help you to give back a response to the user
 * </p>
 * @author Nicolas Zozol - Edupassion.com - Robusta Web - nzozol@edupassion.com
 */
public interface ResourceController <HttpRequest>{


    /**
     * This is the URI aimed by the request : for exemple, if the complete URL is
     * http://www.myapp.com:8080/webapp/user/12?email=robusta@gmail.com
     * then the URI is : /webapp/user/12
     */
    public String getUri();

    /**
     * Method should be GET, POST, PUT, DELETE or valid HTTP, WEBDAV or other methods.
     * Note that implementations might handle differently the <strong>_method</strong> problem of Ajax requests.
     * You can also use the <robusta:request/> Tag (see documentation on <a href="http://www.robustaweb.com/robusta">RobustaWeb.com</a>)
     * @param method
     */

    public HttpRequest getHttpRequest();

    
    /**
     * Returns the Authorization key of the incoming Http request
     * It's often in the Authorization header, but may be in a cookie.
     */
    public String getAuthorizationValue();

    /**
     * Returns the credentials of the request. Default value gives the username/SHA(salt(password)) of a user
     * Here is a good way to use salt and hash : http://crackstation.net/hashing-security.htm
     * @return
     */
    public Couple<String, String> getCredentials();



    /**
     * return the IP Adress of the User
     * @return
     */
    public String getRemoteIp();


    /**
     * Returns a simplified name/value list of Cookies - whereas javax.servlet.http.Cookie also contains informations on path, domain, etc...
     * It returns an empty list if there is no cookie.
     * @return
     */
    public CoupleList<String, String> getCookies();

    /**
     * Returns request headers. It returns an empty list in the very strange case where there is no header.
     * @return request headers
     */
    public CoupleList<String, String> getHeaders();

    /**
     * Returns query params. It returns an empty list if there is no param.
     * Note that some header like Accept-Langage returns sometimes many values ; this method will return only one String, so you
     * may prefer to use the getRequest().getHeaders('Accept-Langage');
     * @return query params
     */
    public CoupleList<String, String> getParams();

    /**
     * It's a good idea to have a different HttpHeader named request-id for each request, to avoid bad returns with 200 Http code by Proxies.
     * This happens mainly with shared wifi in Airports, coffees or conferences.
     * @return value of the HttpRequest
     */
    public String getRequestUuid();
   
}














