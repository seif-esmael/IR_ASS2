import InvertedIndex.InvertedIndex;
import InvertedIndex.InvertedIndexConstructor;
import PreProcessing.Normalizer;
import PreProcessing.Stemmer;
import PreProcessing.StopWordsRemover;
import PreProcessing.Tokenizer;

import java.util.List;
import java.util.Map;

public class UserQuery {
    private String query;
    private List<String> tokens;

    public UserQuery(String query) {
        this.query = query;
        Tokenizer tokenizer = new Tokenizer();
        tokens = tokenizer.tokenize(query);
        Normalizer normalizer = new Normalizer();
        tokens = normalizer.normalize(tokens);
        Stemmer stemmer = new Stemmer();
        tokens = stemmer.stemTokens(tokens);
        StopWordsRemover stopWordsRemover = new StopWordsRemover();
        tokens = stopWordsRemover.removeStopWords(tokens);
    }

    public String getQuery() {
        return query;
    }

    public List<String> getTokens() {
        return tokens;
    }
}
