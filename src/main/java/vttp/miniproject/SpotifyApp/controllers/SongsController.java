package vttp.miniproject.SpotifyApp.controllers;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.miniproject.SpotifyApp.models.Songs;
import vttp.miniproject.SpotifyApp.services.AccountsService;
import vttp.miniproject.SpotifyApp.services.SongsService;

@Controller
@RequestMapping("/songs")
public class SongsController {
    
    @Autowired
    private SongsService songsSvc;

    @Autowired
    private AccountsService accountSvc;

    @GetMapping
    public String getSong(Model model, @RequestParam String query, @RequestParam String username, @RequestParam String password) {
        List<Songs> songs = songsSvc.getSongsList(query);

        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("songs", songs);
        model.addAttribute("query", query);
        return "songs";
    }
    

    @PostMapping
    public String saveSongs(@RequestParam(value="selected") String[] selected, @RequestParam String query, @RequestParam String username, @RequestParam String password, Model model) {
        List<Songs> songlist = songsSvc.getSongsList(query);
        List<Songs> newList = new LinkedList<>();
        for (Songs song: songlist) {
            for (int i = 0; i < selected.length; i++) {
                if (song.getId().equals(Long.parseLong(selected[i]))) {
                    newList.add(song);
                }
            }
        }
        accountSvc.saveAcct2(username, newList);
        List<Songs> savedList = accountSvc.getSongs(username);

        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("saved", newList);
        model.addAttribute("query", query);
        model.addAttribute("savedlist", savedList);

        return "songlist";
    }

    
}
