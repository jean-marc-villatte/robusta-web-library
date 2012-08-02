package com.robustaweb.demojee;

import com.robustaweb.library.commons.MyRobusta;
import com.robustaweb.library.commons.util.Couple;
import com.robustaweb.library.rest.controller.ResourceController;
import com.robustaweb.library.rest.controller.implementation.JaxRsResourceController;
import com.robustaweb.library.rest.representation.implementation.JdomRepresentation;
import com.robustaweb.library.rest.representation.implementation.JsonSimpleRepresentation;
import com.robustaweb.resource.User;
import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.xml.ws.Response;

/**
 * User: Nicolas
 * Date: 14/07/12
 * Time: 14:54
 */
@Path("/test")
public class TestController extends JaxRsResourceController{




    @GET
    @Produces("text/html")
    public String get(){
        MyRobusta.setDefaultRepresentation(new JdomRepresentation());
        User user = new User();
        user.setId(new Couple<String, Long>("1245-45", 18L));
        return user.getRepresentation().toString();
    }

    @Path("json")
    @GET
    @Produces("application/json")
    public String getJson(){
        User user = new User();
        user.setId(new Couple<String, Long>("1245-45", 18L));
        JsonSimpleRepresentation rep = new JsonSimpleRepresentation(new JSONObject());
        MyRobusta.setDefaultRepresentation(rep);


        return user.getRepresentation().toString();
    }



}
