package com.robustaweb.library.rest.resource;

import com.robustaweb.library.rest.resource.Resource;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.representation.RepresentationManager;
import com.robustaweb.library.commons.MyRobusta;
import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.util.CoupleList;

/**
 *
 * @author robusta web
 */
public class ResourceImpl implements Resource<Long> {

    Long id;

    public ResourceImpl(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Representation getRepresentation() throws RepresentationException {
        return MyRobusta.getDefaultRepresentation(this);
    }

    @Override
    public String toString() {
        return "resource " + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResourceImpl) {
            return ((ResourceImpl) obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = (int) (23 * hash + this.id);
        return hash;
    }



    @Override
    public String getPrefix() {
        return "resource";
    }

    @Override
    public String getListPrefix() {
        return "resources";
    }

    @Override
    public CoupleList<String, Object> serialize() throws RepresentationException {
        return CoupleList.build("id", this.id);
    }
}
