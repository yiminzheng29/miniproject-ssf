package vttp.miniproject.SpotifyApp.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.miniproject.SpotifyApp.models.Songs;
import vttp.miniproject.SpotifyApp.repositories.AccountsRepository;

@Service
public class AccountsService {
    
    @Autowired
    private AccountsRepository accountsRepo;

    public String verifyAcc(String username, String password) {
        Optional<String> pw = accountsRepo.getAccount(username, password);
        String correctPw = pw.get();

        return correctPw;
    }

    public void saveAcct(String username, String password) {
        accountsRepo.save(username, password);
    }

    public void saveAcct2(String username, List<Songs> songlist) {
        accountsRepo.save2(username, songlist);
    }

    public void saveAcct3(String username, List<Songs> songlist) {
        accountsRepo.save3(username, songlist);
    }

    public List<Songs> getSongs(String name) {
        return accountsRepo.getSonglist(name);
    }
        
}

