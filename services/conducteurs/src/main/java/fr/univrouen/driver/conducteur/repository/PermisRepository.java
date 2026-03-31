package fr.univrouen.driver.conducteur.repository;

import fr.univrouen.driver.conducteur.domain.Permis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisRepository extends JpaRepository<Permis, Integer> {
  
}
