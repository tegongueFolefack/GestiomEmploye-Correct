package com.example.securingweb.Models;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;


import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;

import com.example.securingweb.DTO.AdminDTO;
import com.example.securingweb.Enums.Role;
import com.example.securingweb.Request.RegisterRequest;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;








@Entity
@Table(name = "admin")
@DiscriminatorValue("admin")


public class Admin extends User{

	

	public Admin(Long id, String nom, String prenom, String email, String password, String login, Date date_creation,
			String genre, String etat_Civil, int telephone, int matricule, String compteIBAN, String addresse,
			double salaireBase, double tauxHoraire, int heuresTravailFixes, boolean transportPrive,
			List<Paiement> paiement, Departement departement, List<com.example.securingweb.Models.Planning> Planning,
			Role role) {
		super(id, nom, prenom, email, password, login, date_creation, genre, etat_Civil, telephone, matricule, compteIBAN,
				addresse, salaireBase, tauxHoraire, heuresTravailFixes, transportPrive, paiement, departement, Planning, role);
		// TODO Auto-generated constructor stub
	}





	private static final long serialVersionUID = 1L;

	@OneToMany (mappedBy="admin")
	@Builder.Default
	private List<Paiement> paiement =new ArrayList<>();
	
	@OneToMany(mappedBy = "admin")
	@Builder.Default
	private List<Departement>departement = new ArrayList<>();
	
	@OneToMany(mappedBy = "admin")
	@Builder.Default
	private List<Planning>planning = new ArrayList<>();
	
	
	
	

	public AdminDTO toAdminDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, AdminDTO.class);
    }





	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}










	




	


	


	

	

	
	

}
