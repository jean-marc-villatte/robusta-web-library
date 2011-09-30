package com.robustaweb.library.rest.resource.implementation;

import com.robustaweb.library.commons.MyRobusta;
import com.robustaweb.library.commons.exception.RepresentationException;
import com.robustaweb.library.commons.util.CoupleList;
import com.robustaweb.library.rest.representation.Representation;
import com.robustaweb.library.rest.resource.Resource;

/**
 * Simple exemple of a User implementation
 * @author n.zozol
 */
public class UserImpl implements Resource<Long> {

    public static UserImpl johnDoe = new UserImpl(1L, "john.doe@gmail.com", "John", "Doe");

    long id;
    String email;
    String firstName;
    String lastName;

    public UserImpl(long id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public CoupleList<String, Object> serialize() throws RepresentationException {
        return (CoupleList<String, Object>) CoupleList.build("id", id, "email", email, "firstName", firstName, "lastName", lastName);
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
        return firstName +" "+ lastName;
    }

    @Override
    public String getPrefix() {
        return "user";
    }

    @Override
    public String getListPrefix() {
        return "users";
    }



}
