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
 *  CodecException is used when there is impossibility to encode or decode some datas
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public class CodecException extends Exception {

    public CodecException() {
        super();
    }
    
    public CodecException(String message) {
        super(message);
    }

    public CodecException(Throwable t){
        super(t);
    }

    
    
}