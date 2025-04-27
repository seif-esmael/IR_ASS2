package InvertedIndex;

import java.util.List;
import java.util.Map;

public class InvertedIndexConstructor {
    public static InvertedIndex constructInvertedIndex(Map<String, List<String>> termDocMap) {
        InvertedIndex invertedIndex = new InvertedIndex();

        for (Map.Entry<String, List<String>> entry : termDocMap.entrySet()) {
            String docID = entry.getKey();
            List<String> tokens = entry.getValue();

            for (String token : tokens) {
                invertedIndex.addingTermToDoc(token, docID);
            }
        }

        return invertedIndex;
    }
}
