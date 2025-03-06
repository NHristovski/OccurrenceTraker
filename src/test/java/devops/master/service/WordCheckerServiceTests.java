package devops.master.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WordCheckerServiceTests {

    private final WordCheckerService wordCheckerService;

    WordCheckerServiceTests() {
        this.wordCheckerService = new WordCheckerServiceImpl();
    }

    @Test
    void whenValidWordReturnTrue() {
        Assertions.assertThat(wordCheckerService.isValidWord("b")).isTrue();
    }

    @Test
    void whenInvalidWordReturnFalse() {
        Assertions.assertThat(wordCheckerService.isValidWord("b1")).isFalse();
    }

    @Test
    void whenNullReturnFalse() {
        Assertions.assertThat(wordCheckerService.isValidWord(null)).isFalse();
    }

    @Test
    void whenEmptyStringReturnFalse() {
        Assertions.assertThat(wordCheckerService.isValidWord("")).isFalse();
    }

    @Test
    void whenSentenceReturnFalse() {
        Assertions.assertThat(wordCheckerService.isValidWord("two words")).isFalse();
    }
}

