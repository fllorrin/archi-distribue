package fr.univrouen.evenements.event.api;

import java.time.Instant;

public record EventResponse(
        Integer id,
        Integer vehicle_id,
        Instant time,
        String event
) {
}
