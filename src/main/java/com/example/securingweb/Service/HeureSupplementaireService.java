package com.example.securingweb.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.HeureSupplementaire;

import com.example.securingweb.Models.*;
import com.example.securingweb.Repository.HeureSupplementaireRepository;
import com.example.securingweb.Repository.*;

@Service
public class HeureSupplementaireService {
	
	@Autowired
	private HeureSupplementaireRepository heure_SupplementaireRepository;
	
	@Autowired
	private UserRepository utilisateurRepository;
	
	public Iterable<HeureSupplementaire> getAllHeure_Supplementaire() {
		return heure_SupplementaireRepository.findAll();
	}

	public boolean deleteHeure_Supplementaire(Integer id) {
		Optional<HeureSupplementaire> RoleOpt = getHeure_SupplementaireById(id);
		if (RoleOpt.isPresent()) {
			heure_SupplementaireRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public HeureSupplementaire updateHeure_Supplementaire(Integer id, HeureSupplementaire Heure_SupplementaireOpt) {
	    Optional<HeureSupplementaire> Heure_Supplementaire1 = heure_SupplementaireRepository.findById(id);
	    
	    if (Heure_Supplementaire1.isPresent()) {
	    	HeureSupplementaire Heure_Supplementaire = Heure_Supplementaire1.get();
	    	Heure_Supplementaire.setDate(Heure_SupplementaireOpt.getDate());
	    	Heure_Supplementaire.setNombreHeures(Heure_SupplementaireOpt.getNombreHeures());
	    	Heure_Supplementaire.setValide(Heure_SupplementaireOpt.isValide());
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return heure_SupplementaireRepository.save(Heure_Supplementaire);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}
	
	public HeureSupplementaire ajouterUtilisateurAHeure_Supplementaire(Long utilisateurId, Integer heureSupplementaireId) {
	    // Récupérer l'objet Heure_Supplementaire par son ID
	   HeureSupplementaire heureSupplementaire = heure_SupplementaireRepository.findById(heureSupplementaireId)
	      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Heure_Supplementaire not found"));

	    // Récupérer l'objet Utilisateur par son ID
	    User utilisateur = utilisateurRepository.findById(utilisateurId)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found"));

	    // Associer l'utilisateur à l'objet Heure_Supplementaire (implémentez la logique appropriée)
	    heureSupplementaire.setUtilisateur(utilisateur);

	    // Enregistrer les modifications dans la base de données
	    return heure_SupplementaireRepository.save(heureSupplementaire);
	}


 
 public HeureSupplementaire saveHeure_Supplementaire(HeureSupplementaire Heure_Supplementaire) {
		return heure_SupplementaireRepository.save(Heure_Supplementaire);
	}

 public Optional<HeureSupplementaire> getHeure_SupplementaireById(Integer id) {
		return heure_SupplementaireRepository.findById(id);
		
	}

}
