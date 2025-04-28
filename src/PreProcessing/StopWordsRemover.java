package PreProcessing;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class StopWordsRemover {
    // Initialize the stop words set using Lucene's EnglishAnalyzer
    private static final CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();
    public List<String> removeStopWords(List<String> tokens) {
        // Create a new list to store the filtered tokens
        List<String> filteredTokens = new ArrayList<>();

        // Iterate through the tokens and add non-stop words to the filtered list
        for (String token : tokens) {
            if (!stopWords.contains(token)) {
                filteredTokens.add(token);
            }
        }

        return filteredTokens;
    }
}
