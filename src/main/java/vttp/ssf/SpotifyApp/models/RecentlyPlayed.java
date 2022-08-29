package vttp.ssf.SpotifyApp.models;

import java.io.Reader;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class RecentlyPlayed {
    
    // private String artistName;
    private String spotify;
    private String url;
    private String songName;
    private String previewUrl;
    
    // public String getArtistName() {return artistName;}
    // public void setArtistName(String artistName) {this.artistName = artistName;}

    public String getSpotify() {return spotify;}
    public void setSpotify(String spotify) {this.spotify = spotify;}
    
    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
    
    public String getSongName() {return songName;}
    public void setSongName(String songName) {this.songName = songName;}
    
    public String getPreviewUrl() {return previewUrl;}
    public void setPreviewUrl(String previewUrl) {this.previewUrl = previewUrl;}

    // public static RecentlyPlayed create(String s) {
    //     Reader r = new StringReader(s);
    //     JsonReader jr = Json.createReader(r);
    //     return create(jr.readObject());
    // }

    // public static RecentlyPlayed create(JsonObject jo) {
    //     RecentlyPlayed playlist = new RecentlyPlayed();
    //     playlist.setSongName(jo.getString("name"));
    //     playlist.setPreviewUrl(previewUrl);
    // }
}
