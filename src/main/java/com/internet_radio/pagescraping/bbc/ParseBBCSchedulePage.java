package com.internet_radio.pagescraping.bbc;

import com.internet_radio.dataclasses.ProgrammeData;
import com.internet_radio.dataclasses.Track;
import com.internet_radio.date.DateUtilities;
import com.internet_radio.pagescraping.ScrapingTools;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ParseBBCSchedulePage {

    private static final String css_class_selector = "list-unstyled g-c-l";
    private static final String css_class_programme_page = "br-blocklink__link block-link__target";

    public List<ProgrammeData> getAllShows(Document wholeDocument, LocalDate date) {
        Element relevantPageSegment = wholeDocument.getElementsByClass(css_class_selector).first();
        int day = date.getDayOfMonth();

        List<ProgrammeInfoFuture> documents = downloadDocumentsForDay(relevantPageSegment, day);

        List<ProgrammeData> programmesOnDay = new ArrayList<>(documents.size());
        for (ProgrammeInfoFuture programmeInfoFuture : documents) {
            ProgrammeData programmeData = extractProgrammeDataFromProgrammeDocument(programmeInfoFuture);
            if (programmeData != null) {
                programmesOnDay.add(programmeData);
            }
        }

        return programmesOnDay;
    }

    private ProgrammeData extractProgrammeDataFromProgrammeDocument(ProgrammeInfoFuture programmeInfoFuture) {
        Document doc = collectFutureDocument(programmeInfoFuture.documentFuture);
        if (doc == null) {
            return null;
        }
        String presenter = ParseBBCDJPage.getPresenter(doc);
        String description = ParseBBCDJPage.getDescription(doc);
        List<Track> allTracksOnProgramme = ParseBBCDJPage.getAllTracks(doc);
        return new ProgrammeData(presenter, programmeInfoFuture.dateTime, description, allTracksOnProgramme, programmeInfoFuture.url);
    }

    private List<ProgrammeInfoFuture> downloadDocumentsForDay(Element relevantPageSegment, int day) {
        List<ProgrammeInfoFuture> documents = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(8);
        for (Element eachProgramme : relevantPageSegment.getElementsByClass("grid-wrapper")) {
            String dateTimeRaw = eachProgramme.getElementsByClass("broadcast__time gamma").attr("content");
            LocalDateTime dateTime = DateUtilities.rawBBCDateStringToLocalDateTime(dateTimeRaw.substring(0, dateTimeRaw.indexOf("+") - 3));
            Elements programme = eachProgramme.getElementsByClass(css_class_programme_page);
            String programmePageHref = programme.attr("href");
            if (dateTime.getDayOfMonth() == day) {

                try {
                    var programmeInfo = new ProgrammeInfoFuture();
                    programmeInfo.dateTime = dateTime;
                    programmeInfo.url = programmePageHref;
                    programmeInfo.documentFuture = service
                            .submit(() -> ScrapingTools.getDocument(programmePageHref));

                    documents.add(programmeInfo);
                } catch (Exception exception) {
                    service.shutdown();
                    throw new RuntimeException(exception);
                }

            }
        }
        service.shutdown();
        return documents;
    }

    private Document collectFutureDocument(Future<Document> documentFuture) {
        Document document = null;

        try {
            document = documentFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            Logger.getLogger(ParseBBCSchedulePage.class.getName()).log(Level.FINE, e.getMessage(), e);
        }

        return document;
    }

    private static class ProgrammeInfoFuture {
        private String url;
        private LocalDateTime dateTime;
        private Future<Document> documentFuture;
    }

}
