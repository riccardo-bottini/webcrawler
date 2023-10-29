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
                    String matchedUrl = link.attr("href");
                    // Exclude URLs that referes to a file, the external links (with a different
                    // domain) and the parent URL
                    if (!(ParserUtils.isFile(matchedUrl)) && matchedUrl.contains(parentUrl) && !(matchedUrl.equalsIgnoreCase(parentUrl))
                            && addAndStartCrawl(matchedUrl)) {
                        logger.info("THREAD URL: " + matchedUrl);
                        CrawlerThread crawlerThread = new CrawlerThread(parentUrl, matchedUrl);
                        crawlerThread.start();
                    }

                }
            }

            bufferedReader.close();

        } catch (MalformedURLException e){
            logger.severe("Exception caused by URL " + url);
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }

    private synchronized boolean addAndStartCrawl(String url) {
        return Main.visitedUrls.add(url);
    }
}
