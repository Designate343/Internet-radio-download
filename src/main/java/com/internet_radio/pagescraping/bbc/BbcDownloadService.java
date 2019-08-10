package com.internet_radio.pagescraping.bbc;

import com.internet_radio.dao.presenter.PresenterCache;
import com.internet_radio.dao.presenter.WritePresenterDao;
import com.internet_radio.dao.programme.WriteProgrammeDao;
import com.internet_radio.dao.track.WriteTrackDao;
import com.internet_radio.dataclasses.ProgrammeData;
import com.internet_radio.dataclasses.Track;
import com.internet_radio.pagescraping.DownloadService;
import com.internet_radio.stations.Stations;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class BbcDownloadService implements DownloadService {

    @Autowired
    private ParseBBCSchedulePage parseBBCSchedulePage;
    @Autowired
    private WritePresenterDao writePresenterDao;
    @Autowired
    private PresenterCache presenterCache;
    @Autowired
    private WriteProgrammeDao writeProgrammeDao;
    @Autowired
    private WriteTrackDao writeTrackDao;

    @Override
    public void downloadProgrammesAndWriteToDatabase(int stationId, LocalDate downloadStart, LocalDate downloadEnd) {
        LocalDate end = downloadEnd != null ? downloadEnd : LocalDate.now();

        var station = Stations.getFromId(stationId);
        if (station == null) {
            throw new RuntimeException("Station not found, aborting");
        }
        var baseUrl = station.getUrl();

        downloadStart.datesUntil(end).forEach(date -> {
            Set<ProgrammeData> programmesOnDay = getProgrammesOnDay(baseUrl, date);
            writeProgrammesOnDay(programmesOnDay, stationId);
        });
    }

    private Set<ProgrammeData> getProgrammesOnDay(final String baseUrl, LocalDate date) {
        Set<ProgrammeData> programmes = new HashSet<>();
        System.out.println("downloading from date " + date.toString());
        String url = baseUrl + date.getYear() + "/" + String.format("%02d", date.getMonthValue()) + "/"
                + String.format("%02d", date.getDayOfMonth());
        try {
            Connection conn = Jsoup.connect(url);
            Document wholeDocument = conn.get();
            programmes.addAll(parseBBCSchedulePage.getAllShows(wholeDocument, date));
        } catch (IOException e) {
            Logger.getLogger(BbcDownloadService.class.getName()).log(Level.WARNING,
                    "Unable to retrieve shows on date " + date.toString() + "\n" + e.getMessage());
        }
        return programmes;
    }

    private void writeProgrammesOnDay(Set<ProgrammeData> programmesPlayed, int stationId) {
        for (var programme : programmesPlayed) {
            UUID presenterId = writePresenterDao.writePresenter(programme.getPresenterName(), stationId);

            UUID programmeId = writeProgrammeDao.insert(presenterId,
                    programme.getDate(),
                    programme.getDescription(),
                    programme.getProgrammeHref());

            for (Track track : programme.getTracksPlayed()) {
                writeTrackDao.writeTrack(track, programmeId, presenterId);
            }
        }
    }

}
