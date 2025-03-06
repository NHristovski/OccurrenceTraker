package devops.master.service;

import org.springframework.stereotype.Service;

@Service
public class WordCheckerServiceImpl implements WordCheckerService {
    @Override
    public boolean isValidWord(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }
        return word.matches("[a-zA-Z]+");
    }
}
