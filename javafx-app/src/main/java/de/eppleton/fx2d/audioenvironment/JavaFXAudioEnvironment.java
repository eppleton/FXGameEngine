/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.eppleton.fx2d.audioenvironment;

import org.apidesign.html.sound.spi.AudioEnvironment;
import org.openide.util.lookup.ServiceProvider;
import javafx.scene.media.AudioClip;


@ServiceProvider(service = AudioEnvironment.class, position = 50)
public class JavaFXAudioEnvironment implements AudioEnvironment<AudioClip>{

    public AudioClip create(String src) {
       return new AudioClip(src);
    }

    public void play(AudioClip a) {
       a.play();
    }

    public void pause(AudioClip a) {
        a.stop();
    }

    public void setVolume(AudioClip a, double volume) {
       a.setVolume(volume);
    }

    public boolean isSupported(AudioClip a) {
       return true;
    }

    
}
