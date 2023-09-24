package com.example.securingweb.DTO;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.example.securingweb.Models.Paiement;

import lombok.Data;

@Data
public class PaiementDTO {

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
	
	
	public Paiement toPaiement() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Paiement.class);
    }
}
