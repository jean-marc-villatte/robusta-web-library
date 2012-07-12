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
package com.robustaweb.library.rest.controller.implementation;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.robustaweb.library.commons.util.Couple;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.rest.controller.ResourceController;


/**
 * @todo2 : this class is pretty old, and needs to be cleared : most stuff must be removed, and design of use must be much simpler.
 * Implementation of {@link ResourceController} for JSP based REST web services. Read with attention the tutorial at <a href="http://www.robustaweb.com">Robustaweb.com</a>.
 * @author Nicolas Zozol - Edupassion.com - Robusta Web nzozol@edupassion.com
 */
public class JspResourceController implements ResourceController<HttpServletRequest> {

    @Override
    public Couple<String, String> getCredentials() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getRequestUuid() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    String requestBody;
    HttpServletResponse httpResponse;
    HttpServletRequest httpRequest;
    String method;

    public JspResourceController() {
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    @Override
    public String getAuthorizationValue() {
        return getParams().getHashMap().get("authorization");
    }

    @Override
    public String getRemoteIp() {
        return getHttpRequest().getRemoteAddr();
    }

    public void setResponse(HttpServletResponse response) {
        this.httpResponse = response;
    }

    public HttpServletResponse getResponse() {
        return httpResponse;
    }

    @Override
    public String getUri() {
        return getHttpRequest().getRequestURI();
    }

    /**
     * Function that handle GET request<br/>
     * Return false if the server did a mistake or if the method is not allowed. You cn decide to return true or false if there is a Client error.
     * Check the getStatusCode function
     * @return false if the server did an error or if the method is not allowed
     */
    public void doGet() {
    }

    /**
     * Function that handle POST request<br/>
     */
    public void doPost() {
    }

    /**
     * Function that handle PUT request<br/>
     */
    public void doPut() {
    }

    /**
     * Function that handle DELETE request<br/>
     */
    public void doDelete() {
    }
    
    /**
    * Function that handle other requests<br/>
    */
    public void doOther(){
    	
    }

    public void setHttpRequest(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void setHttpResponse(HttpServletResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    @Override
    public HttpServletRequest getHttpRequest() {
        return this.httpRequest;
    }

    public HttpServletResponse getHttpResponse() {
        return httpResponse;
    }

    @Override
    public CoupleList<String, String> getCookies() {

        CoupleList<String, String> result = new CoupleList<String, String>();
        /* Getting cookies */
        javax.servlet.http.Cookie[] cookies = getHttpRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                result.addCouple(cookie.getName(), cookie.getValue());
            }
        }

        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public CoupleList<String, String> getParams() {
        CoupleList<String, String> result = new CoupleList<String, String>();

        Enumeration en = getHttpRequest().getParameterNames();
        while (en.hasMoreElements()) {
            String name = en.nextElement().toString();
            result.addCouple(name, getHttpRequest().getParameter(name));
        }

        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public CoupleList<String, String> getHeaders() {
        CoupleList<String, String> result = new CoupleList<String, String>();

        Enumeration en = getHttpRequest().getHeaderNames();
        while (en.hasMoreElements()) {
            String name = en.nextElement().toString();
            result.addCouple(name, getHttpRequest().getHeader(name));
        }
        return result;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
