package vttp.ssf.SpotifyApp.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.SpotifyApp.models.Artist2;
import vttp.ssf.SpotifyApp.models.Username;

@Repository
public class UserRepository {
    
    @Autowired @Qualifier ("redislab")
    private RedisTemplate<String, String> redisTemplate;

    // public Optional<Username> get(String name) {
    //     if (!redisTemplate.hasKey(name)) {
    //         return Optional.empty();
    //     }
    //     List<Artist2> contents = new LinkedList<>();
    //     System.out.println(contents.toString());
    //     ListOperations<String, String> listOps = redisTemplate.opsForList();
    //     long size = listOps.size(name);
    //     for (long i=0; i<size; i++) {
    //         String str = listOps.index(name, i);
    //         contents.add(Artist2.create(str));
    //     }
    //     Username user = new Username(name);
    //     System.out.println(user.getName());

    //     return Optional.of(user);
    // }

    public Optional<List<String>> get(String name) {
            if (!redisTemplate.hasKey(name)) {
            return Optional.empty();
        }
      ListOperations<String, String> listOps = redisTemplate.opsForList();
      long l = listOps.size(name);
      List<String> contents = listOps.range(name, 0, l);
      return Optional.of(contents);
    }
    

    public void save(String name, List<Artist2> contents) {
      ListOperations<String, String> listOps = redisTemplate.opsForList();
      listOps.leftPushAll(name, 
      contents.stream()
        .map(v -> v.toJson().toString()).toList());
      Username user = new Username(name);
      user.setContents(contents);
  }

    public void save2(Username user) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        List<Artist2> contents = user.getContents();
        listOps.leftPushAll(user.getName(), 
				contents.stream()
					.map(v -> v.toJson().toString()).toList());
    }


}
