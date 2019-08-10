package com.internet_radio.pagescraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScrapingTools {
	
	public static Document getDocument(String url) {
		Document wholeDocument = null;
        try {
        	wholeDocument = Jsoup.connect(url).get();
        } catch (IOException e) {
        	Logger.getLogger(ScrapingTools.class.getName()).log(Level.INFO, e.getMessage());
        }
		return wholeDocument;
	}

}
