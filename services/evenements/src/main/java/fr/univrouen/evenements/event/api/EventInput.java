package fr.univrouen.evenements.event.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record EventInput(
        @NotNull Integer vehicle_id,
        @NotNull Instant time,
        @NotBlank String event
) {
}
