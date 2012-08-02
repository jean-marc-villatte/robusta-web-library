package com.robustaweb.resource;


import com.robustaweb.library.commons.MyRobusta;
import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.util.Couple;

import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.commons.util.SerializationUtils;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.resource.Resource;

/**
 * User: Nicolas
 * Date: 15/07/12
 * Time: 16:03
 */
public class User implements Resource<Couple<String, Long>>
{

    String email;
    Long id;

    @Override
    public Couple<String, Long> getId() {
        return new Couple<String, Long>(email, id);
    }

    @Override
    public void setId(Couple<String, Long> key) {
        this.email = key.getLeft();
        this.id = key.getRight();

    }

    @Override
    public String getPrefix() {
        return "user";
    }

    @Override
    public String getListPrefix() {
        return "users";
    }

    @Override
    public CoupleList<String, Object> serialize() throws RepresentationException {
        return SerializationUtils.serialize(this);
    }

    @Override
    public Representation getRepresentation() {
        return MyRobusta.getDefaultRepresentation(this);
    }
}
