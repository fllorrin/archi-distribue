package fr.univrouen.driver.conducteur.api;

import fr.univrouen.driver.conducteur.domain.Permis;

public record ConducteurResponse(
        Integer id,
        String prenom,
        String nom,
        Permis permis
) {
}