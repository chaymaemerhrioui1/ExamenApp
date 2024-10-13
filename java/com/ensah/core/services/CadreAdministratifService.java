package com.ensah.core.services;

import com.ensah.core.bo.CadreAdministrateur;
import com.ensah.core.dao.CadreAdministratifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadreAdministratifService {

    @Autowired
    private CadreAdministratifRepository cadreAdministratifRepository;

    public List<CadreAdministrateur> getAllCadresAdministratifs() {
        return cadreAdministratifRepository.findAll();
    }
}
