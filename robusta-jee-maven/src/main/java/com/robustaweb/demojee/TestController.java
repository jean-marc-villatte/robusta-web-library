package com.robustaweb.demojee;

import com.robustaweb.library.rest.controller.ResourceController;
import com.robustaweb.library.rest.controller.implementation.JaxRsResourceController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.ws.Response;

/**
 * User: Nicolas
 * Date: 14/07/12
 * Time: 14:54
 */
@Path("test")
public class TestController extends JaxRsResourceController{


    @GET
    @Produces("text/html")
    public String get(){
        return "JO";
    }

}
