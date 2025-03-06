package devops.master.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisClient {

    private final RedisTemplate<String, Integer> redisTemplate;

    public void addWord(String word) {
        Integer occurence = redisTemplate.opsForValue().get(word);

        if (occurence == null) {
            redisTemplate.opsForValue().set(word, 1);
        } else {
            redisTemplate.opsForValue().set(word, occurence + 1);
        }
    }

    public Integer getOccurence(String key) {
        Integer occurence = redisTemplate.opsForValue().get(key);
        if (occurence == null) {
            return 0;
        }
        return occurence;
    }
}
