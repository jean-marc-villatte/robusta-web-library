package com.robustaweb.controller;

import com.robustaweb.library.commons.util.FileUtils;
import com.robustaweb.library.commons.util.StringUtils;
import com.robustaweb.library.rest.controller.implementation.JaxRsResourceController;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @POST
    @Path("multi-http-params")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String getParams(@DefaultValue("All") @QueryParam("multi")List<String> multiParams) throws IOException {
        UriInfo context = this.getContext();
        MultivaluedMap map = context.getQueryParameters();
        System.out.println("===>"+this.getAuthorizationValue());
        System.out.println(">>>"+FileUtils.readInputStream(this.getHttpRequest().getInputStream()));
        Set set = map.entrySet();
        for (Object o : set){
            System.out.println(o);
        }

        return StringUtils.join(multiParams, " ; ");
    }
}