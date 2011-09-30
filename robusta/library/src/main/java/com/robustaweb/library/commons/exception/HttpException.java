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
 * This Exception is thrown when an error in the Request is detected before this request is sent.<br/>
 * Exemple : malformed URL, or PostBody datas in a GET Request (depending on implementations).
 * 
 * <p>
 * This Exception is typically thrown by a HttpClient.
 * </p>
 * 
 * @author Nicolas Zozol for Robusta Web ToolKit & http://www.edupassion.com - nzozol@robustaweb.com
 * @todo2 : we may find a better name, describing that there is a Client exception avoiding sending request
 * @todo3 : extends RuntimeException
 */
public class HttpException  extends RuntimeException{

    public HttpException( String message, Throwable underlyingException){
        super(message, underlyingException);
    }

    public HttpException(String message){
        super(message);
    }

}