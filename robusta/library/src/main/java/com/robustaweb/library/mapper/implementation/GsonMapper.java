package com.robustaweb.library.mapper.implementation;

import com.google.gson.Gson;
import com.robustaweb.library.mapper.Mapper;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.representation.implementation.GsonRepresentation;
import com.robustaweb.library.rest.resource.Resource;

/**
 * Created by Nicolas Zozol
 * Date: 12/08/12
 */
public class GsonMapper implements Mapper<Gson,GsonRepresentation>{


    @Override
    public <R extends Resource> R getResource (GsonRepresentation representation, Class <R> resourceClass) {
        return null;
    }

    @Override
    public GsonRepresentation getRepresentation(Resource resource) {
        return null;
    }

    @Override
    public Gson getUnderlyingLibrary() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
