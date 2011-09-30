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
package com.robustaweb.library.rest.resource;

import java.util.ArrayList;

import com.robustaweb.library.commons.exception.RepresentationException;

/**
 * Utility class to work on list of Resources. It also help the Xml writing of this list.
 * @author Nicolas Zozol - Edupassion.com - Robusta Web - nzozol@edupassion.com
 */
public class ResourceList<T extends Resource<IdType>, IdType> extends ArrayList<T> {

    String xml;

    /**
     * create a List with T that extends Ressource
     * @param v
     */
    public ResourceList(ArrayList<T> v) {
        super();
        this.addAll(v);
    }

    /**
     * create a List with T that extends Ressource
     * @param v
     */
    public ResourceList(T[] resources) {
        super();

        if (resources == null) {
            throw new IllegalArgumentException("Resources array is null");
        }
        for (T resource : resources) {
            this.add(resource);
        }
    }

    /**
     * Creates an empty List that you must fill with Ressources
     */
    public ResourceList() {
        super();
    }

    @Override
    public T get(int index) {
        return super.get(index);
    }

    /**
     * @param id
     * @return the ressource with specified id, or null if not found
     */
    public T getById(long id) {
        T resource;
        for (int i = 0; i < this.size(); i++) {
            resource = this.get(i);
            if (resource.getId().equals(id)) {
                return resource;
            }
        }
        return null;
    }

    /**
     * remove the FIRST item of the list with this id and returns it.
     * Returns null if we can't find it.
     * @param id
     * @return
     */
    public T removeById(IdType id) {
        T resource;
        for (int i = 0; i < this.size(); i++) {
            resource = this.get(i);
            if (resource.getId().equals(id)) {
                this.remove(resource);
                return resource;
            }
        }
        return null;
    }

    /**
     * Return true if one of the Resources has the id 
     * @param id
     * @return
     */
    public boolean containsId(Long id) {
        if (id == null) {
            return false;
        } else {
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getId() == id) {
                    return true;
                }
            }
        }

        //not found
        return false;
    }

    /**
     * This function replace the resource with id by the new Resource
     * @return true if it has replaced
     */
    public boolean replace(IdType id, T resource) {

        int index;
        for (T t : this) {
            if (t.getId().equals(id)) {
                index = this.indexOf(t);
                this.remove(t);
                this.add(index, resource);
                return true;
            }
        }
        return false;
    }

    /**
     * return something like :
     * <resourceList>
     * <resource>
     * <class>pack.MyObject</class>
     * <id>28</id>
     * <name>this object name</name>
     * ...
     * </resource>
     * <resource>
     * <class>pack.MyObject</class>
     * <id>29</id>
     * <name>this otherobject name</name>
     * ...
     * </resource>
     * ...
     * 
     * </resourceList>
     * @return the xml representation
     */
    public String getXmlRepresentation() throws RepresentationException {

        String result = "<resourceList>\n";

        for (int i = 0; i < this.size(); i++) {
            result += this.get(i).getRepresentation() + "\n";
        }

        result += "\n</resourceList>";
        return result;

    }

    /**
     * return something like :
     * <list>     
     * <id>28</id>
     * <id>29</id>
     * </list>
     * 
     * This is on the purpose to economize the bandwidth
     * @return the xml representation of ONLY the indexes - Be careful of what is inside
     */
    public String getXmlIds() {
        String result = "<list>\n";

        for (int i = 0; i < this.size(); i++) {
            result += "<id>";
            result += String.valueOf(this.get(i).getId());
            result += "</id>\n";
        }

        result += "\n</list>";
        return result;
    }

    /**
     *
     * @return a arraylist of all ids
     */
    public ArrayList<IdType> getIds() {
        ArrayList<IdType> ids = new ArrayList<IdType>();
        for (Resource<IdType> r : this) {
            ids.add(r.getId());
        }
        return ids;
    }

   



}//End of class










