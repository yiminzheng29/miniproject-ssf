package vttp.miniproject.SpotifyApp.services;

import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.miniproject.SpotifyApp.models.Songs;
import vttp.miniproject.SpotifyApp.repositories.SongsRepository;

@Service
public class SongsService {
    
    private static final String URL = "https://api.deezer.com/search";

    @Autowired
    private SongsRepository songsRepo;

    public List<Songs> getSongsList(String query) {
        Optional<String> opt = songsRepo.getSongs(query);
        String payload;

        // Check if redisDatabase contains artist
        // If redisDatabase does not contain artist, retrieve info from Spotify API
        if (opt.isEmpty()) {
            System.out.printf("Retrieving %s's information from Deezer\n", query);
            
            try {
                String url = UriComponentsBuilder.fromUriString(URL)
                    .queryParam("q", URLEncoder.encode(query, "UTF-8"))
                    .toUriString();

                // Create GET request, GET URL
                RequestEntity<Void> req = RequestEntity.get(url).build();
                
                // Make the call to SpotifyApp
                RestTemplate temp = new RestTemplate();
                ResponseEntity<String> resp = temp.exchange(req, String.class);

                payload = resp.getBody();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return Collections.emptyList();
            }
        } else {
            // Retrieve artist information from redis Database
            System.out.printf("Retrieving %s's information from redis Database\n", query);
            payload = opt.get();
            System.out.println("Cache: " + payload);
        }
        
        JsonReader jr = Json.createReader(new StringReader(payload));
        JsonObject jo = jr.readObject();
        List<Songs> songsList = new LinkedList<>();
        JsonArray jArray = jo.getJsonArray("data");
        for (int i = 0; i < jArray.size(); i++) {
            JsonObject jOb = jArray.getJsonObject(i);
            songsList.add(Songs.create(jOb));
        }
        return songsList;
    }

    public void saveSongs(List<Songs> songlist) {
        for (int i = 0; i < songlist.size(); i++) {
            songsRepo.save(songlist.get(i));
        }
    }
}