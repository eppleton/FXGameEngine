/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.eppleton.fx2d.samples.test;

import de.eppleton.fx2d.AbstractGame;
import de.eppleton.fx2d.Game;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author antonepple
 */
@ServiceProvider(service= Game.class)
public class HelloWorld extends AbstractGame{

    public HelloWorld() {
        addLevel(new HelloWorldLevel( 1000,1000,1000,1000));
    }
    
    
    
}
