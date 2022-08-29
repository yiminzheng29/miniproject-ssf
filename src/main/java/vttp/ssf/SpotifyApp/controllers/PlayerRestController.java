package vttp.ssf.SpotifyApp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.ssf.SpotifyApp.services.PlayerService;

@RestController
@RequestMapping(path="/playerinfo", produces=MediaType.APPLICATION_JSON_VALUE)
public class PlayerRestController {
    
    @Autowired
    private PlayerService playerSvc;

    @GetMapping
    public ResponseEntity<String> getPlayerInfo() {

        Optional<JsonObject> getPlayer = playerSvc.getPlayerInfo();
        if (getPlayer.isEmpty()) {
            JsonObject errMsg = Json.createObjectBuilder().add("error", "Cannot find player info").build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errMsg.toString());
        }
        JsonObject jo = getPlayer.get();
        return ResponseEntity.ok(jo.toString());
    }
    
}
