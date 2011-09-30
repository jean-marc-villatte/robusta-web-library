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
import com.robustaweb.library.commons.exception.RestException;
import com.robustaweb.library.commons.util.CoupleList;

/**
 * Interface describing a Http Client making Asynchronous requests
 * It uses a Callback more close of Prototype/Jquery
 * @author robusta web
 * @see Callback
 */
public interface AsynchronousRestClient<Client> extends RestClient<Client> {

  
    /**
     * <p>Execute a GET Http request. The relative apth compares to the AbsolutePath</p>
     * <p>For exemple, if ApplicationUri has been set to http://exemple.com/mycontext/, a relative url of students/johndoe will set
     * a GET request to the http://exemple.com/mycontext/johndoe resource.</p>
     * <p>The request handles parameters that will be added to the url request. You might use CoupleList.build(param1, value1, param2, value2) to set these
     * parameters.</p>
     * <p>AsyncCallback<String> refers to GWT </p>
     * @param relativePath  compare to ApplicationUri path
     * @param parameters
     * @param callback
     * @throws HttpException
     * @throws RestException
     */
    public void GET(String relativePath, CoupleList<String, Object> parameters, Callback callback) throws HttpException;

    public void POST(String relativePath, CoupleList<String, Object> parameters, Callback callback) throws HttpException;

    public void PUT(String relativePath, CoupleList<String, Object> parameters, Callback callback) throws HttpException;

    public void DELETE(String relativePath, CoupleList<String, Object> parameters, Callback callback) throws HttpException;
    
    /**
     * Execute any othe method, including WEBDAV methods, or the PATCH method
     * @param method
     * @param relativePath
     * @param parameters
     * @param callback
     * @throws HttpException
     */
    public void OTHER(String method, String relativePath, CoupleList<String, Object> parameters, Callback callback) throws HttpException;
    
   
    

    public void join();

        
}
