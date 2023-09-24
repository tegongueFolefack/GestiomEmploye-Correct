package com.example.securingweb.Models;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;


import com.example.securingweb.DTO.HeureSupplementaireDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class HeureSupplementaire {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @ManyToOne
	    private User utilisateur;

	    private LocalDate date;
	    private int nombreHeures;
	    private boolean valide;
	    
	    public HeureSupplementaireDTO toHeure_SupplementaireDTO() {
	        ModelMapper modelMapper = new ModelMapper();
	        return modelMapper.map(this, HeureSupplementaireDTO.class);
	    }

}
