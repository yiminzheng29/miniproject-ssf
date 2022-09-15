package vttp.miniproject.SpotifyApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import vttp.miniproject.SpotifyApp.models.Songs;
import vttp.miniproject.SpotifyApp.repositories.AccountsRepository;

@Service
public class AccountsServices {
    
    @Autowired
    private AccountsRepository accountsRepo;

    public String verifyAcc(String username, String password) {
        Optional<String> pw = accountsRepo.getAccount(username, password);
        String correctPw = pw.get();
        // System.out.println(correctPw);

        return correctPw;
    }

    public void saveAcct(String username, String password) {
        accountsRepo.save(username, password);
    }


    // public void saveAcct2(String username, List<Songs> songlist) {
    //     accountsRepo.save2(username, songlist);
    // }

    public void saveAcct2(String username, List<Songs> songlist) {
        accountsRepo.save2(username, songlist);
    }

    // public List<Songs> getSongs(String name) {
    //     Optional<List<String>> user = accountsRepo.get(name);
    //     List<String> list = user.get();
    //     List<Songs> songlist = new LinkedList<>();

    //     for (int i = 0; i < list.size(); i++) {
    //         JsonReader jr = Json.createReader(new StringReader(list.get(i)));
    //         JsonObject jo = jr.readObject();
    //         songlist.add(Songs.create2(jo));
    //     }
    //     return songlist;
    // }

    public List<Songs> getSongs(String name) {
        return accountsRepo.getSonglist(name);
    }
        
}

