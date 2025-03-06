package devops.master.service.integration;

import com.github.dockerjava.api.command.CreateContainerCmd;
import devops.master.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.images.builder.dockerfile.statement.Statement;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest
@Testcontainers
@Slf4j
public class RedisIT {

    private static final Network EXISTING_NETWORK = new Network() {
        @Override
        public String getId() {
            return "cicd_default";
        }

        @Override
        public void close() {

        }

        @Override
        public org.junit.runners.model.Statement apply(org.junit.runners.model.Statement statement, Description description) {
            return null;
        }
    };

    @Container
    public static GenericContainer<?> redisContainer =
            new GenericContainer<>(DockerImageName.parse("redis:7.0-alpine"))
                    .withNetwork(EXISTING_NETWORK)
                    .withExposedPorts(6379)
                    .withCreateContainerCmdModifier(cmd ->
                            cmd.withName("redis-test"));

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    private RedisClient redisClient;

    @BeforeEach
    void setUp() {

        log.info("Setting up Redis Client");

        redisTemplate.getConnectionFactory().getConnection().flushAll();
        redisClient = new RedisClient(redisTemplate);
    }

    @AfterEach
    void tearDown() {

        log.info("Tearing down Redis Client");
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Test
    void testAddWordAndGetOccurrence() {
        redisClient.addWord("hello");
        redisClient.addWord("hello");

        Assertions.assertThat(2 == redisClient.getOccurence("hello")).isTrue();
    }

    @Test
    void testGetOccurrenceForNonExistentWord() {
        Assertions.assertThat(0 == redisClient.getOccurence("nonexistent")).isTrue();
    }
}