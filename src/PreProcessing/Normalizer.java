package PreProcessing;

import java.util.ArrayList;
import java.util.List;

public class Normalizer {
    public List<String> normalize(List<String> tokens) {
        // Create an array to hold the normalized tokens
        List<String> normalizedTokens = new ArrayList<>(tokens.size());

        // Normalize each token
        for (String token : tokens) {
            // Convert to lowercase and remove non-alphabetic characters
            String normalizedToken = token.toLowerCase();
            normalizedToken = normalizedToken.replaceAll("[^a-z]", "");

            if (!normalizedToken.isEmpty()) {
                normalizedTokens.add(normalizedToken);
            }
        }

        return normalizedTokens;
    }
}
