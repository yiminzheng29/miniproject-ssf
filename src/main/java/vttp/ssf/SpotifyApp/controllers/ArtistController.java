package vttp.ssf.SpotifyApp.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.ssf.SpotifyApp.models.Artist;
import vttp.ssf.SpotifyApp.services.ArtistService;

@Controller
@RequestMapping("/artist")
public class ArtistController {
    
    @Autowired
    private ArtistService artistSvc;

    @GetMapping
    public String getArtist(Model model, @RequestParam String name, @RequestParam String token) {

        List<Artist> artist = artistSvc.loadArtist(name, token);
        model.addAttribute("artist", artist);
        model.addAttribute("name", name);
        model.addAttribute("token", token);
        return "artist";
    }
}
