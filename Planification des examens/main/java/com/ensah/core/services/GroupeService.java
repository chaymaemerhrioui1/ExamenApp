package com.ensah.core.services;

import com.ensah.core.bo.Groupe;
import com.ensah.core.dao.IGroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupeService {

    @Autowired
    private IGroupeRepository groupeRepository;

    public List<Groupe> getGroupsByDepartement(Long departementId) {
        return groupeRepository.findGroupByDepartement(departementId);
    }

    public List<Groupe> getGroupsByFiliere(Long filiereId) {
        return groupeRepository.findGroupByFiliere(filiereId);
    }
}
