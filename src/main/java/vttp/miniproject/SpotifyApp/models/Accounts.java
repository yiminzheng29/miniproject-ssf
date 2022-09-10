package vttp.miniproject.SpotifyApp.models;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Accounts {
    
    private String username;
    private String password;
    private List<Songs> songlist = new LinkedList<>();


    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public List<Songs> getSonglist() {return songlist;}
    public void setSonglist(List<Songs> songlist) {this.songlist = songlist;}

    public void add(Songs song) {
        Long n = song.getId();
        for (Songs s:songlist) {
            if (!s.getId().equals(n)) {
                songlist.add(song);
            }
        }
    }

    public static Accounts create(String s) {
        Reader r = new StringReader(s);
        JsonReader jr = Json.createReader(r);
        return create(jr.readObject());
    }

    //
    public static Accounts create (JsonObject jo) {
        Accounts acct = new Accounts();
        acct.setUsername(jo.getString("username"));
        acct.setPassword(jo.getString("password"));

        return acct;
    }

    public JsonObject toJson() {

        return Json.createObjectBuilder()
            .add("username", username)
            .add("password", password)
            .build();
    }

}
