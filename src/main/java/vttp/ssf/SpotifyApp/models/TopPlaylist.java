package vttp.ssf.SpotifyApp.models;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class TopPlaylist {
    
    private String description;
    private String url;
    private String spotifyUrl;
    private String name;

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    
    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
    
    public String getSpotifyUrl() {return spotifyUrl;}
    public void setSpotifyUrl(String spotifyUrl) {this.spotifyUrl = spotifyUrl;}
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public static TopPlaylist create(String s) {
        Reader r = new StringReader(s);
        JsonReader jr = Json.createReader(r);
        return create(jr.readObject());
    }

    public static TopPlaylist create (JsonObject jo) {
        TopPlaylist tops = new TopPlaylist();
        tops.setDescription(jo.getString("description"));
        tops.setName(jo.getString("name"));
        List<String> urlList = new LinkedList<>();
        JsonArray img = jo.getJsonArray("images");
        for (int i = 0; i < img.size(); i++) {
            JsonObject j = img.getJsonObject(i);
            urlList.add(j.getString("url"));
        }
        for (int i = 0; i < urlList.size(); i++) {
            tops.setUrl(urlList.get(i));
        }
        tops.setSpotifyUrl(jo.getJsonObject("external_urls").getString("spotify"));

        return tops;
    }
}
