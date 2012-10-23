package com.robustaweb.library.rest.controller;

import com.robustaweb.library.rest.controller.implementation.JaxRsResourceController;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas Zozol
 * Date: 18/10/12
 */
@Path("root")
public class JaxRsController extends JaxRsResourceController{



    public void before(){
        System.out.println("");
        peoples.add("Nicolas Zozol");
    }

    List<String> peoples = new ArrayList<String>();


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
