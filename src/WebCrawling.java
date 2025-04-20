import InvertedIndex.InvertedIndex;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class WebCrawling {

    // A set for visited URLs to prevent crawling the same URL more than once
    private final HashSet<String> visited = new HashSet<String>();

    // Mapping a specific document to a link
    private HashMap<String, String> documents = new HashMap<>();

    InvertedIndex invertedIndex;
    WebCrawling(){
        invertedIndex = new InvertedIndex();
    }

    public void tokenize(String ID, String text){
        text = text.toLowerCase();
        String[] tokens = text.split("\\W+");
        for (String t:tokens) invertedIndex.addingTermToDoc(t,ID);
     }

    public void crawl() {
        // Provided URLs in the assignment as examples
        String url1 = "https://en.wikipedia.org/wiki/List_of_pharaohs";
        String url2 = "https://en.wikipedia.org/wiki/Pharaoh";

        // Linked list for the pages
        LinkedList<String> links = new LinkedList<String>();

        // Initially adding the 2 URLs to crawl
        links.add(url1);
        links.add(url2);

        // Maximum number of documents to crawl
        int maxDocs = 10;

        // Crawling process
        while (!links.isEmpty() && visited.size() < maxDocs) {
            // Pop the first URL to crawl
            String url = links.poll();

            // This condition checks if the URL is a Wikipedia link and if it hasn't been visited
            if (url.startsWith("https://en.wikipedia.org/wiki/") && !visited.contains(url)) {
                try {
                    // Connecting to the URL, fetching, and parsing the HTML content, storing in Document
                    Document doc = Jsoup.connect(url).get();

                    // Marking this URL as visited
                    visited.add(url);

                    // Fetching content
                    String text = doc.body().text();

                    // Storing each doc ID (url) to its content in the map
                    documents.put(url,text);

                    // Tokenization stage
                    tokenize(url,text);

                    // Select all <a href="..."> elements to find links on the page
                    Elements urls = doc.select("a[href]");
                    for (Element x : urls) {

                        // Get the absolute URL of the <a href> tag
                        String absUrl = x.absUrl("href");

                        // Add it to the list of links if it's a valid Wikipedia link and hasn't been visited
                        if (absUrl.startsWith("https://en.wikipedia.org/wiki/") && !visited.contains(absUrl)) {
                            links.add(absUrl);
                        }

                        // Stop if we've reached the maximum document limit
                        if (visited.size() >= maxDocs) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    // Exception for not correct links
                    System.err.println("Error connecting to " + url + ": " + e.getMessage());
                }
            }

            // terminate the outer loop if the visited size exceeds the maximum limit
            if (visited.size() >= maxDocs) {
                break;
            }
        }

        // Print the list of crawled pages
        System.out.println("\nCrawled pages:");
        for (String page : visited) {
            System.out.println(page);
        }
        invertedIndex.printTable();
    }
}
