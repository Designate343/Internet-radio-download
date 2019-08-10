package com.internet_radio.pagescraping.bbc;

import com.internet_radio.dataclasses.Track;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ParseBBCDJPage {

    private static final String CSSClassTrackSection = "segment segment--music";
    private static final String CSSClassTitle = "br-masthead__title";
    private static final String CSS_CLASS_PROGRAMME_DESCRIPTION = "text--prose longest-synopsis";

    public static List<Track> getAllTracks(Document wholeDocument) {
        List<Track> allTracksPlayed = new ArrayList<>(30);
        if (wholeDocument == null) {
        	return allTracksPlayed;
        }

        var songList = wholeDocument.getElementsByClass(CSSClassTrackSection);
        for (Element element : songList) {
            String songName = "";
            String artist = "";
            //songs all have the <p> tag
            var songNameHack = element.getElementsByTag("p");
            if (songNameHack.hasText()) {
                songName = songNameHack.text();
            }

            var artistName = element.getElementsByClass("artist");
            if (artistName.hasText()) {
                artist = artistName.text();
            }
            if (!songName.isEmpty() && !artist.isEmpty()) {
                allTracksPlayed.add(new Track(songName, artist));
            }
        }
        return allTracksPlayed;
    }

    public static String getDescription(Document document) {
        Elements programmeDescription = document.getElementsByClass(CSS_CLASS_PROGRAMME_DESCRIPTION);
        if (programmeDescription.isEmpty()) {
            //try other page layout
            programmeDescription = document.getElementsByClass("synopsis-toggle__short");
            if (programmeDescription.isEmpty()) {
                return "Description Not Found";
            }
        }
        Elements actualDescription = programmeDescription.get(0).getElementsByTag("p");
        return actualDescription.text();
    }

    public static String getPresenter(Document document) {
        Elements programmeName = document.getElementsByClass(CSSClassTitle);
        return programmeName.get(0).text();
    }

}
