package fr.univrouen.driver.conducteur.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "permis")
public class Permis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate dateValidite;

    @Column(nullable = false)
    private Set<PermisTypes> types;

    public Permis() {
    }

    public Permis(LocalDate dateValidite, Set<PermisTypes> types) {
        this.dateValidite = dateValidite;
        this.types = types;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDateValidite() {
        return dateValidite;
    }

    public Set<PermisTypes> getTypes() {
        return types;
    }

    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    public void setTypes(Set<PermisTypes> types) {
        this.types = types;
    }
}
    }
