package Calculations;

import java.util.Map;

public class CosineSimilarity {
    public static double calculateCosineSimilarity(Map<String, Double> queryVector, Map<String, Double> documentVector) {
        // Calculate the cosine similarity between two documents represented as term frequency vectors
        double dotProduct = 0.0;
        double euclideanNormA = 0.0;
        double euclideanNormB = 0.0;
        // Iterate through the common terms in queryVector and documentVector to calculate the dot product
        for (String term : queryVector.keySet()) {
            if (documentVector.containsKey(term)) {
                dotProduct += queryVector.get(term) * documentVector.get(term);
            }
        }
        // Calculate the Euclidean norms of queryVector
        for (double value : queryVector.values()) {
            euclideanNormA += Math.pow(value, 2);
        }
        euclideanNormA = Math.sqrt(euclideanNormA);
        // Calculate the Euclidean norms of documentVector
        for (double value : documentVector.values()) {
            euclideanNormB += Math.pow(value, 2);
        }
        euclideanNormB = Math.sqrt(euclideanNormB);
        // If either vector is empty, return 0.0
        if (euclideanNormA == 0 || euclideanNormB == 0) {
            return 0.0;
        }
        // Calculate the cosine similarity
        return dotProduct / (euclideanNormA * euclideanNormB);
    }
}
