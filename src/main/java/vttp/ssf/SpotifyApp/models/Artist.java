package vttp.ssf.SpotifyApp.models;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Artist {
    
    private String url;
    private String name;
    // private String genre;
    private String imageurl;
    // private String popularity;

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    // public String getGenre() {return genre;}
    // public void setGenre(String genre) {this.genre = genre;}

    public String getImageurl() {return imageurl;}
    public void setImageurl(String imageurl) {this.imageurl = imageurl;}
    
    // public String getPopularity() {return popularity;}
    // public void setPopularity(String popularity) {this.popularity = popularity;}

    public static Artist create(String str) {
        Reader r = new StringReader(str);
        JsonReader jr = Json.createReader(r);
        return create(jr.readObject());
    }

    public static Artist create(JsonObject jo) {
        Artist artist = new Artist();
        artist.setUrl(jo.getJsonObject("external_urls").getString("spotify"));
        // artist.setGenre(jo.getString("genres"));
        List<String> urlList = new LinkedList<>();
        JsonArray img = jo.getJsonArray("images");
        for (int i = 0; i < img.size(); i++) {
            JsonObject j = img.getJsonObject(i);
            urlList.add(j.getString("url"));
        }
        for (int i = 0; i < urlList.size(); i++) {
            artist.setImageurl(urlList.get(i));
        }
        artist.setName(jo.getString("name"));
        // artist.setPopularity(jo.getString("popularity"));
        return artist;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("name", name)
            // .add("popularity", popularity)
            // .add("genres", genre)
            .add("spotify", url)
            .add("url", imageurl)
            .build();    
        }
    
}
