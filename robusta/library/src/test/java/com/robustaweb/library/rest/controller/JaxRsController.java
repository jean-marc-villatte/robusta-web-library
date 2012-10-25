package com.robustaweb.library.rest.controller;

import com.robustaweb.library.rest.controller.implementation.JaxRsResourceController;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas Zozol
 * Date: 18/10/12
 */
@Path("root")
public class JaxRsController extends JaxRsResourceController{

    List<String> peoples = new ArrayList<String>();

    @PostConstruct
    public void fillList(){
        peoples.add("Nicolas");
        peoples.add("jack");
    }

    @GET
    public String hello(){
        System.out.println("Uri is "+getUri());
        return "Hello "+peoples;
    }

    @POST
    public String addPeople(@QueryParam("name")String name){
        this.peoples.add(name);
        return String.valueOf(peoples.size());
    }

    @PUT
    public String replacePeople(@QueryParam("name")String name, @QueryParam("new-name")String newName){
        this.peoples.remove(name);
        this.peoples.add(name);
        return String.valueOf(peoples.size());
    }

    @DELETE
    @Path("{name}")
    public String deletePeople(@PathParam("name") String name){
        System.out.println("deleting "+name);
        this.peoples.remove(name);
        return String.valueOf(peoples.size());
    }
}