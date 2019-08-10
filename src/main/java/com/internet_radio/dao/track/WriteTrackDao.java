package com.internet_radio.dao.track;

import com.internet_radio.dataclasses.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class WriteTrackDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void writeTrack(Track track, UUID programmeId, UUID presenterId) {
        Map<String, Object> params = Map.of(
                "trackName", track.getSong_name(),
                "artistName", track.getArtist(),
                "programmeId", programmeId.toString(),
                "presenterId", presenterId.toString()
        );

        jdbcTemplate.update("INSERT INTO tracks" +
                " (track_name, track_artist, track_programme_origin_uuid, track_presenter_uuid)" +
                " VALUES (:trackName, :artistName, :programmeId, :presenterId)",
                params);
    }

}
