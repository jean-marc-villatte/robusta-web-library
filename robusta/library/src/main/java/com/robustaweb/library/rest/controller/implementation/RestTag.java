package com.robustaweb.library.rest.controller.implementation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;


/**
 * ResourceCOntroller for JSP pages
 * @author robusta web
 */
public class RestTag extends SimpleTagSupport {

    JspResourceController controller;

    public void setController(JspResourceController controller) {
        this.controller = controller;
        if (this.controller == null){
            throw new IllegalArgumentException("No controller");
        }
        this.controller = controller;
    }
    

    /**
     * Called by the container to invoke this tag.
     * The implementation of this method is provided by the tag library developer,
     * and handles all tag processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();

        HttpServletRequest request = (HttpServletRequest) ((PageContext) getJspContext()).getRequest();
        String line, requestBody = "", method;
        
        controller.setHttpRequest(request);

        //set method, requestBody

        //method
        method = request.getMethod().toUpperCase();
        

        //Requst BODY
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(request.getInputStream()));
            

            while ((line = br.readLine()) != null) {
            //    System.out.println("line:" + line);
                requestBody += line + "\n";
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        if (controller != null) {
            
            controller.setRequestBody(requestBody);
            controller.setMethod(method);
            
            if (method.equals("GET")){
                controller.doGet();
            }else if (method.equals("POST")){
                controller.doPost();
            }else if (method.equals("PUT")){
                controller.doPut();
            }else if (method.equals("DELETE")){
                controller.doDelete();
            }
        }


        try {         

            JspFragment f = getJspBody();
            if (f != null) {
                f.invoke(out);
            }

        } catch (java.io.IOException ex) {
            throw new JspException("Error in RequestTag tag", ex);
        }
    }

 
}
