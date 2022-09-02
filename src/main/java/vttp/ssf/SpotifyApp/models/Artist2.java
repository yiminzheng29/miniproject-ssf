package vttp.ssf.SpotifyApp.models;

import java.io.Reader;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Artist2 {
    private String url;
    private String name;

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public static Artist2 create(String str) {
        Reader r = new StringReader(str);
        JsonReader jr = Json.createReader(r);
        return create(jr.readObject());
    }

    public static Artist2 create(JsonObject jo) {
        Artist2 artist = new Artist2();
        artist.setUrl(jo.getJsonObject("external_urls").getString("spotify"));
        artist.setName(jo.getString("name"));
        return artist;
    }

    public static Artist2 create2(String str) {
        Reader r = new StringReader(str);
        JsonReader jr = Json.createReader(r);
        return create(jr.readObject());
    }

    public static Artist2 create2(JsonObject jo) {
        Artist2 artist = new Artist2();
        artist.setUrl(jo.getString("spotify"));
        artist.setName(jo.getString("name"));
        return artist;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("name", name)
            .add("spotify", url)
            .build();    
        }
}
