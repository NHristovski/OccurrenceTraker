package devops.master.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@org.springframework.context.annotation.Configuration
@Slf4j
public class Configuration {

    @Value("${spring.data.redis.host}")
    private String redisHostname;

    @Value("${spring.data.redis.port}")
    private Integer redisPort;

    @Value("${spring.data.redis.usePassword}")
    private boolean usePassword;

    @Value("${spring.data.redis.password}")
    private String password;



    @Bean
    JedisConnectionFactory jedisConnectionFactory() {

        log.info("Redis hostname is {}", redisHostname);
        log.info("Redis port is {}", redisPort);

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHostname);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setDatabase(0);

        if (usePassword) {
            log.info("Connecting to Redis with password {}", password);
            redisStandaloneConfiguration.setPassword(password);
        } else {
            log.info("Connecting to Redis WITHOUT password");
        }

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(60));

        return new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
    }


    @Bean
    RedisTemplate<String, Integer> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Integer> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
