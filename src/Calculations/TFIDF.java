package Calculations;

import InvertedIndex.Posting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFIDF {
    private int N = 10;
    public Map<String, Map<String, Double>> calculateTFIDF(Map<String, List<Posting>> invertedIndex) {
        // Map to store TF-IDF values of all terms in each document (docId -> (term -> tfidf))
        Map<String, Map<String, Double>> tfidfMap = new HashMap<>();

        // Calculate TF-IDF for all terms in each document
        for (Map.Entry<String, List<Posting>> entry : invertedIndex.entrySet()) {
            String term = entry.getKey();
            List<Posting> postings = entry.getValue();

            // Calculate IDF
            double idf = Math.log((double) N / postings.size());

            // Calculate TF-IDF for each document
            for (Posting posting : postings) {
                String docID = posting.getDocumentId();
                double tf = posting.getTermFrequency();
                double tfidf = tf * idf;

                // Initialize the map for the document if it doesn't exist
                tfidfMap.putIfAbsent(docID, new HashMap<>());
                tfidfMap.get(docID).put(term, tfidf);
            }
        }
        return tfidfMap;
    }
}
