/**
 * This file is part of FXGameEngine 
 * A Game Engine written in JavaFX
 * Copyright (C) 2012 Anton Epple <info@eppleton.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://opensource.org/licenses/GPL-2.0.
 * 
 * For alternative licensing or use in closed source projects contact Anton Epple 
 * <info@eppleton.de>
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
