package com.bottiniriccardo.webcrawler;

import java.util.logging.Logger;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

import com.bottiniriccardo.webcrawler.crawler.Crawler;

public class WebCrawlerApplicationTests {

    private static final Logger logger = Logger.getLogger(WebCrawlerApplicationTests.class.getName());

    @Test
    public void testCrawlWebsite() {
        logger.info("testCrawlWebsite");

        String htmlBody = "<html>\n" + //
                "<body>\n" + //
                "    <h1>Collegamenti a Siti Web Casuali</h1>\n" + //
                "    <ul>\n" + //
                "        <li><a href=\"https://www.foobar.com/foobar\">Esempio.com</a></li>\n" + //
                " <li><a href=\"https://www.google.com\">Google</a></li>\n" + //
                " <li><a href=\"https://www.yahoo.com\">Yahoo</a></li>\n" + //
                " <li><a href=\"https://www.openai.com\">OpenAI</a></li>\n" + //
                "        <li><a href=\"https://www.foobar.com/test\">Esempio.com</a></li>\n" + //
                "    </ul>\n" + //
                "</body>\n" + //
                "</html>";

        Reader reader = new StringReader(htmlBody);
        String url = "https://www.foobar.com";
        try {
            Crawler.crawlUrl(new URL(url), new BufferedReader(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(2, Main.visitedUrls.size());
    }

}
