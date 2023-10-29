package com.bottiniriccardo.webcrawler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.bottiniriccardo.webcrawler.crawler.Crawler;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {

        if (args.length == 0){
            logger.severe("Missing URL. Please add a valid URL when starting the program.");
            System.exit(0);
        }

        String url = args[0];

        logger.info("Start crawling");

        Crawler.read(url);
    }
}