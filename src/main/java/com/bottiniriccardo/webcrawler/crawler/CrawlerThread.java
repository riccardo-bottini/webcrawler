package com.bottiniriccardo.webcrawler.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bottiniriccardo.webcrawler.Main;
import com.bottiniriccardo.webcrawler.utils.ParserUtils;

public class CrawlerThread extends Thread {

    private static final Logger logger = Logger.getLogger(CrawlerThread.class.getName());

    private String parentUrl;
    private String url;
    private Set<String> visitedThreadUrls;

    public CrawlerThread(String parentUrl, String url) {
        this.parentUrl = parentUrl;
        this.url = url;
        this.visitedThreadUrls = new HashSet<>();
    }

    public Set<String> getVisitedThreadUrls() {
        return visitedThreadUrls;
    }

    @Override
    public void run() {
        logger.info("Start Thread on " + url);
        try {
            URL websiteToCrawl = new URL(url);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(websiteToCrawl.openStream()));

            String websiteRow = null;

            while ((websiteRow = bufferedReader.readLine()) != null) {
                Document doc = Jsoup.parse(websiteRow);

                // Select rows that has the "a" element
                Elements links = doc.select("a");

                for (Element link : links) {
                    // Exctract the "href" element
                    String matchedUrlString = link.attr("href");
                    
                    // Check if the content of href element is valid
                    // Exclude URLs that referes to a file, the external links (with a different
                    // domain) and the input URL
                    URL matchedUrl = ParserUtils.isValidUrl(matchedUrlString);
                    if (matchedUrl != null &&
                            !(ParserUtils.isFile(matchedUrlString)) &&
                            ParserUtils.isSameDomain(websiteToCrawl, matchedUrl) &&
                            !(matchedUrl.sameFile(new URL(parentUrl))) &&
                            addUrl(matchedUrlString)) {
                        logger.info("Thread URL: " + matchedUrlString);
                        CrawlerThread crawlerThread = new CrawlerThread(parentUrl, matchedUrlString);
                        crawlerThread.start();
                    }
                }
            }
            
            bufferedReader.close();

        } catch (MalformedURLException e) {
            logger.severe("Exception caused by URL");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }

    private synchronized boolean addUrl(String url) {
        return Main.visitedUrls.add(url);
    }
}
