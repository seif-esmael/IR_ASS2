package PreProcessing;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Tokenizer {
    private final String regex = "([a-zA-Z]\\.?){2,}";
    public List<String> tokenize(String text) {
        // Using regex to find all tokens in the text
        Pattern pattern = Pattern.compile(regex);

        List<String> tokens = List.of(pattern.matcher(text).results()
                .map(MatchResult::group)
                .toArray(String[]::new));

        return tokens;
    }

}
