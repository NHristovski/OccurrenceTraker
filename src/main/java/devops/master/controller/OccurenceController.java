package devops.master.controller;

import devops.master.message.request.AddWordRequest;
import devops.master.redis.RedisClient;
import devops.master.service.WordCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OccurenceController {

    private final RedisClient redisClient;
    private final WordCheckerService wordCheckerService;

    @PostMapping("/add")
    public ResponseEntity<String> addWord(@RequestBody AddWordRequest addWordRequest) {
        if (wordCheckerService.isValidWord(addWordRequest.getWord())) {
            redisClient.addWord(addWordRequest.getWord());
            return ResponseEntity.ok("Successfully added word: " + addWordRequest.getWord());
        }
        return ResponseEntity.badRequest().body("Invalid word: " + addWordRequest.getWord());
    }

    @GetMapping("/get/{word}")
    public ResponseEntity<Integer> getOccurence(@PathVariable(name = "word") String word) {
        if (wordCheckerService.isValidWord(word)) {
            return ResponseEntity.ok(redisClient.getOccurence(word));
        }
        return ResponseEntity.badRequest().body(-1);
    }
}
