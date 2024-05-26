package com.ensah.core.bo;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Groupe")
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGroupe;

    private String nomGroupe;
    private String typeGroupe;

    @ManyToOne
    @JoinColumn(name = "idFiliere")
    private Filiere filiere;

    @ManyToOne
    @JoinColumn(name = "idDepartement")
    private Departement departement;

    @Column(name = "enseignants_ids")
    private String enseignantsIds;

    @ManyToMany
    @JoinTable(
            name = "Groupe_Enseignant",
            joinColumns = @JoinColumn(name = "idGroupe"),
            inverseJoinColumns = @JoinColumn(name = "idEnseignant")
    )
    private List<Enseignant> enseignants;

    // Getters, setters et constructeur


    public Long getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(Long idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getNomGroupe() {
        return nomGroupe;
    }

    public void setNomGroupe(String nomGroupe) {
        this.nomGroupe = nomGroupe;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Enseignant> getEnseignants() {
        return enseignants;
    }

    public String getTypeGroupe() {
        return typeGroupe;
    }
    public String getEnseignantsIds() {
        return enseignantsIds;
    }

    public void setEnseignantsIds(String enseignantsIds) {
        this.enseignantsIds = enseignantsIds;
    }

    public void setTypeGroupe(String typeGroupe) {
        this.typeGroupe = typeGroupe;
    }

    public void setEnseignants(List<Enseignant> enseignants) {
        this.enseignants = enseignants;
    }




}


