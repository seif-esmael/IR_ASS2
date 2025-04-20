package InvertedIndex;

public class Posting {
    private String documentId;
    private int termFrequency;


    public Posting(String documentId, int termFrequency) {
        this.documentId = documentId;
        this.termFrequency = termFrequency;
    }

    public String getDocumentId() {
        return documentId;
    }

    public int getTermFrequency() {
        return termFrequency;
    }

    public void incrementFrequency() {
        this.termFrequency++;
    }

    @Override
    public String toString() {
        return documentId + " | " + termFrequency;
    }
}
