CREATE TABLE IF NOT EXISTS download_log (
    download_tag VARCHAR(36) NOT NULL PRIMARY KEY,
    download_date_started TIMESTAMP NOT NULL,
    download_date_ended TIMESTAMP,
    download_finished BOOLEAN DEFAULT FALSE,
    download_request TEXT -- really json but h2 doesn't support json types
)