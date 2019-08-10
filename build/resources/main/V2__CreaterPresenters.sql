CREATE TABLE IF NOT EXISTS presenters (
    presenter_name VARCHAR(80) NOT NULL,
    presenter_uuid VARCHAR(36) NOT NULL PRIMARY KEY,
    presenter_station_id INTEGER,
    FOREIGN KEY (presenter_station_id) REFERENCES stations (station_id)
)