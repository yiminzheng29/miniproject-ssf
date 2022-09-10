package vttp.miniproject.SpotifyApp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import vttp.miniproject.SpotifyApp.models.Songs;

@Repository
public class AccountsRepository {
    
    @Autowired @Qualifier ("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public void save(String username, String password) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set("!"+username, password);
    }

    public void save2(String username, List<Songs> songlist) {
        String user = username;
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        long l = listOps.size(user);
        System.out.println(l);
        if (l>0) {
            listOps.trim(user, 0, l);
        }
        listOps.leftPushAll(user, songlist.stream().map(v->v.toJson().toString()).toList());
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
}
