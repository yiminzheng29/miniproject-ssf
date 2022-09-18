package vttp.miniproject.SpotifyApp.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("empty", savedList.isEmpty());

        return "songlist";
    }

    @PostMapping
    public String deleteSavedSongs(@RequestBody MultiValueMap<String, String> form, Model model) {
        String username = form.getFirst("username");
        List<Songs> savedList = accountSvc.getSongs(username);
        String IDtoDelete = form.getFirst("delete");
        List<Songs> newList = new LinkedList<>();

        for (Songs song:savedList) {
            if (!song.getId().toString().equals(IDtoDelete)) {
                newList.add(song);
            }
        }
        accountSvc.saveAcct3(username, newList);

        model.addAttribute("savedlist", newList);
        model.addAttribute("username", username);
        model.addAttribute("delete", IDtoDelete);
        model.addAttribute("empty", newList.isEmpty());

        return "songlist";
    }
}
