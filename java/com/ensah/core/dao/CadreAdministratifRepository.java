package com.ensah.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ensah.core.bo.CadreAdministrateur;

@Repository
public interface CadreAdministratifRepository extends JpaRepository<CadreAdministrateur, Long> {

}
