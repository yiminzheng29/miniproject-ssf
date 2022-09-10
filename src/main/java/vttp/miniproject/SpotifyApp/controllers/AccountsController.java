package vttp.miniproject.SpotifyApp.controllers;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.miniproject.SpotifyApp.services.AccountsService;

@Service
@RequestMapping("/account")
public class AccountsController {
    
    @Autowired
    private AccountsService accountSvc;


    @GetMapping(path="{username}")
    public String getUser(@PathVariable String username, Model model) {

                
        model.addAttribute("currTime", (new Date()).toString());
        model.addAttribute("username", username);

        return "account";
    }

    @PostMapping
    public String postUser(@RequestBody MultiValueMap<String, String> form, Model model) {
        
        String username = form.getFirst("username");
        String password = form.getFirst("password");
        System.out.println(password);

        String correctPw = accountSvc.verifyAcc(username, password);
        if (!password.equals(correctPw)) {
            return null;
        }
        accountSvc.saveAcct(username, password);
        model.addAttribute("currTime", (new Date()).toString());
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        // model.addAttribute("wrongpw", (!password.equals(correctPw)));

        return "account";
    }
}
