package com.eclipsesource.json;

import com.eclipsesource.json.JsonObject.Member;
import de.eppleton.fx2d.tileengine.Data;
import de.eppleton.fx2d.tileengine.ObjectGroup;
import de.eppleton.fx2d.tileengine.SourceImage;
import de.eppleton.fx2d.tileengine.TObject;
import de.eppleton.fx2d.tileengine.TileMap;
import de.eppleton.fx2d.tileengine.TileMapException;
import de.eppleton.fx2d.tileengine.TileMapLayer;
import de.eppleton.fx2d.tileengine.TileMapReader;
import de.eppleton.fx2d.tileengine.TileMapSerializationEnvironment;
import de.eppleton.fx2d.tileengine.TileSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.html.canvas.Image;
import org.openide.util.lookup.ServiceProvider;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author antonepple
 */
@ServiceProvider(service = TileMapSerializationEnvironment.class)
public class DefaultTileMapSerializationEnvironment implements TileMapSerializationEnvironment {

    private static Logger LOGGER = Logger.getLogger(DefaultTileMapSerializationEnvironment.class.getName());
    String baseUrl;

    public DefaultTileMapSerializationEnvironment() {
    }

    @Override
    public TileMap readMap(String url) throws TileMapException {
        baseUrl = url.substring(0, url.lastIndexOf('/'));
        TileMap map = new TileMap();
        String toParse;
        JsonObject jsonObject;
        try {
            toParse = readFile(url);
            jsonObject = JsonObject.readFrom(toParse);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(DefaultTileMapSerializationEnvironment.class.getName()).log(Level.SEVERE, null, ex);
            throw new TileMapException("couldn't read file", ex);
        }
        map.setVersion("" + jsonObject.get("version"));
        map.setHeight(jsonObject.get("height").asInt());
        map.setWidth(jsonObject.get("width").asInt());
        map.setTileheight(jsonObject.get("tileheight").asInt());
        map.setTilewidth(jsonObject.get("tilewidth").asInt());
        if (jsonObject.get("properties") != null) {
            map.setProperties(parseProperties(jsonObject.get("properties").asObject()));
        }
        JsonArray asArray = jsonObject.get("layers").asArray();
        ArrayList<TileMapLayer> tileMapLayers = new ArrayList<TileMapLayer>();
        ArrayList<ObjectGroup> objectGroups = new ArrayList<ObjectGroup>();
        for (int i = 0; i < asArray.size(); i++) {
            JsonObject layer = asArray.get(i).asObject();
            if (layer.get("type").asString().equals("tilelayer")) {
                TileMapLayer newLayer = parselayer(map, layer);
                tileMapLayers.add(newLayer);
            } else if (layer.get("type").asString().equals("objectgroup")) {
                ObjectGroup group = parseObjectGroup(map, layer);
                objectGroups.add(group);
            }
        }
        map.setLayers(tileMapLayers);
        ArrayList<TileSet> tilesets = new ArrayList<TileSet>();
        JsonArray tilesetArray = jsonObject.get("tilesets").asArray();

        for (int i = 0; i < tilesetArray.size(); i++) {
            JsonObject asObject = tilesetArray.get(i).asObject();
            TileSet tileset = parseTileSet(asObject);
            tilesets.add(tileset);
        }
        map.setTileSetlist(tilesets);
        map.setObjectGroups(objectGroups);
        return map;
    }


    private String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
        String sCurrentLine = br.readLine();
        while (sCurrentLine != null) {
            sb.append(sCurrentLine);
            sCurrentLine = br.readLine();
        }
        return sb.toString();
    }

    private TileMapLayer parselayer(TileMap parent, JsonObject layer) {
        TileMapLayer tileMapLayer = new TileMapLayer();
        tileMapLayer.setName(layer.get("name").asString());
        tileMapLayer.setOpacity(layer.get("opacity").asDouble());
        tileMapLayer.setVisible(layer.get("visible").asBoolean());

        tileMapLayer.afterUnmarshal(parent);
        String dataString = layer.get("data").toString();
        dataString = dataString.substring(1, dataString.length() - 1);
        Data data = new Data();
        data.setContent(dataString);
        tileMapLayer.setData(data);
        return tileMapLayer;
    }

    private ObjectGroup parseObjectGroup(TileMap map, JsonObject layer) {
        ObjectGroup objectGroup = new ObjectGroup();
        objectGroup.setName(layer.get("name").asString());
        objectGroup.setOpacity(layer.get("opacity").asDouble());
        objectGroup.setVisible(layer.get("visible").asBoolean());
        if (layer.get("properties") != null) {
            objectGroup.setProperties(parseProperties(layer.get("properties").asObject()));
        }
        ArrayList<TObject> tObjects = new ArrayList<TObject>();
        JsonArray asArray = layer.get("objects").asArray();
        for (int i = 0; i < asArray.size(); i++) {
            TObject newTObject = parseTObject(asArray.get(i).asObject());
            tObjects.add(newTObject);
        }
        objectGroup.setObjectLIst(tObjects);
        objectGroup.afterUnmarshal(map);
        return objectGroup;
    }

    private TObject parseTObject(JsonObject object) {
        TObject tObject = new TObject();
        tObject.setHeight(object.get("height").asDouble());
        tObject.setName(object.get("name").asString());
        Properties properties = parseProperties(object.get("properties").asObject());
        tObject.setProperties(properties);
        tObject.setType(object.get("type").asString());
        tObject.setVisible(object.get("visible").asBoolean());
        tObject.setWidth(object.get("width").asDouble());
        tObject.setX(object.get("x").asDouble());
        tObject.setY(object.get("y").asDouble());
        return tObject;

    }

    private Properties parseProperties(JsonObject object) {
        Properties properties = new Properties();
        for (Member member : object) {
            String name = member.getName();
            JsonValue value = member.getValue();
            properties.put(name, value.toString().replace("\"", ""));
            LOGGER.info(name + " " + properties.getProperty(name));
        }
        return properties;
    }

    private TileSet parseTileSet(JsonObject asObject) {
        TileSet tileSet = new TileSet();
        tileSet.setBaseUrl(baseUrl);

        tileSet.setFirstgid(asObject.get("firstgid").asInt());
        tileSet.setMargin(asObject.get("margin").asInt());
        tileSet.setSpacing(asObject.get("spacing").asInt());
        tileSet.setTileheight(asObject.get("tileheight").asInt());
        tileSet.setTilewidth(asObject.get("tilewidth").asInt());
        tileSet.setName(asObject.get("name").asString());
        tileSet.setImage(parseImage(asObject));
        String source = TileMapReader.resourcePath(tileSet.getImage().getSource(), baseUrl);
        Image image = Image.create(source);
        tileSet.init(image);
        return tileSet;
        /* {
         "firstgid":1,
         "image":"..\/graphics\/tilesets\/tiles\/base_out_atlas.png",
         "imageheight":1024,
         "imagewidth":1024,
         "margin":0,
         "name":"Outside",
         "properties":
         {
         },
         "spacing":0,
         "tileheight":32,
         "tilewidth":32
         }*/
    }

    private SourceImage parseImage(JsonObject asObject) {
        SourceImage sourceImage = new SourceImage();
        sourceImage.setHeight(asObject.get("imageheight").asInt());
        sourceImage.setWidth(asObject.get("imagewidth").asInt());
        sourceImage.setSource(asObject.get("image").asString());

        return sourceImage;

    }
}
