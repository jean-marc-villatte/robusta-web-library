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
public interface SynchronousRestClient<Response>{




    public Response GET(String path, CoupleList<String, Object> parameters) throws HttpException;

    public Response POST(String path, CoupleList<String, Object> parameters) throws HttpException;

    public Response POST(String path, String body) throws HttpException;

    public Response PUT(String path, CoupleList<String, Object> parameters) throws HttpException;

    public Response PUT(String path, String body) throws HttpException;

    public Response DELETE(String path, CoupleList<String, Object> parameters) throws HttpException;
    

    
}

