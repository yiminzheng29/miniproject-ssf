package vttp.ssf.SpotifyApp.services;

import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.SpotifyApp.models.Categories;

@Service
public class CategoriesService {
    
    private static final String URL = "https://api.spotify.com/v1/browse/categories";

    @Value("${API_TOKEN}")
    private String token;

    public List<Categories> getCategories(String country, String limit, String offset) {

        System.out.println("Retrieving categories from Spotify");
        String payload;

        String url = UriComponentsBuilder.fromUriString(URL)
            .queryParam("country", country)
            .queryParam("limit", limit)
            .queryParam("offset", offset)
            .queryParam("access_token", token)
            .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate temp = new RestTemplate();

        try {
            ResponseEntity<String> resp = temp.exchange(req, String.class);
            payload = resp.getBody();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return Collections.emptyList();
        }
        JsonReader jr = Json.createReader(new StringReader(payload));
        JsonObject jo = jr.readObject();
        List<Categories> cats = new LinkedList<>();
        JsonArray jArray = jo.getJsonObject("categories").getJsonArray("items");
        for (int i = 0; i < jArray.size(); i++) {
            JsonObject j = jArray.getJsonObject(i);
            cats.add(Categories.create(j));
        }
        return cats;
    }
}
