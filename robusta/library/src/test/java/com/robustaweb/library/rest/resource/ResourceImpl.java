package com.robustaweb.library.rest.resource;

import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.commons.MyRobusta;
import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.util.CoupleList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        if (obj instanceof com.robustaweb.library.rest.resource.implementation.ResourceImpl) {
            return ((com.robustaweb.library.rest.resource.implementation.ResourceImpl) obj).getId() == this.getId();
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
    public HashMap<String, Object> serialize() throws RepresentationException {
        return CoupleList.<String, Object>build("id", this.id).getHashMap();
    }

    public List<ResourceImpl> createList(){
        int count = 0;
        List<ResourceImpl> list = new ArrayList<ResourceImpl>(5);
        for (long i = 0 ; i < 5 ; i++){
            list.add(new ResourceImpl(i));
        }
        return list;
    }
}
