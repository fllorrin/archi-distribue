package fr.univrouen.driver.conducteur.repository;

import fr.univrouen.driver.conducteur.domain.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConducteurRepository extends JpaRepository<Conducteur, Integer> {
  
}
