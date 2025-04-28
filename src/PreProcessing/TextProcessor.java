package PreProcessing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextProcessor {
    public static Map<String, List<String>> processText(Map<String, String> documentContent, boolean normalize, boolean stem, boolean removeStopWords) {
        // Initialize the components
        Tokenizer tokenizer = new Tokenizer();
        Normalizer normalizer = new Normalizer();
        Stemmer stemmer = new Stemmer();
        StopWordsRemover stopWordsRemover = new StopWordsRemover();
        // Create a map to store processed content
        Map<String, List<String>> processedContent = new HashMap<>();
        // Process each document
        for (Map.Entry<String, String> entry : documentContent.entrySet()) {
            String docID = entry.getKey();
            String content = entry.getValue();
            // Tokenize the content
            List<String> tokens = tokenizer.tokenize(content);
            // Apply normalization, stemming, and stop word removal based on flags
            if (normalize) {
                tokens = normalizer.normalize(tokens);
            }
            if (stem) {
                tokens = stemmer.stemTokens(tokens);
            }
            if (removeStopWords) {
                tokens = stopWordsRemover.removeStopWords(tokens);
            }
            // Store the processed tokens in the map
            processedContent.put(docID, tokens);
        }
        return processedContent;
    }
}
