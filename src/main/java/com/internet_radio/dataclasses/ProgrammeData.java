package com.internet_radio.dataclasses;

import java.time.LocalDateTime;
import java.util.List;

public class ProgrammeData {

    private final String presenterName;
    private final LocalDateTime date;
    private final String description;
    private final List<Track> tracksPlayed;
    private final String programmeHref;

    public ProgrammeData(String presenter, LocalDateTime date, String description, List<Track> info, String programmeHref) {
    	this.presenterName = presenter;
        this.tracksPlayed = info;
        this.date = date;
        this.description = description;
        this.programmeHref = programmeHref;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<Track> getTracksPlayed() {
        return tracksPlayed;
    }

	public String getDescription() {
		return description;
	}

    public String getProgrammeHref() {
        return programmeHref;
    }
}
