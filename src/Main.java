import Calculations.CosineSimilarity;
import Calculations.TFIDF;
import InvertedIndex.InvertedIndexConstructor;
import InvertedIndex.InvertedIndex;
import PreProcessing.*;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initialize the web crawler and crawl the documents
        WebCrawling w = new WebCrawling();
        Map<String, String> documentContent = w.crawl();
        // process the documents' content
        Map<String, List<String>> filteredContent = TextProcessor.processText(documentContent, true, true, true);
        // Create an inverted index from the filtered content
        InvertedIndex invertedIndex = InvertedIndexConstructor.constructInvertedIndex(filteredContent);
        // Calculate TF-IDF for the documents
        TFIDF tfidfCalculator = new TFIDF();
        Map<String, Map<String, Double>> tfidfMap = tfidfCalculator.calculateTFIDF(invertedIndex.getInvertedIndex());

        while (true) {
            // Prompt the user for a query
            System.out.println("Enter your query:");
            String query = new java.util.Scanner(System.in).nextLine();
            // Process the query
            UserQuery userQuery = new UserQuery(query);
            Map<String, List<String>> queryTokens = userQuery.getTokens(true, true, true);
            // Construct the inverted index for the query
            InvertedIndex queryInvertedIndex = InvertedIndexConstructor.constructInvertedIndex(queryTokens);
            // Calculate TF-IDF for the query
            Map<String, Double> queryVector = tfidfCalculator.calculateTFIDF(queryInvertedIndex.getInvertedIndex()).get("query");
            // Calculate cosine similarity between the query and each document
            Map<String, Double> results = new HashMap<>();
            for (String docID : tfidfMap.keySet()) {
                Map<String, Double> documentVector = tfidfMap.get(docID);
                double cosineSimilarity = CosineSimilarity.calculateCosineSimilarity(queryVector, documentVector);
                results.put(docID, cosineSimilarity);
            }
            // Sort the results by cosine similarity score
            List<Map.Entry<String, Double>> ranks = new ArrayList<>();
            for (Map.Entry<String, Double> entry : results.entrySet()) {
                // Check if the rank list is empty
                if (ranks.isEmpty()){
                    ranks.add(entry);
                }else {
                    boolean added = false;
                    // Check if the current entry's score is greater than the existing scores
                    for (int i = 0; i < ranks.size(); i++) {
                        if (entry.getValue() > ranks.get(i).getValue()) {
                            ranks.add(i, entry);
                            added = true;
                            break;
                        }
                    }
                    // If the entry was not added, it means it has the lowest score
                    if (!added) {
                        ranks.addLast(entry);
                    }
                }
            }
            // Print the top 10 results
            System.out.println("Top 10 results:");
            for (int i = 0; i < 10; i++) {
                Map.Entry<String, Double> entry = ranks.get(i);
                String docID = entry.getKey();
                double score = entry.getValue();
                System.out.println(STR."Rank: \{i+1} Document ID: \{docID}, Score: \{score}");
            }
            // Ask the user if they want to search again
            System.out.println("Do you want to search again? (yes/no)");
            String answer = new java.util.Scanner(System.in).nextLine();
            if (answer.equalsIgnoreCase("no")) {
                break;
            }
        }
    }
}