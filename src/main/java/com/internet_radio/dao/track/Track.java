package com.internet_radio.dao.track;

public class Track {

    private final String song_name;
    private final String artist;

    public String getSong_name() {
        return song_name;
    }

    public String getArtist() {
        return artist;
    }

    public Track(String song_name, String artist) {
        this.song_name = song_name;
        this.artist = artist;
    }

}
