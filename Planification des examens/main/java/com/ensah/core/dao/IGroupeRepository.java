package com.ensah.core.dao;

import com.ensah.core.bo.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ensah.core.bo.Filiere;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IGroupeRepository extends JpaRepository<Groupe, Long> {

    // Méthode pour trouver les groupes par département
    @Query("SELECT g FROM Groupe g WHERE g.departement.id = :departementId")
    List<Groupe> findGroupByDepartement(@Param("departementId") Long departementId);

    // Méthode pour trouver les groupes par filière
    @Query("SELECT g FROM Groupe g WHERE g.filiere.id = :filiereId")
    List<Groupe> findGroupByFiliere(@Param("filiereId") Long filiereId);
}

