package InvertedIndex;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndex {
    // Mapping a term to its posting list
    private HashMap<String, List<Posting>> invertedIndex;

    public InvertedIndex() {
        invertedIndex = new HashMap<>();
    }

    public void addingTermToDoc(String term, String docID){

        //if term not found in the map , add it
        if(!invertedIndex.containsKey(term)){
            invertedIndex.put(term,new ArrayList<>());
        }

        // Making a list of postings
        List<Posting> postings = invertedIndex.get(term);

        // this flag for checking the existence of the term
        boolean flag = false;

        // this loop for updating the posting list for the term processed
        // if the term found in doc... , increase the frequency of it and mark it as found
        for (Posting p : postings){
            if (p.getDocumentId().equals(docID)){
                p.incrementFrequency();
                flag = true;
                break;
            }
        }

        // if the term not found in the postings list , add it with a freq 1 as it's the first time this term seen
        if (!flag) postings.add(new Posting(docID,1));

        invertedIndex.put(term, postings);
    }



    // printing the index in a table format to be readable
    public void printTable() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("InvertedIndexTable.txt"))) {
            writer.printf("%-20s | %-20s | %-10s%n", "Term", "Document ID", "Term Frequency");
            writer.println("---------------------------------------------------------------");

            for (Map.Entry<String, List<Posting>> x : invertedIndex.entrySet()) {
                String term = x.getKey();
                List<Posting> postings = x.getValue();

                for (Posting posting : postings) {
                    writer.printf("%-20s | %-20s | %-10d%n",
                            term, posting.getDocumentId(), posting.getTermFrequency());
                }
            }

            System.out.println("Inverted index table has been written to InvertedIndexTable.txt");

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    public HashMap<String, List<Posting>> getInvertedIndex() {
        return invertedIndex;
    }
}
