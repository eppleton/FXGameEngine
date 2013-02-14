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
package de.eppleton.fx2d.tileengine;

import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author antonepple
 */
public class Data {

    private String content;
    private String encoding;
    private int[] gids;
    private boolean dirty = false;

    @XmlAttribute
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @XmlValue
    public String getContent() {
        if (dirty) {
            updateContent();
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGid(int idx) {
        initGids();
        return gids[idx];
    }

    public void setGid(int i, int gid) {
        initGids();
        gids[i] = gid;
        dirty = true;
    }
    private static final Logger LOG = Logger.getLogger(Data.class.getName());

    private void updateContent() {

        if (gids != null) {
            content = "";
            for (int i = 0; i < gids.length; i++) {
                content += "" + gids[i] + ",";

            }
            content = content.substring(0, content.lastIndexOf(",") );
        }
    }

    public void initGids() throws NumberFormatException {
        if (gids == null) {
            String[] gidStrings = content.trim().split(",");
            gids = new int[gidStrings.length];
            for (int i = 0; i < gidStrings.length; i++) {
                String string = gidStrings[i];
                gids[i] = Integer.valueOf(string.trim());
            }
        }
    }
}
