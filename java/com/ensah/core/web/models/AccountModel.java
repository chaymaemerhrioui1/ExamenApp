package com.ensah.core.web.models;

//Classe model utilisé dans la partie web
// pour recevoir les données de la vue 
//utilisée pour créer les comptes
//C'est une classe non persistante
public class AccountModel {
	
	private String username;
	
	private String password;
	
	private Long TypeId;
	
	private Long personId;
	
	
	public AccountModel() {
	}

	public AccountModel(Long personId) {
		this.personId = personId;
	}

	
	public AccountModel(Long TypeId, Long personId) {
		this.TypeId = TypeId;
		this.personId = personId;
	}

	public AccountModel(String username, String password, Long TypeId, Long personId) {
		this.username = username;
		this.password = password;
		this.TypeId = TypeId;
		this.personId = personId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getTypeId() {
		return TypeId;
	}

	public void setTypeId(Long TypeId) {
		this.TypeId = TypeId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	
	
	
}
