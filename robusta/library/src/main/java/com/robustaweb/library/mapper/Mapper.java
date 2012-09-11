package com.robustaweb.library.mapper;

import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.resource.Resource;

/**
 * Created by Nicolas Zozol
 * Date: 12/08/12
 */
public interface Mapper <Lib, Rep extends Representation> {

    <R extends Resource> R getResource (Rep representation, Class <R> resourceClass);

    Rep getRepresentation (Resource resource);

    <T> T getObject (Object o, Class <T> resourceClass);

    Lib getUnderlyingLibrary ();
}
