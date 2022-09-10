package vttp.miniproject.SpotifyApp.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import vttp.miniproject.SpotifyApp.models.Songs;

@Repository
public class SongsRepository {
    
    @Autowired @Qualifier ("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public void save(Songs song) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set(song.getId().toString(), song.toJson().toString());
    }

    public Optional<String> getSongs(String name) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(name)) {
            return Optional.of(valueOps.get(name));
        }
        return Optional.empty();
    }
}
