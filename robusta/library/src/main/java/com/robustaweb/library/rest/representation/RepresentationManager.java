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
package com.robustaweb.library.rest.representation;

import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.rest.resource.Resource;

/**
 * Gives the xml format of a Resource, and build a Resource object form the Xml representation.
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public interface RepresentationManager<R extends Resource> {
    


    /**
     * 
     * @return the xml representation of this Ressource
     */
    public Representation getRepresentation(R resource) throws RepresentationException;
    
    /**
     * Decode a Resource Representation, and create the Resource object
     * @param representation : the Resource representation
     * @return the Resource
     * @throws RepresentationException
     * 
     */
    public R getResource(String representation) throws RepresentationException;
   
}