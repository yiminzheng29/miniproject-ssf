package vttp.miniproject.SpotifyApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.miniproject.SpotifyApp.services.AccountsService;

@Controller
@RequestMapping("/signup")
public class NewAccountController {

    @Autowired
    private AccountsService accountSvc;

    @GetMapping
    public String get(Model model) {
        return "signup";
    }
     
    @PostMapping
    public String newAcc(@RequestBody MultiValueMap<String, String> form, Model model) {
        String username = form.getFirst("username");
        String password = form.getFirst("password");
        System.out.println(username);
        System.out.println(password);
        accountSvc.saveAcct(username, password);
        model.addAttribute("username", username);
        model.addAttribute("password", password);

        return "/";
    }
    
}
