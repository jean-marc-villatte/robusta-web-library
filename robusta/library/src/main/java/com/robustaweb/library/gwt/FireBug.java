/*
 * Copyright 2007-2011 Nicolas Zozol
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robustaweb.library.gwt;

/**
 *
 * @author Nicolas Zozol for Robusta Web ToolKit & <a href="http://www.edupassion.com">Edupassion.com</a> - nzozol@robustaweb.com
 * @todo1 : Remove log level, console.dir, and put object in console.log (though in Javascript....) 
 * @todo2 : to be tested in real conditions
 * @todo2 : add the Firefox verification into isFireBug() method
 */
public class FireBug {

    public static final int ALWAYS = 0, INFO = 5, DEBUG = 10;
    static boolean showAll = true;
    static int logLevel = ALWAYS;

    public static void log(Object object) {

        if (!showAll) {
            return;
        }

        if (isFireBug()) {
            clog(object.toString());
        } else {
            System.out.println(object);
        }
    }

    public static void error(Object o, boolean trace) {
        if (isFireBug()) {
            cerror(o.toString());
            if (trace) {
                ctrace();
            }
        } else {
            System.out.println(o);
        }
    }

    /**
     * Default level is Always
     * @param logLevel
     */
    public static void setLogLevel(int logLevel) {
        FireBug.logLevel = logLevel;
    }

    /**
     * Put the Object representation in the console
     * @param object object to display
     */
    public static void dir(Object object) {
        cdir(object);
    }

    /**
     * Test if Firebug is active.
     * @todo2 : check also that this is Firefox
     * @return
     */
    private native static boolean isFireBug()/*-{
    try{
     //checking isFirefox
     var userAgent = navigator.userAgent.toLowerCase();
     ff =   !( /webkit/.test(userAgent) ) && /gecko/.test(userAgent);
     if (!ff){
      return false;
     }

     if (console !=null && console!=undefined)
     {
      return true;
     }
    }
    catch(e){return false;}
    }-*/;

    private native static void clog(String text)/*-{
    try{
    console.log(text)
    }catch(e){}
    }-*/;

    private native static void cerror(String error)/*-{
    try{
    console.error(error);
    }catch(e){}
    }-*/;

    private native static void ctrace()/*-{
    try{
    console.trace();
    }catch(e){}
    }-*/;

    private native static void cdir(Object object)/*-{
    try{
    console.dir(object);
    }catch(e){}
    }-*/;
}
