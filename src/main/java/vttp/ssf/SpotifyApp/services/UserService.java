package vttp.ssf.SpotifyApp.services;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.SpotifyApp.models.Artist2;
import vttp.ssf.SpotifyApp.models.Username;
import vttp.ssf.SpotifyApp.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;


    public void save(String name, List<Artist2> contents) {
        userRepo.save(name, contents);
    }

    public List<Artist2> getUser(String name) {
        Optional<List<String>> user = userRepo.get(name);
        List<String> list = user.get();
        List<Artist2> contents = new LinkedList<>();

        for (int i = 0; i < list.size(); i++) {
            JsonReader jr = Json.createReader(new StringReader(list.get(i)));
            JsonObject jo = jr.readObject();
            contents.add(Artist2.create2(jo));
        }

        return contents;
        }
    }

