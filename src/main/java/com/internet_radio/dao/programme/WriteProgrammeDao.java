package com.internet_radio.dao.programme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Repository
public class WriteProgrammeDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UUID insert(UUID presenterUuid, LocalDateTime dateBroadcast, String description, String ref) {
        var programmeId = UUID.randomUUID();
        Map<String, Object> params = Map.of(
                "programmeId", programmeId.toString(),
                "presenterId", presenterUuid.toString(),
                "date", Timestamp.valueOf(dateBroadcast),
                "ref", ref,
                "description", description
        );

        namedParameterJdbcTemplate.update("INSERT INTO programmes" +
                " (programme_uuid, programme_presenter_uuid, programme_date_broadcast, programme_ref, programme_description)" +
                " VALUES (:programmeId, :presenterId, :date, :ref, :description)",
                params);

        return programmeId;
    }

}
