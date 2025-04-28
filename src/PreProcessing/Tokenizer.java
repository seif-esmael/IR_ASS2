package PreProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Tokenizer {
    // Regular expression to match tokens (words) and possible abbreviations
    private static final String regex = "([a-zA-Z]\\.?){2,}";
    public List<String> tokenize(String text) {
        // Initialize an empty list to store tokens
        List<String> tokens = new ArrayList<>();
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Find all matches in the input text
        List<MatchResult> resultList = pattern.matcher(text).results().toList();

        // Convert MatchResult to String and add it to a list
        for (MatchResult matchResult : resultList) {
            tokens.add(matchResult.group());
        }
        return tokens;
    }

}
