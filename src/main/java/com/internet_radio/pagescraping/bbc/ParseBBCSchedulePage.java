package com.internet_radio.pagescraping.bbc;

import com.internet_radio.dao.programme.ProgrammeDto;
import com.internet_radio.pagescraping.ScrapingTools;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
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

import static java.util.Collections.emptyList;

@Service
public class ParseBBCSchedulePage extends BbcPageParser {

    private static final String css_class_selector = "list-unstyled g-c-l";
    private static final String CSS_CLASS_PROGRAMME_PAGE = "br-blocklink__link block-link__target";

    @Autowired
    private ParseBBCProgrammePage parseBBCProgrammePage;

    public List<ProgrammeDto> getAllProgrammes(Document schedulePageDocument, LocalDate date) {
        Element relevantPageSegment = schedulePageDocument.getElementsByClass(css_class_selector).first();
        int day = date.getDayOfMonth();

        List<Future<Document>> programmeDocumentsForDay = downloadDocumentsForDay(relevantPageSegment, day);

        List<ProgrammeDto> programmesOnDay = new ArrayList<>(programmeDocumentsForDay.size());
        for (Future<Document> programmeInfoFuture : programmeDocumentsForDay) {
            Document programmePage = collectFutureDocument(programmeInfoFuture);
            if (programmePage == null) {
                return emptyList();
            }
            programmesOnDay.add(parseBBCProgrammePage.parseForProgrammeData(programmePage));
        }

        return programmesOnDay;
    }

    private List<Future<Document>> downloadDocumentsForDay(Element relevantPageSegment, int day) {
        List<Future<Document>> documents = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(8);
        for (Element eachProgramme : relevantPageSegment.getElementsByClass("grid-wrapper")) {

            String dateTimeRaw = eachProgramme.getElementsByClass("broadcast__time gamma").attr("content");
            LocalDateTime dateTime = rawBBCDateStringToLocalDateTime(dateTimeRaw.substring(0, dateTimeRaw.indexOf("+") - 3));
            if (dateTime == null) {
                continue;
            }

            Elements programme = eachProgramme.getElementsByClass(CSS_CLASS_PROGRAMME_PAGE);
            String programmePageHref = programme.attr("href");
            if (dateTime.getDayOfMonth() == day) {

                try {
                    Future<Document> documentFuture = service
                            .submit(() -> ScrapingTools.getDocument(programmePageHref));

                    documents.add(documentFuture);
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

}
