package com.robustaweb.library.mapper;

import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.resource.Resource;
import com.robustaweb.library.rest.resource.ResourceList;

import java.util.List;

/**
 * Created by Nicolas Zozol
 * Date: 12/08/12
 */
public interface Mapper <Lib, Rep extends Representation> {

    <R extends Resource> R getResource (Rep representation, Class <R> resourceClass);

    /**
     * TODO
     * @param representation
     * @param resourceClass
     * @return
     *
    ResourceList<Resource> getResourceList(Rep representation, Class <? extends Resource> resourceClass);
    */

    Rep getRepresentation (Resource resource);

    <T> T getObject (Object o, Class <T> objectClass);

    /**
     *
     * @param o
     * @param objectClass
     * @param <T>
     * @return
     *
    <T> List<? extends T> getList (Object o, Class <T> objectClass);
    */

    /**
     *
     * @param representation
     * @param objectClass
     * @param <T>
     * @return
     * <T> T getObject (Representation representation, Class <T> objectClass);
     */


    Lib getUnderlyingLibrary ();


    //There should be a notion of depth : eventually at -1 if no depth needed
    int INFINITE_DEPTH = Integer.MIN_VALUE;


}
