package com.internet_radio.dataclasses;

import java.util.UUID;

public class Presenter {
    private final String presenterName;
    private final UUID presenterId;
    private final int stationId;

    public Presenter(String presenterName, UUID presenterId, int stationId) {
        this.presenterName = presenterName;
        this.presenterId = presenterId;
        this.stationId = stationId;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public UUID getPresenterId() {
        return presenterId;
    }

    public int getStationId() {
        return stationId;
    }
}
