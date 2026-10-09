# Atelier 3 — Couche Repository Spring Data JPA

## Choix d'interface pour chaque repository

| Interface | Étend | Justification |
|---|---|---|
| `IAgenceRepository` | `JpaRepository<Agence, Long>` | CRUD complet, `findAll` renvoie une `List`, `saveAndFlush` et pagination disponibles. |
| `IEmployeRepository` | `JpaRepository<Employe, Long>` | Idem — accès en lecture/écriture aux employés d'une agence. |
| `IVehiculeRepository` | `JpaRepository<Vehicule, Long>` | Idem — entité centrale du domaine, pagination utile pour les listes de véhicules. |
| `IEquipementRepository` | `JpaRepository<Equipement, Long>` | Idem — référentiel d'équipements, liste complète souvent nécessaire. |
| `IClientRepository` | `JpaRepository<Client, Long>` | Idem — recherches fréquentes, tri et pagination indispensables. |
| `IReservationRepository` | `JpaRepository<Reservation, Long>` | Idem — volume élevé, pagination et tri par date requis. |
| `IContratRepository` | `JpaRepository<Contrat, Long>` | CRUD complet ; `saveAndFlush` utile pour valider immédiatement un contrat en base. |
| `IPaiementRepository` | `JpaRepository<Paiement, Long>` | Lecture des paiements d'un contrat ; la création passe toujours par le `Contrat` parent. |
| `IMaintenanceRepository` | `JpaRepository<Maintenance, Long>` | Idem — suivi des maintenances par véhicule, tri par date utile. |

## Pourquoi JpaRepository plutôt que CrudRepository ?

- `JpaRepository` réunit `ListCrudRepository` + `ListPagingAndSortingRepository` + méthodes JPA spécifiques (`flush`, `saveAndFlush`, `getReferenceById`, suppressions en lot).
- `findAll()` renvoie une `List` (plus pratique que `Iterable`) sans effort supplémentaire.
- `getReferenceById(id)` permet de renseigner une clé étrangère sans charger l'entité entière.
- Pour AutoLoc, aucune interface ne doit être plus restrictive : `JpaRepository` est le choix retenu pour tous les repositories.

## Anomalies relevées avec SonarQube for IDE (SonarLint)

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| Package `Repository` avec R majuscule | Java naming convention : les packages doivent être en minuscules (`repository`). La déclaration `package tn.esprit.autoloc.repository;` est correcte dans le code ; seul le répertoire physique est limité par l'insensibilité à la casse de Windows. | Package déclaré en minuscules dans tous les fichiers sources. |
| Absence de `@Repository` sur les interfaces | Non requis avec Spring Data JPA : le proxy est généré automatiquement au démarrage. Ajouter `@Repository` serait du code mort. | Aucune annotation `@Repository` ajoutée — conforme à la règle « pas de code inutile ». |
| `@Data` sur une entité JPA (risque de boucle infinie) | `@Data` génère `equals`, `hashCode` et `toString` qui traversent toutes les associations, provoquant des boucles infinies sur les relations bidirectionnelles. | Utilisation de `@Getter` / `@Setter` ciblés sur toutes les entités du package `domain`. |
