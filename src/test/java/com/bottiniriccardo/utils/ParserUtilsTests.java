package com.bottiniriccardo.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;

import com.bottiniriccardo.webcrawler.utils.ParserUtils;

public class ParserUtilsTests {

    private static final Logger logger = Logger.getLogger(ParserUtilsTests.class.getName());

    @Test
    public void testSameDomainUrls() {
        String stringUrl1 = "https://www.google.com";
        String stringUrl2 = "https://drive.google.com";
        try {
            logger.info("testSameDomainUrls with " + stringUrl1 + " and " + stringUrl2);

            URL url1 = new URL(stringUrl1);
            URL url2 = new URL(stringUrl2);

            Assert.assertTrue(ParserUtils.isSameDomain(url1, url2));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDifferentDomainUrls() {
        String stringUrl1 = "https://www.google.com";
        String stringUrl2 = "https://www.facebook.com";
        try {
            logger.info("testDifferentDomainUrls with " + stringUrl1 + " and " + stringUrl2);

            URL url1 = new URL(stringUrl1);
            URL url2 = new URL(stringUrl2);

            Assert.assertFalse(ParserUtils.isSameDomain(url1, url2));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUrlFormat() {
        String url1 = "www.google.com";
        logger.info("testUrlFormat with " + url1);

        Assert.assertEquals("https://www.google.com/", ParserUtils.checkUrlFormat(url1));
    }

    @Test
    public void testIsFile() {
        String url1 = "https://upload.wikimedia.org/wikipedia/commons/9/96/Google_web_search.png";
        logger.info("testIsFile with " + url1);

        Assert.assertTrue(ParserUtils.isFile(url1));
    }

    @Test
    public void testIsNotAFile() {
        String url1 = "https://www.google.com";
        logger.info("testIsNotAFile with " + url1);

        Assert.assertFalse(ParserUtils.isFile(url1));
    }

}
