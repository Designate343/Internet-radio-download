package com.internet_radio.stations;

public enum Stations {
    BBC_6_MUSIC(6, "bbc6Music", "https://www.bbc.co.uk/schedules/p00fzl65/");

    private final int stationId;
    private final String name;
    private final String url;

    Stations(int stationId, String name, String url) {
        this.stationId = stationId;
        this.name = name;
        this.url = url;
    }

    public static Stations getFromId(int id) {
        for (var station : Stations.values()) {
            if (station.getStationId() == id) {
                return station;
            }
        }
        return null;
    }

    public int getStationId() {
        return stationId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
