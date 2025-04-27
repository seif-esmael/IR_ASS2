package Calculations;

import java.util.Map;

public class CosineSimilarity {
    public static double calculateCosineSimilarity(Map<String, Double> vectorA, Map<String, Double> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (String term : vectorA.keySet()) {
            if (vectorB.containsKey(term)) {
                dotProduct += vectorA.get(term) * vectorB.get(term);
            }
        }

        for (double value : vectorA.values()) {
            normA += Math.pow(value, 2);
        }

        for (double value : vectorB.values()) {
            normB += Math.pow(value, 2);
        }

        if (normA == 0 || normB == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
