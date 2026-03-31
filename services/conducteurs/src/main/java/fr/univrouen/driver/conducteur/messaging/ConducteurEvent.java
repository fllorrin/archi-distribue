package fr.univrouen.driver.conducteur.messaging;

import java.time.Instant;

public record ConducteurEvent(
        Integer id,
        String prenom,
        String nom,
        String event,
        Instant timestamp,
        String version
) {

}
