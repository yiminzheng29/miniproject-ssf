package vttp.miniproject.SpotifyApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.miniproject.SpotifyApp.models.Songs;
import vttp.miniproject.SpotifyApp.services.AccountsService;

@RestController
@RequestMapping(path="/usersonglist", produces=MediaType.APPLICATION_JSON_VALUE)
public class SonglistRESTController {
    
    @Autowired
    private AccountsService accountSvc;

    @GetMapping(value="{username}")
    public ResponseEntity<String> getSonglist(@PathVariable String username) {

        List<Songs> savedSongs = accountSvc.getSongs(username);
        String response="";

        if (savedSongs.isEmpty()) {
            JsonObject errMsg = Json.createObjectBuilder().add("error", "Cannot find songlist for %s".formatted(username)).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errMsg.toString());
        } 
        for (Songs song:savedSongs) {
            response += song.toJson().toString();
        }
        return ResponseEntity.ok(response);
    }    
}
