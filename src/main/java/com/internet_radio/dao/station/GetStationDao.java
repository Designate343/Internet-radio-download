package com.internet_radio.dao.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonMap;

@Repository
public class GetStationDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public StationDto execute(int stationId) {
        List<StationDto> res =  jdbcTemplate.query("SELECT station_name, station_id" +
                " FROM stations" +
                " WHERE station_id = :stationId",
                singletonMap("stationId", stationId),
                (rs, i) -> new StationDto(rs.getString("station_name"), rs.getInt("station_id")));
        return res == null ? null : res.get(0);
    }

    public static class StationDto {
        private final String name;
        private final int id;

        public StationDto(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }
}
