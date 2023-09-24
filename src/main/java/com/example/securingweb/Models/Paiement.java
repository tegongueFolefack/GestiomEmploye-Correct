package com.example.securingweb.Models;



import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.DTO.AdminDTO;
import com.example.securingweb.DTO.PaiementDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Paiement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private double total_heure;
	private Date date_paiement;
	private Date periode_paiement;
	private double precompte_professionnel;
	private double cotisation_onss;
	private double retenue_retraite;
	private double retenue_chaumage;
	private double retenu_total;
	private double onss_renum;
	private double suppl_transport;
	private double primes_hs;
	private double prime_prestation;
	private double prime_equipe;
	private double total_prime;
	private double total_brut;
	private double total_imposable;
	private double total_net;
	
	@ManyToOne
	private Admin admin;
	
	@ManyToOne
	private User utilisateur;
	
	@ManyToOne
	private Comptable comptable;
	
	

	public Paiement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public PaiementDTO toPaiementDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, PaiementDTO.class);
    }


	public Paiement(int id, double total_heure, Date date_paiement, Date periode_paiement,
			double precompte_professionnel, double cotisation_onss, double retenue_retraite, double retenue_chaumage,
			double retenu_total, double onss_renum, double suppl_transport, double primes_hs, double prime_prestation,
			double prime_equipe, double total_prime, double total_brut, double total_imposable, double total_net,
			Admin admin, User utilisateur, Comptable comptable) {
		super();
		this.id = id;
		this.total_heure = total_heure;
		this.date_paiement = date_paiement;
		this.periode_paiement = periode_paiement;
		this.precompte_professionnel = precompte_professionnel;
		this.cotisation_onss = cotisation_onss;
		this.retenue_retraite = retenue_retraite;
		this.retenue_chaumage = retenue_chaumage;
		this.retenu_total = retenu_total;
		this.onss_renum = onss_renum;
		this.suppl_transport = suppl_transport;
		this.primes_hs = primes_hs;
		this.prime_prestation = prime_prestation;
		this.prime_equipe = prime_equipe;
		this.total_prime = total_prime;
		this.total_brut = total_brut;
		this.total_imposable = total_imposable;
		this.total_net = total_net;
		this.admin = admin;
		this.utilisateur = utilisateur;
		this.comptable = comptable;
	}
	

}
