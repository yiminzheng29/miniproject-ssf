package vttp.ssf.SpotifyApp.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import vttp.ssf.SpotifyApp.models.Artist;

@Repository
public class ArtistRepository {
    
    @Autowired @Qualifier ("redislab")
    private RedisTemplate<String, String> redisTemplate;

    public void save(String name, String payload) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        valueOps.set(name, payload);
    }

    public Optional<String> getArtist(String name) {
        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(name)) {
            return Optional.of(valueOps.get(name));
        }
        return Optional.empty();
    }
}
