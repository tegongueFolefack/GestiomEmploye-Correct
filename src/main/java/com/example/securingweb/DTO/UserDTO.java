package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.User;

import lombok.Data;

@Data
public class UserDTO {
	
	
	private String passWord;
	private String login;
	private Date date_creation;
	private String genre;
	private String etat_Civil;
	private int telephone;
	private int matricule;
	private String email;
	private String compteIBAN;
	private String addresse;
	private String nom;
	private String prenom;
	private double salaireBase;
	private double tauxHoraire;
	private int heuresTravailFixes;
	private boolean transportPrive;
	
	public User toUtilisateur() {
	    ModelMapper modelMapper = new ModelMapper();
	    return modelMapper.map(this, User.class);
	}
}

