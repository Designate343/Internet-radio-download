package pagescraping.bbc;

import com.internet_radio.dataclasses.ProgrammeData;
import com.internet_radio.dataclasses.Track;
import com.internet_radio.pagescraping.bbc.ParseBBCDJPage;
import com.internet_radio.pagescraping.bbc.ParseBBCSchedulePage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;


public class TestProgrammeInfoParser {

    @Test
    public void testParseDJPage() throws Exception {
        String url = System.getProperty("user.dir") + "/src/test/resources/sampleProgrammePage.html";
        File htmlFile = new File(url);
        Document doc = Jsoup.parse(htmlFile, "UTF-8");
        List<Track> res = ParseBBCDJPage.getAllTracks(doc);
        Assert.assertFalse(res.isEmpty());
        Assert.assertEquals("Bella and Me", res.get(0).getArtist());
        Assert.assertEquals("Caleb Meyer (Radio 2, Nick Barraclough Session, Apr 1998)", res.get(3).getSong_name());
    }

    @Test
    public void canGetPresenterName() throws Exception {
        String url = System.getProperty("user.dir") + "/src/test/resources/sampleProgrammePage.html";
        File htmlFile = new File(url);
        Document doc = Jsoup.parse(htmlFile, "UTF-8");
        String presenterName = ParseBBCDJPage.getPresenter(doc);
        Assert.assertEquals("Gideon Coe", presenterName);
    }

    @Test
    public void canGetDescription() throws Exception {
        String url = System.getProperty("user.dir") + "/src/test/resources/sampleProgrammePage.html";
        File htmlFile = new File(url);
        Document doc = Jsoup.parse(htmlFile, "UTF-8");
        String description = ParseBBCDJPage.getDescription(doc);
        Assert.assertEquals("As many records as we can manage including sessions and concerts from the BBC archive.", description);
    }

    @Test
    public void canParseScehulePage() throws Exception {
        String url = System.getProperty("user.dir") + "/src/test/resources/sampleProgrammeListPage.html";
        File htmlFile = new File(url);
        Document doc = Jsoup.parse(htmlFile, "UTF-8");
        var schedulePageParser = new ParseBBCSchedulePage();
        List<ProgrammeData> programmesOnDay = schedulePageParser.getAllShows(doc, LocalDate.now());
        Assert.assertEquals(0, programmesOnDay.size());
    }
}
