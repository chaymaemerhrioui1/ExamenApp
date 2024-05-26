package com.ensah.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensah.core.bo.Enseignant;

public interface IEnseignantRepository extends JpaRepository<Enseignant, Long> {
    // Vous pouvez ajouter des méthodes de recherche personnalisées ici
}
