package vttp.miniproject.SpotifyApp.repositories;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.miniproject.SpotifyApp.models.Songs;

@Repository
public class AccountsRepository {
    
    @Autowired @Qualifier ("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public void save(String username, String password) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set("!"+username, password);
    }

    // public void save2(String username, List<Songs> songlist) {
    //     String user = username;
    //     ListOperations<String, String> listOps = redisTemplate.opsForList();
    //     long l = listOps.size(user);
    //     System.out.println(l);
    //     if (l>0) {
    //         listOps.trim(user, 0, l);
    //     }
    //     listOps.leftPushAll(user, songlist.stream().map(v->v.toJson().toString()).toList());
    // }

    // public void save2(String username, List<Songs> songlist) {
    //     String user = username;
    //     ListOperations<String, String> listOps = redisTemplate.opsForList();
    //     long l = listOps.size(user);

        
    //     if (l>0) {
    //         List<Songs> savedList = getSonglist(username);
    //         // List<Songs> tobesavedList = new LinkedList<>();
    //         List<String> songs = new LinkedList<>();
    //         for (Songs s:savedList) {
    //             System.out.println("Savedlist"+s.getId());
    //             songs.add(s.getId().toString());
    //         }

    //         for (Songs song:songlist) {
    //             if (!(songs.contains(song.getId().toString()))) {
    //                 System.out.println("Songlist"+song.getId());
    //                 savedList.add(song);
    //             }
    //         }
    //         listOps.rightPop(username);

    //         for (Songs ss: savedList) {
    //             listOps.rightPush(username, ss.toJson().toString());
    //         }
    //     }
    //     else {
    //         for (Songs ss: songlist) {
    //             listOps.rightPush(username, ss.toJson().toString());
    //         }
    //     }
    // }

    public void save2(String username, List<Songs> songlist) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        long l = listOps.size(username);

        
        if (l>0) {
            List<Songs> savedList = new LinkedList<>();
            // List<Songs> tobesavedList = new LinkedList<>();
            for (int i = 0; i < listOps.size(username); i++) {
                JsonReader jr = Json.createReader(new StringReader(listOps.index(username, i)));
                JsonObject jo = jr.readObject();
                savedList.add(Songs.create2(jo));
            }
            List<String> songs = new LinkedList<>();
            for (Songs s:savedList) {
                songs.add(s.getId().toString());
            }

            for (Songs song:songlist) {
                if (!(songs.contains(song.getId().toString()))) {
                    savedList.add(song);
                }
            }
            for (Long i = listOps.size(username); i > 0; i--) {
                listOps.rightPop(username);
            }

            for (Songs ss: savedList) {
                listOps.rightPush(username, ss.toJson().toString());
            }
        }
        else {
            for (Songs ss: songlist) {
                listOps.rightPush(username, ss.toJson().toString());
            }
        }
    }

    public void save3(String username, List<Songs> songlist) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        long l = listOps.size(username);

            // List<Songs> savedList = new LinkedList<>();
            // // List<Songs> tobesavedList = new LinkedList<>();
            // for (int i = 0; i < listOps.size(username); i++) {
            //     JsonReader jr = Json.createReader(new StringReader(listOps.index(username, i)));
            //     JsonObject jo = jr.readObject();
            //     savedList.add(Songs.create2(jo));
            // }

            for (Long i = listOps.size(username); i > 0; i--) {
                listOps.rightPop(username);
            }

            for (Songs ss: songlist) {
                listOps.rightPush(username, ss.toJson().toString());
            }
        }
    

    public Optional<String> getAccount(String username, String password) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        String pw = valueOps.get("!"+username);
        if ((redisTemplate.hasKey("!"+username)) && (pw.equals(password))) {
            return Optional.of(valueOps.get("!"+username));
        }
        return Optional.empty();
    }

    public Optional<List<String>> get(String name) {
        if (!redisTemplate.hasKey(name)) {
            return Optional.empty();
        }
        
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        long l = listOps.size(name);
        List<String> songlist = listOps.range(name, 0, l);
        return Optional.of(songlist);
    }

    public List<Songs> getSonglist(String username) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        List<Songs> songlist = new LinkedList<>();
        for (int i = 0; i < listOps.size(username); i++) {
            JsonReader jr = Json.createReader(new StringReader(listOps.index(username, i)));
            JsonObject jo = jr.readObject();
            songlist.add(Songs.create2(jo));
        }
        return songlist;
    }
}
