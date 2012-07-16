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
package com.robustaweb.library.commons.util;

import com.robustaweb.library.rest.resource.Resource;

import java.lang.reflect.Field;

/**
 * Workaround support for simple Releflection unsing Java Reflection API
 * @author n.zozol
 */
public class SerializationUtils {

   

    /**
     *
     * Any IllegalAccessException will be written in the Couple right value
     * @param object
     * @return
     */
    public static CoupleList<String, Object> serialize(Object object){
        CoupleList<String, Object> cp = new CoupleList<String, Object>();


        //Checking first all superclass of the Resource
        Class current = object.getClass();
        while (current != null){
            cp.addAll(getClassItems(current, object));
            current = current.getSuperclass();
        }
        return cp;
    }

    /**
     * Any error will be written in the couples
     * @param c
     * @param object
     * @return
     */
    private  static CoupleList<String, Object> getClassItems(Class c, Object object){
        CoupleList<String, Object> cp = new CoupleList<String, Object>();

        Field[] fields = c.getDeclaredFields();
        Couple <String, Object> couple;
        for (Field f : fields){
            f.setAccessible(true);
            try {
                Object value = f.get(object);
                if (value instanceof Resource){
                     //adding another couple with the key
                    Resource r = (Resource) value;
                    Couple keyCouple = new Couple<String, Object>(f.getName()+"Id", r.getId());
                    cp.add(keyCouple);
                }
                couple = new Couple<String, Object>(f.getName(),value);
            } catch (IllegalArgumentException ex) {
                couple = new Couple<String, Object>(f.getName(), ex.getMessage());
            } catch (IllegalAccessException ex) {
                couple = new Couple<String, Object>(f.getName(), ex.getMessage());
            }
            cp.add(couple);
        }

        return cp;
    }

    /**
     * 
     * @param prefix
     * @param obj
     * @return
     */
    public String toXml(String prefix, Object obj){
        return XmlUtils.build (null, prefix, serialize(obj));
    }

    /**
     * Problem of Json is that we need to distinguish
     * @return
     */
    public static String toJson(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
