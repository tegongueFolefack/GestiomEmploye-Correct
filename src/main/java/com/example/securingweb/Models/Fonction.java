package com.example.securingweb.Models;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.FonctionDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Data
@Entity
public class Fonction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int Id;
	
	private double heure_travail_semaine;
	private double salaire_heure;
	private String type_fonction;
	private double heureSupplementaire;
	
	@OneToMany
	private Collection<Planning> plannings = new ArrayList<>();
	
	@ManyToMany (mappedBy = "fonction")
	private Collection<Departement> departements = new ArrayList<>();
	
	@ManyToMany(fetch= FetchType.EAGER)
	private Collection<Avantages> avantages = new ArrayList<>();
	
	


	public Fonction() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FonctionDTO toFonctionDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, FonctionDTO.class);
    }

	public Fonction(int id, double heure_travail_semaine, double salaire_heure, String type_fonction) {
		super();
		Id = id;
		this.heure_travail_semaine = heure_travail_semaine;
		this.salaire_heure = salaire_heure;
		this.type_fonction = type_fonction;
	}
	
	
}
