/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.eppleton.fx2d.dukescript;

import net.java.html.js.JavaScriptBody;
import net.java.html.js.JavaScriptResource;

/**
 *
 * @author antonepple
 */
@JavaScriptResource("jquery-1.11.0.min.js")
public class JQuery {
    
    @JavaScriptBody(args = {},body="")
    public static native void init();
    
    
}
