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
package com.robustaweb.library.rest.client.implementation;

import com.robustaweb.library.commons.exception.RestException;
import com.robustaweb.library.rest.client.AsynchronousRestClient;

/**
 * @TODO notice
 * A Simple Callback. Some D datas can be set and retrieve. Override methods needed ; Extend this callback
 * Use CallbackSimple&lt;Void> if no datas is set.
 * @author Nicolas Zozol for Robusta Web Library & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public class SimpleCallback implements Callback{

    public static boolean debug = true;
    
   
    AsynchronousRestClient client;

    public SimpleCallback(AsynchronousRestClient client) {
        this.client = client;
    }

   

    /**
     * This implementation doesn't do anything. Overwrite it to extend functionnalities.
     * @param client
     * @param response
     */
    @Override
    public void onSuccess( String response, int code) {
        if (debug) {
            System.out.println("Debug SimpleCallback : Successful request");
        }
    }

    /**
     * This implementation doesn't do anything. Overwrite it to extend functionnalities.
     * @param client
     * @param response
     */
    @Override
    public void onFailure(RestException failure) {
        if (debug) {
            System.out.println("Debug SimpleCallback : Request failed :" + failure);
        }
    }
    

    /**
     * This implementation doesn't do anything. Overwrite it to extend functionnalities.
     * @param client
     * @param response
     */
    @Override
    public void onComplete() {
        if (debug) {
            System.out.println("Debug SimpleCallback : Request complete");
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public AsynchronousRestClient getClient() {
        return this.client;
    }

    /**
     * This implementation throws a new RuntimeException. Overwrite it to extend functionnalities.
     */
    @Override
    public void onException(Exception exception) {
        throw new RuntimeException(exception);
    }


    
}
