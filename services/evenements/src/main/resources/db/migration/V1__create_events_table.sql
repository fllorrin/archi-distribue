CREATE TABLE IF NOT EXISTS events (
    id SERIAL PRIMARY KEY,
    vehicle_id INTEGER NOT NULL,
    event_time TIMESTAMPTZ NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    external_event_id VARCHAR(255) UNIQUE
);

CREATE INDEX IF NOT EXISTS idx_events_vehicle_id ON events(vehicle_id);
CREATE INDEX IF NOT EXISTS idx_events_event_type ON events(event_type);
CREATE INDEX IF NOT EXISTS idx_events_event_time ON events(event_time DESC);
