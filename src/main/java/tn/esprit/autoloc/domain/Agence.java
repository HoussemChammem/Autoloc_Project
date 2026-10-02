package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String ville;

    @Column(nullable = false, length = 255)
    private String adresse;

    @Column(length = 20)
    private String telephone;

    // Une agence possède plusieurs véhicules (1 → *)
    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehicule> vehicules;

    // Une agence emploie plusieurs employés (1 → *)
    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employe> employes;
}
