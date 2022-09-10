package vttp.miniproject.SpotifyApp.services;

import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp.miniproject.SpotifyApp.models.Accounts;
import vttp.miniproject.SpotifyApp.models.Songs;
import vttp.miniproject.SpotifyApp.repositories.AccountsRepository;

@Service
public class AccountsService {
    
    @Autowired
    private AccountsRepository accountsRepo;

    // public List<Accounts> getAcc(String username, String password) {
    //     Optional<String> opt = accountsRepo.getAccount(username, password);
    //     String payload;
    //     List<Accounts> list = new LinkedList<>();

    //     if (opt.isEmpty()) {

    //         return null;
    //     } else {
    //         System.out.println("Retrieving user info from database");
    //         payload = opt.get();
    //     }
    //     JsonReader jr = Json.createReader(new StringReader(payload));
    //     JsonObject jo = jr.readObject();
    //     JsonArray jArray = jo.getJsonArray("Accounts");
    //     for (int i = 0; i < jArray.size(); i++) {
    //         JsonObject jOb = jArray.getJsonObject(i);
    //         String pw = jOb.getString("password");
    //         System.out.println(pw);
    //         if (!(pw.equals(password))) {
    //             return null;
    //         }
    //         list.add(Accounts.create(jOb));
    //     }
    //     return list;
    // }

    public String verifyAcc(String username, String password) {
        Optional<String> pw = accountsRepo.getAccount(username, password);
        String correctPw = pw.get();
        System.out.println(correctPw);

        return correctPw;
    }

    // public void saveAcct(String username, String password) {
    //     JsonObjectBuilder acctBuilder = Json.createObjectBuilder();
    //     JsonObjectBuilder unBuilder = Json.createObjectBuilder();
    //     unBuilder.add("username", username);
    //     unBuilder.add("password", password);
    //     JsonArrayBuilder arBuilder = Json.createArrayBuilder();
    //     arBuilder.add(unBuilder);
    //     acctBuilder.add("Accounts", arBuilder);

    //     JsonObject acct = acctBuilder.build();
    //     JsonArray jArray = acct.getJsonArray("Accounts");
    //     for (int i = 0; i < jArray.size(); i++) {
    //         JsonObject jOb = jArray.getJsonObject(i);
    //         System.out.println(jOb.toString());
    //         accountsRepo.save(Accounts.create(jOb));
    //     }
    // }

    public void saveAcct(String username, String password) {
        accountsRepo.save(username, password);
    }

    // public void saveAcct2(String username, String password, List<Songs> songlist) {
    //     JsonObjectBuilder acctBuilder = Json.createObjectBuilder();
    //     JsonObjectBuilder unBuilder = Json.createObjectBuilder();
    //     unBuilder.add("username", username);
    //     unBuilder.add("password", password);
    //     JsonArrayBuilder arBuilder = Json.createArrayBuilder();
    //     JsonArrayBuilder songBuilder = Json.createArrayBuilder();
    //     for (int i = 0; i < songlist.size(); i++) {
    //         JsonObject jo = songlist.get(i).toJson();
    //         songBuilder.add(jo.toString());
    //         // System.out.println(jo.toString());
    //     }
    //     unBuilder.add("contents", songBuilder);

    //     arBuilder.add(unBuilder);
    //     acctBuilder.add("Accounts", arBuilder);

    //     JsonObject acct = acctBuilder.build();
    //     JsonArray jArray = acct.getJsonArray("Accounts");
    //     for (int i = 0; i < jArray.size(); i++) {
    //         JsonObject jOb = jArray.getJsonObject(i);
    //         System.out.println(jOb.toString());
    //         accountsRepo.save2(Accounts.create(jOb));
    //     }
    // }

    public void saveAcct2(String username, List<Songs> songlist) {
        // List<Songs> songs = getSongs(username);
        List<Songs> saved = new LinkedList<>();
        
        // if (songs.isEmpty()) {
        //     accountsRepo.save2(username, songlist);
        // }
    
        // for (Songs s:songs) {
        //     for (Songs ss: songlist) {
        //         if (!(s.getId()==(ss.getId()))) {
        //             saved.add(ss);
        //             System.out.println(ss.getId());
        //         }
        //     }
        // }
        accountsRepo.save2(username, songlist);
    }

    public List<Songs> getSongs(String name) {
        Optional<List<String>> user = accountsRepo.get(name);
        List<String> list = user.get();
        // System.out.println(list);
        List<Songs> songlist = new LinkedList<>();

        for (int i = 0; i < list.size(); i++) {
            JsonReader jr = Json.createReader(new StringReader(list.get(i)));
            JsonObject jo = jr.readObject();
            songlist.add(Songs.create2(jo));
        }
        return songlist;
    }
}
