package com.robustaweb.library.rest.representation;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Nicolas
 * Date: 04/10/12
 * Time: 22:26
 */
public class SomeClass {
    static int i=0;


    int id;

    public SomeClass(){
        this.id = i++;
    }

    public static List<SomeClass> createList(){
        i =0;
        List<SomeClass> list = new ArrayList<SomeClass>(5);
        for (int j = 0 ; j < 5 ; j++){
            assert(j == i);
            list.add(new SomeClass());
        }
        return list;
    }
}
