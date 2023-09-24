package com.example.securingweb.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import com.example.securingweb.DTO.ComptableDTO;
import com.example.securingweb.Enums.Role;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "comptable")
@DiscriminatorValue("comptable")
public class Comptable extends User{
	
	 
	    

	private static final long serialVersionUID = 1L;
	@OneToMany
	private Collection<Paiement> paiement =new ArrayList<>();
	
	
	public Comptable() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ComptableDTO toComptableDTO() {
	        ModelMapper modelMapper = new ModelMapper();
	        return modelMapper.map(this, ComptableDTO.class);
	    }









	public Comptable(Long id, String nom, String prenom, String email, String password, String login,
			Date date_creation, String genre, String etat_Civil, int telephone, int matricule, String compteIBAN,
			String addresse, double salaireBase, double tauxHoraire, int heuresTravailFixes, boolean transportPrive,
			List<Paiement> paiement, Departement departement, List<com.example.securingweb.Models.Planning> Planning,
			Role role) {
		super(id, nom, prenom, email, password, login, date_creation, genre, etat_Civil, telephone, matricule, compteIBAN,
				addresse, salaireBase, tauxHoraire, heuresTravailFixes, transportPrive, paiement, departement, Planning, role);
		// TODO Auto-generated constructor stub
	}
	 
	 
	
	
	
	
}
