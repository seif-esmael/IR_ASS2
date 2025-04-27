import Calculations.CosineSimilarity;
import Calculations.TFIDF;
import InvertedIndex.InvertedIndexConstructor;
import InvertedIndex.InvertedIndex;
import PreProcessing.Normalizer;
import PreProcessing.Stemmer;
import PreProcessing.StopWordsRemover;
import PreProcessing.Tokenizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        WebCrawling w = new WebCrawling();
        Map<String, String> documentContent = w.crawl();

        Tokenizer tokenizer = new Tokenizer();
        Map<String, List<String>> tokenizedContent = new HashMap<>();
        for (Map.Entry<String, String> entry : documentContent.entrySet()) {
            String docID = entry.getKey();
            String content = entry.getValue();
            List<String> tokens = tokenizer.tokenize(content);
            tokenizedContent.put(docID, tokens);
        }

        Normalizer normalizer = new Normalizer();
        Map<String, List<String>> normalizedContent = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : tokenizedContent.entrySet()) {
            String docID = entry.getKey();
            List<String> tokens = entry.getValue();
            List<String> normalizedTokens = normalizer.normalize(tokens);
            normalizedContent.put(docID, normalizedTokens);
        }

        Stemmer stemmer = new Stemmer();
        Map<String, List<String>> stemmedContent = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : normalizedContent.entrySet()) {
            String docID = entry.getKey();
            List<String> tokens = entry.getValue();
            List<String> stemmedTokens = stemmer.stemTokens(tokens);
            stemmedContent.put(docID, stemmedTokens);
        }

        StopWordsRemover stopWordsRemover = new StopWordsRemover();
        Map<String, List<String>> filteredContent = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : stemmedContent.entrySet()) {
            String docID = entry.getKey();
            List<String> tokens = entry.getValue();
            List<String> filteredTokens = stopWordsRemover.removeStopWords(tokens);
            filteredContent.put(docID, filteredTokens);
        }

        InvertedIndex invertedIndex = InvertedIndexConstructor.constructInvertedIndex(filteredContent);
        TFIDF tfidfCalculator = new TFIDF();
        Map<String, Map<String, Double>> tfidfMap = tfidfCalculator.calculateTFIDF(invertedIndex.getInvertedIndex());

        while (true) {
            System.out.println("Enter your query:");
            String query = new java.util.Scanner(System.in).nextLine();
            UserQuery userQuery = new UserQuery(query);
            List<String> queryTokens = userQuery.getTokens();
            InvertedIndex queryInvertedIndex = InvertedIndexConstructor.constructInvertedIndex(Map.of("query", queryTokens));
            Map<String, Double> queryTFIDF = tfidfCalculator.calculateTFIDF(queryInvertedIndex.getInvertedIndex()).get("query");

            Map<String, Double> results = new HashMap<>();
            for (String docID : tfidfMap.keySet()) {
                Map<String, Double> docTFIDF = tfidfMap.get(docID);
                double cosineSimilarity = CosineSimilarity.calculateCosineSimilarity(queryTFIDF, docTFIDF);
                results.put(docID, cosineSimilarity);
            }

            List<Map.Entry<String, Double>> ranks = results.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).toList();
            System.out.println("Top 10 results:");
            for (int i = 0; i < 10; i++) {
                Map.Entry<String, Double> entry = ranks.get(i);
                String docID = entry.getKey();
                double score = entry.getValue();
                System.out.println(STR."Document ID: \{docID}, Score: \{score}");
            }
            System.out.println("Do you want to search again? (yes/no)");
            String answer = new java.util.Scanner(System.in).nextLine();
            if (answer.equalsIgnoreCase("no")) {
                break;
            }
        }
    }
}