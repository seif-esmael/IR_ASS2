import PreProcessing.*;

import java.util.List;
import java.util.Map;

public class UserQuery {
    // The original query string
    private final String query;

    public UserQuery(String query) {
        this.query = query;
    }
    public Map<String, List<String>> getTokens(boolean normalize, boolean stem, boolean removeStopWords) {
        // Process the query string to extract tokens
        return TextProcessor.processText(Map.of("query", query), normalize, stem, removeStopWords);
    }
}
