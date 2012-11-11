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
package com.robustaweb.library.commons.exception;

/**
 *  Exception when a request sends an Http code in a <strong>RESTful</strong> context ; this code will be between 400 & 600.
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 * @todo1 : create a RepresentationManager for Exception ?
 */
public class RestException extends Exception {

    int httpCode;
    String rawResponse;

    /**
     * Create a REST exception with a 400-599 Http Code, a message explaining the failure, and the raw server response
     * This is usually made by the RestCleint when it receives a 400-599 Http Code.
     * @param httpCode Http Code sent by the server
     * @param message an explanation
     * @param rawResponse the response sent by the server
     * TODO : switch code with response
     */
    public RestException(int httpCode, String rawResponse) {
        
        this.httpCode = httpCode;
        this.rawResponse = rawResponse;
    }

    /**
     * Returns the Http Code
     * @return the Http Code
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * Returns the response sent by the server
     * @return the server's response
     */
    public String getRawResponse() {
        return rawResponse;
    }
}

