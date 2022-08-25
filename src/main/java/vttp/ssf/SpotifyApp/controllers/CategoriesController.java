package vttp.ssf.SpotifyApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.SpotifyApp.models.Categories;
import vttp.ssf.SpotifyApp.services.CategoriesService;

@Controller
@RequestMapping("/categories")
public class CategoriesController {
    
    @Autowired
    private CategoriesService catSvc;

    @PostMapping
    public String getCategories(Model model, @RequestBody MultiValueMap<String, String> form) {

        String country = form.getFirst("country");
        String limit = form.getFirst("limit");
        String offset = form.getFirst("offset");

        List<Categories> cats = catSvc.getCategories(country, limit, offset);
        model.addAttribute("country", country);
        model.addAttribute("cats", cats);

        return "categories";
    }
}
