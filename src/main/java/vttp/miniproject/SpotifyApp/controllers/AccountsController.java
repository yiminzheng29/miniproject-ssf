package vttp.miniproject.SpotifyApp.controllers;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import vttp.miniproject.SpotifyApp.models.Accounts;
import vttp.miniproject.SpotifyApp.models.Songs;
import vttp.miniproject.SpotifyApp.services.AccountsService;
import vttp.miniproject.SpotifyApp.services.SongsService;

@Service
@RequestMapping("/account")
public class AccountsController {
    
    @Autowired
    private AccountsService accountSvc;

    @Autowired
    private SongsService songsSvc;

    // @GetMapping
    // public String getUser(@RequestParam String username, @RequestParam String password, @RequestParam String query, @RequestBody String[] selected, Model model) {

    //     List<Songs> songlist = songsSvc.getSongsList(query);
    //     List<Songs> newList = new LinkedList<>();
    //     for (Songs song: songlist) {
    //         for (int i = 0; i < selected.length; i++) {
    //             if (song.getId().equals(Long.parseLong(selected[i]))) {
    //                 newList.add(song);
    //             }
    //         }
    //     }

    //     accountSvc.saveAcct2(username, password, newList);
    //     model.addAttribute("username", username);
    //     model.addAttribute("password", password);
    //     model.addAttribute("songs", newList);

    //     return "account";
    // }

    @GetMapping(path="{username}")
    public String getUser(@PathVariable String username, Model model) {

        // List<Songs> songlist = songsSvc.getSongsList(query);
        // List<Songs> newList = new LinkedList<>();
        // for (Songs song: songlist) {
        //     for (int i = 0; i < selected.length; i++) {
        //         if (song.getId().equals(Long.parseLong(selected[i]))) {
        //             newList.add(song);
        //         }
        //     }
        // }
                
        model.addAttribute("currTime", (new Date()).toString());
        model.addAttribute("username", username);
        // model.addAttribute("acct", acct);

        return "account";
    }

    @PostMapping
    public String postUser(@RequestBody MultiValueMap<String, String> form, Model model) {
        
        String username = form.getFirst("username");
        String password = form.getFirst("password");
        System.out.println(username);
        System.out.println(password);
        // String correctPw = accountSvc.verifyAcc(username, password);
        // if (!password.equals(correctPw)) {
        //     accountSvc.saveAcct(username, password);
        // }
        accountSvc.saveAcct(username, password);
        model.addAttribute("currTime", (new Date()).toString());
        model.addAttribute("username", username);
        model.addAttribute("password", password);

        return "account";
    }

    // @PostMapping
    // public String postSongs(@RequestBody MultiValueMap<String, String> form, @RequestBody String[] selected, Model model) {
    //     String username = form.getFirst("username");
    //     String password = form.getFirst("password");
    //     String query = form.getFirst("query");
    //     List<Songs> songlist = songsSvc.getSongsList(query);
    //     List<Songs> newList = new LinkedList<>();
    //     for (Songs song: songlist) {
    //         for (int i = 0; i < selected.length; i++) {
    //             if (song.getId().equals(Long.parseLong(selected[i]))) {
    //                 newList.add(song);
    //             }
    //         }
    //     }
    //     accountSvc.saveAcct2(username, password, songlist);
    //     model.addAttribute("username", username);
    //     model.addAttribute("password", password);
    //     model.addAttribute("songs", newList);

    //     return "songs";

    // }
    
}
