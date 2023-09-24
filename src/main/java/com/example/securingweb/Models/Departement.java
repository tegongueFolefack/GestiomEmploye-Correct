package com.example.securingweb.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.DepartementDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Data
@Entity
public class Departement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private int id;
	
	private String nom_departement;

	@ManyToOne
	private Admin admin;
	
	@ManyToMany (fetch = FetchType.EAGER) 
	private Collection<Fonction>  fonction = new ArrayList<>();
	
	
	
	@OneToMany
	private Collection<User> utilisateur = new ArrayList<>();
	
	


	public Departement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DepartementDTO toDepartementDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, DepartementDTO.class);
    }

	public Departement(String nom_departement) {
		super();
		this.nom_departement = nom_departement;
	}
	
	

}
