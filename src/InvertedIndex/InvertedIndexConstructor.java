package InvertedIndex;

import java.util.List;
import java.util.Map;

public class InvertedIndexConstructor {
    public static InvertedIndex constructInvertedIndex(Map<String, List<String>> termDocMap) {
        // Create an instance of InvertedIndex
        InvertedIndex invertedIndex = new InvertedIndex();
        // Iterate through the term-document map
        for (Map.Entry<String, List<String>> entry : termDocMap.entrySet()) {
            String docID = entry.getKey();
            List<String> tokens = entry.getValue();
            // Iterate through the tokens in the document
            for (String token : tokens) {
                // Add the token to the inverted index
                invertedIndex.addingTermToDoc(token, docID);
            }
        }

        return invertedIndex;
    }
}
