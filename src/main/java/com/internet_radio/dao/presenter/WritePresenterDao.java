package com.internet_radio.dao.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public class WritePresenterDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public UUID writePresenter(String presenterName, int stationId) {

        UUID existingEntry = alreadyExists(presenterName, stationId);
        if (existingEntry != null) {
            return existingEntry;
        }

        UUID presenterId = UUID.randomUUID();

        Map<String, Object> params = Map.of("presenterName", presenterName,
                "presenterId", presenterId.toString(),
                "stationId", stationId);

        jdbcTemplate.update("INSERT INTO presenters" +
                "(presenter_name, presenter_uuid, presenter_station_id)" +
                " VALUES (:presenterName, :presenterId, :stationId)",
                params);

        return presenterId;
    }

    private UUID alreadyExists(String presenterName, int stationId) {
        var result = jdbcTemplate.query("SELECT presenter_uuid" +
                " FROM presenters" +
                " WHERE presenter_name = :presenterName" +
                " AND presenter_station_id = :stationId",
                Map.of("presenterName", presenterName,
                        "stationId", stationId),
                (rs, i) -> UUID.fromString(rs.getString("presenter_uuid")));

        return result.isEmpty() ? null : result.get(0);
    }

}
