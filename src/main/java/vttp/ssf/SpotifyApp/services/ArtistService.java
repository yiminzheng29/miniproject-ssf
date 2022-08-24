package vttp.ssf.SpotifyApp.services;

import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.SpotifyApp.models.Artist;
import vttp.ssf.SpotifyApp.repositories.ArtistRepository;

@Service
public class ArtistService {

    private final static String URL = "https://api.spotify.com/v1/search";
    
    @Autowired
    private ArtistRepository artistRepo;

    public List<Artist> loadArtist(String name, String token) {
        Optional<String> opt = artistRepo.getArtist(name);
        String payload;

        // Check if redisDatabase contains artist
        // If redisDatabase does not contain artist, retrieve info from Spotify API
        if (opt.isEmpty()) {
            System.out.printf("Retrieving %s's information from Spotify\n", name);
            
            try {
                String url = UriComponentsBuilder.fromUriString(URL)
                    .queryParam("q", URLEncoder.encode(name, "UTF-8"))
                    .queryParam("type", "artist")
                    .queryParam("access_token", token)
                    .toUriString();

                // Create GET request, GET URL
                RequestEntity<Void> req = RequestEntity.get(url).build();
                
                // Make the call to SpotifyApp
                RestTemplate temp = new RestTemplate();
                ResponseEntity<String> resp = temp.exchange(req, String.class);

                payload = resp.getBody();
                System.out.println("Payload: "+ payload);
                artistRepo.save(name, payload);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return Collections.emptyList();
            }
        } else {
            // Retrieve artist information from redis Database
            System.out.printf("Retrieving %s's information from redis Database\n", name);
            payload = opt.get();
            System.out.println("Cache: " + payload);
        }
        

        JsonReader jr = Json.createReader(new StringReader(payload));
        JsonObject jo = jr.readObject();
        // JsonObject newJo = Json.createObjectBuilder()
        //     .add("name", (jo.getJsonObject("artists")))
        //     // .add("popularity", (jo.getJsonObject("artists")))
        //     // .add("genres", (jo.getJsonObject("artists")))
        //     // .add("spotify", (jo.getJsonObject("artists").getJsonArray("items").getJsonObject(0)))
        //     // .add("url", (jo.getJsonObject("artists").getJsonArray("images").getJsonObject(0)))
        //     .build();
        List<Artist> artistList = new LinkedList<>();
        JsonArray jArray = jo.getJsonObject("artists").getJsonArray("items");
        for (int i = 0; i < jArray.size(); i++) {
            JsonObject jOb = jArray.getJsonObject(i);
            artistList.add(Artist.create(jOb));
        }
        return artistList;
        
    } 

}
