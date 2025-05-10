package devops.master.controller;

import devops.master.message.request.AddWordRequest;
import devops.master.redis.RedisClient;
import devops.master.service.WordCheckerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OccurenceController {

    private final RedisClient redisClient;
    private final WordCheckerService wordCheckerService;

    @PostMapping("/add")
    public ResponseEntity<String> addWord(@RequestBody AddWordRequest addWordRequest) {
        log.info("Add Word request for word: {}", addWordRequest.getWord());
        if (wordCheckerService.isValidWord(addWordRequest.getWord())) {
            redisClient.addWord(addWordRequest.getWord());
            return ResponseEntity.ok("Successfully added word: " + addWordRequest.getWord());
        }
        return ResponseEntity.badRequest().body("Invalid word: " + addWordRequest.getWord());
    }

    @GetMapping("/get/{word}")
    public ResponseEntity<Integer> getOccurence(@PathVariable(name = "word") String word) {
        log.info("Get Occurance request for word: {}", word);
        if (wordCheckerService.isValidWord(word)) {
            return ResponseEntity.ok(redisClient.getOccurence(word));
        }
        return ResponseEntity.badRequest().body(-1);
    }
}
