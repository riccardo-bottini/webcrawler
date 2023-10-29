package com.bottiniriccardo.webcrawler.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bottiniriccardo.webcrawler.Main;
import com.bottiniriccardo.webcrawler.utils.FileUtils;
import com.bottiniriccardo.webcrawler.utils.ParserUtils;

public class Crawler {

    private static final Logger logger = Logger.getLogger(Crawler.class.getName());

    private static List<CrawlerThread> crawlerThreads = new ArrayList<>();

    public static void read(String inputUrl) {

        inputUrl = ParserUtils.checkUrlFormat(inputUrl);
        logger.info("Start from " + inputUrl);

        try {
            URL websiteToCrawl = new URL(inputUrl);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(websiteToCrawl.openStream()));

            crawlUrl(websiteToCrawl, bufferedReader, inputUrl);

            bufferedReader.close();

            // Start a thread for every URL in the list
            for (String urlFound : Main.visitedUrls) {
                CrawlerThread crawlerThread = new CrawlerThread(inputUrl, urlFound);
                crawlerThreads.add(crawlerThread);
                crawlerThread.start();
            }

            // Wait the termination of every thread
            waitForTermination(crawlerThreads);

            FileUtils.writeToAFile();

        } catch (MalformedURLException e) {
            logger.severe("Exception caused by the URL: " + e.getMessage() + ". Check your URL " + inputUrl);
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void waitForTermination(List<CrawlerThread> crawlerThreads) throws InterruptedException {
        // Wait the termination of every thread
        for (CrawlerThread thread : crawlerThreads) {
            thread.join();
        }
    }

    public static void crawlUrl(URL websiteToCrawl, BufferedReader bufferedReader, String inputUrl) throws IOException{
        String websiteRow = null;

        while ((websiteRow = bufferedReader.readLine()) != null) {
            Document doc = Jsoup.parse(websiteRow);

            // Select rows that has the "a" element
            Elements links = doc.select("a");

            for (Element link : links) {

                // Exctract the "href" element
                String matchedUrlString = link.attr("href");
                // Exclude URLs that referes to a file, the external links (with a different
                // domain) and the input URL
                URL matchedUrl = new URL(matchedUrlString);
                if (!(ParserUtils.isFile(matchedUrlString)) &&
                        ParserUtils.isSameDomain(websiteToCrawl, matchedUrl) &&
                        !(matchedUrlString.equalsIgnoreCase(inputUrl))) {
                    logger.debug("Found URL: " + matchedUrlString);
                    // Add the Url to the list
                    Main.visitedUrls.add(matchedUrlString);
                }
            }
        }
    }
}
