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
 * This exception is used when there is a problem with a File.
 * In a web service context, it will often be a Server Exception (500), but it may be due to a bad parameter in the request(404)
 * @author Nicolas Zozol for Robusta Web ToolKit & http://www.edupassion.com - nzozol@robustaweb.com
 */
public class FileException extends Exception{

    public FileException(String message) {
        super(message);
    }

    public FileException(Throwable t) {
        super(t);
    }


}
