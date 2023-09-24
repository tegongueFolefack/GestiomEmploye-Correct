package com.example.securingweb.Models;



import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.FonctionDTO;
import com.example.securingweb.DTO.PlanningDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Planning {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int Id;
	
	private Date date_creation;
	private Date date_debut;
	private Date date_fin;
	
	@ManyToOne
	private User utilisateur;
	
	@ManyToOne
	private Admin admin;
	
	@ManyToOne
	private TypePlanning typePlanning;
	
	@ManyToOne
	private Fonction fonction;
	
	
	
	
	public Planning(Date date_creation, Date date_debut, Date date_fin) {
		super();
		this.date_creation = date_creation;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
	}


	public Planning() {
		super();
		// TODO Auto-generated constructor stub
	}
		
	public PlanningDTO toPlanningDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, PlanningDTO.class);
    }


	
}
