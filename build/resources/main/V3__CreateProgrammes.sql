CREATE TABLE IF NOT EXISTS programmes (    
    programme_uuid VARCHAR(36) PRIMARY KEY NOT NULL,
    programme_presenter_uuid VARCHAR(36) NOT NULL,
    programme_date_broadcast timestamp NOT NULL,
    programme_description VARCHAR(1000),
    FOREIGN KEY (programme_presenter_uuid) REFERENCES presenters (presenter_uuid)
)