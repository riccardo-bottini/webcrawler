package com.bottiniriccardo.webcrawler.utils;

import java.net.URL;
import java.util.regex.Pattern;

public class ParserUtils {

    /**
     * Check if the URL needs to be formatted
     * 
     * @param url
     * @return well formatted URL
     */
    public static String checkUrlFormat(String url) {
        if (!url.endsWith("/")) {
            url = url + '/';
        }

        if (!url.startsWith("https://")) {
            url = "https://" + url;
        }
        return url;
    }

    /**
     * Exclude URLs that refer to a file from the crawler
     * 
     * @param link input URL
     * @return true if the URL refers to a file, false instead
     */
    public static boolean isFile(String url) {
        Pattern filePattern = Pattern.compile(".*\\.(pdf|docx|xlsx|pptx|zip|rar|txt|jpeg|jpg|png|gif|mp3|mp4|mkv)$",
                Pattern.CASE_INSENSITIVE);
        return filePattern.matcher(url).matches();
    }

    /**
     * Check if the two URLs have the same domain
     * @param parentUrl
     * @param matchedUrl
     * @return
     */
    public static boolean isSameDomain(URL parentUrl, URL matchedUrl){
        String parentHost = parentUrl.getHost();
        String matchedHost = matchedUrl.getHost();

        int parentDotIndex = parentHost.indexOf(".");
        int matchedDotIndex = matchedHost.indexOf(".");

        String parentDomain = parentHost.substring(parentDotIndex + 1);
        String matchedDomain = matchedHost.substring(matchedDotIndex + 1);

        return parentDomain.equals(matchedDomain);
    }

}
