package vttp.miniproject.SpotifyApp.models;

import java.io.Reader;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Songs {
    private Long id;
    private String title;
    private String artistName;
    private String link;
    private Integer duration;
    private String preview;
    private String picture_medium;
    
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getArtistName() {return artistName;}
    public void setArtistName(String artistName) {this.artistName = artistName;}
    
    public String getLink() {return link;}
    public void setLink(String link) {this.link = link;}
    
    public Integer getDuration() {return duration;}
    public void setDuration(Integer duration) {this.duration = duration;}
    
    public String getPreview() {return preview;}
    public void setPreview(String preview) {this.preview = preview;}
    
    public String getPicture_medium() {return picture_medium;}
    public void setPicture_medium(String picture_medium) {this.picture_medium = picture_medium;}

    public static Songs create(String s) {
        Reader r = new StringReader(s);
        JsonReader jr = Json.createReader(r);
        return create(jr.readObject());
    }
    
    public static Songs create(JsonObject jo) {
        Songs song = new Songs();
        song.setId(jo.getJsonNumber("id").longValue());
        song.setDuration(jo.getInt("duration"));
        song.setLink(jo.getString("link"));
        song.setPreview(jo.getString("preview"));
        song.setTitle(jo.getString("title"));
        song.setPicture_medium(jo.getJsonObject("artist").getString("picture_medium"));
        song.setArtistName(jo.getJsonObject("artist").getString("name"));
        return song;
    }

    public static Songs create2(JsonObject jo) {
        Songs song = new Songs();
        song.setId(jo.getJsonNumber("id").longValue());
        song.setArtistName(jo.getString("name"));
        song.setDuration(jo.getInt("duration"));
        song.setLink(jo.getString("link"));
        song.setPreview(jo.getString("preview"));
        song.setTitle(jo.getString("title"));
        song.setPicture_medium(jo.getString("picture_medium"));
        return song;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("name", artistName)
            .add("title", title)
            .add("duration", duration)
            .add("link", link)
            .add("preview", preview)
            .add("picture_medium", picture_medium)
            .build();
    }
    
}
