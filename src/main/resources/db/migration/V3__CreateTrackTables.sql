CREATE TABLE IF NOT EXISTS tracks ( 
    track_name VARCHAR(200) NOT NULL,
    track_artist VARCHAR(200) NOT NULL,
    track_programme_origin_uuid VARCHAR(36) NOT NULL,
    track_presenter_uuid VARCHAR(36) NOT NULL,
    FOREIGN KEY (track_programme_origin_uuid) REFERENCES programmes (programme_uuid),
    FOREIGN KEY (track_presenter_uuid) REFERENCES presenters (presenter_uuid)
)