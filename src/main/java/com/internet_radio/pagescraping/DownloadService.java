package com.internet_radio.pagescraping;

import java.time.LocalDate;

public interface DownloadService {

    void downloadProgrammesAndWriteToDatabase(int stationId, LocalDate downloadStart, LocalDate downloadEnd);

}