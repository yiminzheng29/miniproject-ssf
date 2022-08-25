package vttp.ssf.SpotifyApp.models;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Categories {
    
    private String id;
    private String name;
    private String url;

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    public static Categories create(String s) {
        Reader r = new StringReader(s);
        JsonReader jr = Json.createReader(r);
        return create(jr.readObject());
    }

    public static Categories create(JsonObject jo) {
        Categories cats = new Categories();
        cats.setId(jo.getString("id"));
        cats.setName(jo.getString("name"));
        List<String> catList = new LinkedList<>();
        JsonArray jArray = jo.getJsonArray("icons");
        for (int i = 0; i < jArray.size(); i++) {
            JsonObject j = jArray.getJsonObject(i);
            catList.add(j.getString("url"));
        }
        for (int i = 0; i < catList.size(); i++) {
            cats.setUrl(catList.get(i));
        }
        return cats;
    }
}
