package com.robustaweb.library.rest.resource.implementation;

import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.commons.util.ReflectionUtils;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.representation.implementation.JdomRepresentation;
import com.robustaweb.library.rest.resource.Resource;

/**
 * User: Nicolas
 * Date: 15/07/12
 * Time: 16:20
 */
public class ResourceImpl<IdType> implements Resource<IdType> {

    IdType id;

    @Override
    public IdType getId() {
        return this.id;
    }

    @Override
    public void setId(IdType id) {
        this.id = id;
    }

    @Override
    public String getPrefix() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getListPrefix() {
        return this.getPrefix()+"s";
    }

    @Override
    public CoupleList<String, Object> serialize() throws RepresentationException {
        return ReflectionUtils.basicSerialization(this);
    }

    @Override
    public Representation getRepresentation() {
        return new JdomRepresentation(this.getPrefix(), this.serialize());
    }
}
