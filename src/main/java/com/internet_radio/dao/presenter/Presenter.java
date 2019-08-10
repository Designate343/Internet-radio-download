package com.internet_radio.dao.presenter;

import java.util.Objects;

public class Presenter {
    private final String presenterName;
    private final int stationId;

    public Presenter(String presenterName,  int stationId) {
        this.presenterName = presenterName;
        this.stationId = stationId;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public int getStationId() {
        return stationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presenter presenter = (Presenter) o;
        return getStationId() == presenter.getStationId() &&
                getPresenterName().equals(presenter.getPresenterName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPresenterName(), getStationId());
    }
}
