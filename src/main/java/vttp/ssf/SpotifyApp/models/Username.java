package vttp.ssf.SpotifyApp.models;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Username {
    
    private String name;
    private List<Artist2> contents = new LinkedList<>();

    public Username(String name) {}

    public String getName() {return name;}

    public void add(Artist2 artist) {
        String n = artist.getName().toLowerCase();
        for (Artist2 a : contents) {
            if (!a.getName().toLowerCase().equals(n)) {
                contents.add(artist);
            }
        }
    }

    public List<Artist2> getContents() {return contents;}
    public void setContents(List<Artist2> contents) {this.contents = contents;}

    public JsonObject toJson() {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        contents.stream()
            .map(i -> i.toJson())
            .forEach(i -> arr.add(i));
        return Json.createObjectBuilder()
            .add("name", name)
            .add("contents", arr)
            .build();
    }

    public static Username create(String s) {
        JsonReader jr = Json.createReader(new StringReader(s));
        JsonObject c = jr.readObject();
        Username user = new Username(c.getString("name"));
        List<Artist2> contents = c.getJsonArray("contents")
            .stream()
            .map(v -> (JsonObject)v)
            .map(Artist2::create)
            .toList();
        user.setContents(contents);
        return user;
    }
    
    
}
