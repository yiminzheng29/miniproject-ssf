package vttp.miniproject.SpotifyApp.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.miniproject.SpotifyApp.models.Songs;
import vttp.miniproject.SpotifyApp.services.AccountsService;

@Controller
@RequestMapping("/songlist")
public class SonglistController {

    @Autowired
    private AccountsService accountSvc;
    
    @GetMapping(path="{username}")
    public String getSavedSongs(@PathVariable String username, Model model) {
        List<Songs> savedList = accountSvc.getSongs(username);
        model.addAttribute("username", username);
        model.addAttribute("savedlist", savedList);

        return "songlist";
    }
}
