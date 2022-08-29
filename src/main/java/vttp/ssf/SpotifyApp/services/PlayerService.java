package vttp.ssf.SpotifyApp.services;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class PlayerService {
    
    @Value("${API_TOKEN}")
    private String token;

    private static final String URL = "https://api.spotify.com/v1/me/player";

    public Optional<JsonObject> getPlayerInfo() {
        String payload;
        
        String url = UriComponentsBuilder.fromUriString(URL)
            .queryParam("access_token", token)
            .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate temp = new RestTemplate();
        try {
            ResponseEntity<String> resp = temp.exchange(req, String.class);
            payload = resp.getBody();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        if (payload==null) {
            return Optional.empty();
        }
        JsonReader jr = Json.createReader(new StringReader(payload));
        JsonObject jo = jr.readObject();

        return Optional.of(jo);
    }
}
