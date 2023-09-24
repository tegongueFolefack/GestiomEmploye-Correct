package com.example.securingweb.Service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Departement;

import com.example.securingweb.Models.User;
import com.example.securingweb.Repository.DepartementRepository;

import com.example.securingweb.Repository.UserRepository;

@Service
public class UtilisateurService {

	
	 @Autowired
	    private UserRepository utilisateurRepository;
	 
	
	 
	 @Autowired DepartementRepository departementRepository;
	 

		public Optional<User> getUtilisateurById(Long id) {
			return utilisateurRepository.findById(id);
		}

		public Iterable<User> getAllUtilisateur() {
			return utilisateurRepository.findAll();
		}

		public boolean deleteUtilisateur(Long id) {
			Optional<User> utilisateurOpt = getUtilisateurById(id);
			if (utilisateurOpt.isPresent()) {
				utilisateurRepository.deleteById(id);
				return true;
			} else {
				return false;
			}
		}
		
		public User updateUtilisateur(Long id, User utilisateur2) {
		    Optional<User> utilisateurOpt = utilisateurRepository.findById(id);
		    
		    if (utilisateurOpt.isPresent()) {
		    	User utilisateur = utilisateurOpt.get();
		        
		        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
		    	utilisateur.setPassword(utilisateur2.getPassword());
		    	utilisateur.setNom(utilisateur2.getNom());
		    	utilisateur.setEmail(utilisateur2.getEmail());
//		    	utilisateur.setPrenom(utilisateur2.getPrenom());
//		    	utilisateur.setLogin(utilisateur2.getLogin());
//		    	utilisateur.setDate_creation(utilisateur2.getDate_creation());
//		    	utilisateur.setGenre(utilisateur2.getGenre());
//		    	utilisateur.setEtat_Civil(utilisateur2.getEtat_Civil());
//		    	utilisateur.setTelephone(utilisateur2.getTelephone());
//		    	utilisateur.setMatricule(utilisateur2.getMatricule());
//		    	
//		    	utilisateur.setCompteIBAN(utilisateur2.getCompteIBAN());
//		    	utilisateur.setAddresse(utilisateur2.getAddresse());
//		    	utilisateur.setNom(utilisateur2.getNom());
//		    	utilisateur.setPrenom(utilisateur2.getPrenom());
		        
		        // Sauvegarder les modifications dans la base de données
		        return utilisateurRepository.save(utilisateur);
		    } else {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		    }
		}

	 
	 public User saveUtilisateur(User utilisateur) {
			return utilisateurRepository.save(utilisateur);
		}
	 
	 
	 public User ajouterDepartementAUtilisateur(Long utilisateurId, Integer departementId) {
	        Optional<User> utilisateurOpt = utilisateurRepository.findById(utilisateurId);
	        Optional<Departement> departementOpt = departementRepository.findById(departementId);

	        if (utilisateurOpt.isPresent() && departementOpt.isPresent()) {
	        	User utilisateur = utilisateurOpt.get();
	            Departement departement = departementOpt.get();

	            utilisateur.setDepartement(departement);
	            return utilisateurRepository.save(utilisateur);
	        } else {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur or Departement not found");
	        }
	    }

//	 public Utilisateur ajouterRoleAUtilisateur(Long utilisateurId, Integer roleId) {
//	        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(utilisateurId);
//	        Optional<Role> roleOpt = roleRepository.findById(roleId);
//
//	        if (utilisateurOpt.isPresent() && roleOpt.isPresent()) {
//	            Utilisateur utilisateur = utilisateurOpt.get();
//	            Role role = roleOpt.get();
//
//	            utilisateur.setRole(role);
//	            return utilisateurRepository.save(utilisateur);
//	        } else {
//	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur or Role not found");
//	        }
//		}

		

}
