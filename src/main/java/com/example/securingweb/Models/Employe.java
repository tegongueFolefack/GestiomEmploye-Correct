package com.example.securingweb.Models;



import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;

import com.example.securingweb.DTO.EmployeDTO;
import com.example.securingweb.Enums.Role;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "employe")
@DiscriminatorValue("employe")
public class Employe extends User {
	

	private static final long serialVersionUID = 1L;


	public Employe() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	public EmployeDTO toEmployeDTO() {
	        ModelMapper modelMapper = new ModelMapper();
	        return modelMapper.map(this, EmployeDTO.class);
	    }




	public Employe(Long id, String nom, String prenom, String email, String password, String login, Date date_creation,
			String genre, String etat_Civil, int telephone, int matricule, String compteIBAN, String addresse,
			double salaireBase, double tauxHoraire, int heuresTravailFixes, boolean transportPrive,
			List<Paiement> paiement, Departement departement, List<com.example.securingweb.Models.Planning> Planning,
			Role role) {
		super(id, nom, prenom, email, password, login, date_creation, genre, etat_Civil, telephone, matricule, compteIBAN,
				addresse, salaireBase, tauxHoraire, heuresTravailFixes, transportPrive, paiement, departement, Planning, role);
		// TODO Auto-generated constructor stub
	}



}
