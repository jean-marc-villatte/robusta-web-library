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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Small class for commn tasks on Lists
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 */
public class ListUtils {

   

    /**
     * The list will be modified if all elements are not unique.
     * <p>
     * For exemple, if list is 3,4,5,3,5,3,0,11, then after setUnicity(list), list will be 3,4,5,0,11<br>
     * The function removes element with highest index in the list
     * </p>
     * @param list the list to modify
     */
    public static void setUnicity(List list) {

        if (list == null) {
            return ;
        }

        if (list.size()<=1) return ;
           
        Object current;

        for (int i = 0; i < list.size(); i++) {
            current = list.get(i);

            int index = list.lastIndexOf(current);
            if (index!=i) {
                list.remove(index);
                setUnicity(list);
            }            
        }

    }
    
    public static  <T>  List<T> list(Collection<T> collection ){
    	ArrayList<T> list = new ArrayList<T>();
    	for (T t : collection){
    		list.add(t);
    	}
    	return list;
    }

    
    public static String join(String glue, Collection collection){
    	
    	StringBuilder result = new StringBuilder();
    	Iterator it = collection.iterator();
    	while(it.hasNext()){
    		Object o = it.next();
    		result.append(o.toString());
    		if (it.hasNext()){
    			result.append(glue);
    		}
    	}
    	
    	return result.toString();
    }
     




    
}
