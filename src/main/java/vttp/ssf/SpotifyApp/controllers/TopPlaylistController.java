package vttp.ssf.SpotifyApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.ssf.SpotifyApp.models.TopPlaylist;
import vttp.ssf.SpotifyApp.services.TopPlaylistService;

@Controller
@RequestMapping("/topplaylist")
public class TopPlaylistController {
    
    @Autowired
    private TopPlaylistService topSvc;

    @GetMapping
    public String getTopPlayList(Model model, @RequestParam String token) {

        List<TopPlaylist> tops = topSvc.getTopPlaylist();
        model.addAttribute("playlist", tops);
        model.addAttribute("token", token);
        return "topplaylist";
    }
}
