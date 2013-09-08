package de.eppleton.fx2d.samplegames.bck2brwsr;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.apidesign.bck2brwsr.core.JavaScriptBody;

/**
 *
 * @author antonepple
 */
public class Logger {

    @JavaScriptBody( args = {"message"},
            body = "console.log(message)")
    public static native void log(String message);
    
    @JavaScriptBody( args = {"message"},
            body = "console.log(message)")
    public static  native void logObject(Object message);
    
    @JavaScriptBody(args = { "src" }, body = "console.log(src);var a = new Audio(src);console.log(a);a.play();")
    public static native void play(Object a);

}
