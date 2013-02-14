/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
