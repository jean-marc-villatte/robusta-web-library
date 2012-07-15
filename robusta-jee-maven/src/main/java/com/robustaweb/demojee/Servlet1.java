package com.robustaweb.demojee;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: Nicolas
 * Date: 15/07/12
 * Time: 15:53
 */
public class Servlet1 extends GenericServlet{
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        try
        {
            PrintWriter out = servletResponse.getWriter() ;
            out.println ("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\">") ;
            out.println ("<title>Bonjour tout le monde&amp;nbsp;!</title>") ;
            out.println ("<p>Hello world!</p>") ;
        }
        catch (IOException e)
        {
            e.printStackTrace() ;
        }
    }
}
