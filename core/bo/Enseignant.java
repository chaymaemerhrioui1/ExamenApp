package com.ensah.core.bo;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
@Entity
@PrimaryKeyJoinColumn(name = "idEnseignant")
public class Enseignant extends Personne {

	private String specialite;

	@ManyToOne // Relation Many-to-One avec Filiere
	@JoinColumn(name = "idFiliere")
	private Filiere filiere;

	@ManyToOne
	@JoinColumn(name = "idDepartement")
	private Departement departement;
	@ManyToMany
	@JoinTable(
			name = "Enseignant_Filiere",
			joinColumns = @JoinColumn(name = "idEnseignant"),
			inverseJoinColumns = @JoinColumn(name = "idFiliere")
	)
	private Set<Filiere> filieres;
	@ManyToMany(mappedBy = "enseignants")
	private List<Examen> examens;

	// Getters and setters for examens
	public List<Examen> getExamens() {
		return examens;
	}

	public void setExamens(List<Examen> examens) {
		this.examens = examens;
	}

	// Getters et Setters

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	public Set<Filiere> getFilieres() {
		return filieres;
	}

	public void setFilieres(Set<Filiere> filieres) {
		this.filieres = filieres;
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

	// Ajouter un getter pour idPersonne
	public Long getIdPersonne() {
		return super.getIdPersonne();
	}
}