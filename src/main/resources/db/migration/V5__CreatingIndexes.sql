--programmes
CREATE INDEX idx_programme_presenter_uuid ON programmes(programme_presenter_uuid);

-- tracks
CREATE INDEX idx_track_programme_origin_uuid ON tracks(track_programme_origin_uuid);