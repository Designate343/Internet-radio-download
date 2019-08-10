package pagescraping.bbc;

import com.internet_radio.dao.programme.ProgrammeDto;
import com.internet_radio.dao.track.Track;
import com.internet_radio.pagescraping.bbc.ParseBBCProgrammePage;
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

        ParseBBCProgrammePage parseBBCProgrammePage = new ParseBBCProgrammePage();
        ProgrammeDto programmeDto = parseBBCProgrammePage.parseForProgrammeData(doc);
        List<Track> res = programmeDto.getTracksPlayed();
        Assert.assertFalse(res.isEmpty());
        Assert.assertEquals("Bella and Me", res.get(0).getArtist());
        Assert.assertEquals("Caleb Meyer (Radio 2, Nick Barraclough Session, Apr 1998)", res.get(3).getSong_name());
    }

    @Test
    public void canGetPresenterName() throws Exception {
        String url = System.getProperty("user.dir") + "/src/test/resources/sampleProgrammePage.html";
        File htmlFile = new File(url);
        Document doc = Jsoup.parse(htmlFile, "UTF-8");

        ParseBBCProgrammePage parseBBCProgrammePage = new ParseBBCProgrammePage();
        ProgrammeDto programmeDto = parseBBCProgrammePage.parseForProgrammeData(doc);

        String presenterName = programmeDto.getPresenterName();
        Assert.assertEquals("Gideon Coe", presenterName);
    }

    @Test
    public void canGetDescription() throws Exception {
        String url = System.getProperty("user.dir") + "/src/test/resources/sampleProgrammePage.html";
        File htmlFile = new File(url);
        Document doc = Jsoup.parse(htmlFile, "UTF-8");

        ParseBBCProgrammePage parseBBCProgrammePage = new ParseBBCProgrammePage();
        ProgrammeDto programmeDto = parseBBCProgrammePage.parseForProgrammeData(doc);

        String description = programmeDto.getDescription();
        Assert.assertEquals("As many records as we can manage including sessions and concerts from the BBC archive.", description);
    }

    @Test
    public void canParseScehulePage() throws Exception {
        String url = System.getProperty("user.dir") + "/src/test/resources/sampleProgrammeListPage.html";
        File htmlFile = new File(url);
        Document doc = Jsoup.parse(htmlFile, "UTF-8");
        var schedulePageParser = new ParseBBCSchedulePage();
        List<ProgrammeDto> programmesOnDay = schedulePageParser.getAllProgrammes(doc, LocalDate.now());
        Assert.assertEquals(0, programmesOnDay.size());
    }
}
