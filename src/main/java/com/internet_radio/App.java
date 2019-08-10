package com.internet_radio;

import com.internet_radio.pagescraping.DownloadService;
import com.internet_radio.stations.Stations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

import static com.internet_radio.stations.Stations.BBC_6_MUSIC;

@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private DownloadService downloadService;

    @Override
    public void run(String[] args) {
        LocalDate localDate = LocalDate.now().minusWeeks(4);
        downloadService.downloadProgrammesAndWriteToDatabase(BBC_6_MUSIC.getStationId(), localDate, null);
    }
}

