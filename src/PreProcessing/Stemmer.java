package PreProcessing;

import org.tartarus.snowball.ext.PorterStemmer;

import java.util.ArrayList;
import java.util.List;

public class Stemmer {
    public List<String> stemTokens(List<String> tokens) {
        // Create a new PorterStemmer instance and a list to store the stemmed tokens
        PorterStemmer stemmer = new PorterStemmer();
        List<String> stemmedTokens = new ArrayList<>(tokens.size());

        // Iterate through each token and stem it
        for (String token : tokens) {
            stemmer.setCurrent(token);
            stemmer.stem();
            stemmedTokens.add(stemmer.getCurrent());
        }

        return stemmedTokens;
    }
}
