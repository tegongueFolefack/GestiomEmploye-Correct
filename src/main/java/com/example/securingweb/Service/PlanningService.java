package com.example.securingweb.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.*;

import com.example.securingweb.Repository.*;


@Service
public class PlanningService {
	
	@Autowired
	private PlanningRepository planningRepository;
	
	@Autowired
	private UserRepository utilisateurRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private FonctionRepository fonctionRepository;
	
	@Autowired
	private TypePlanningRepository typePlanningRepository;
	
	
	public Iterable<Planning> getAllPlanning() {
		return planningRepository.findAll();
	}

	public boolean deletePlanning(Integer id) {
		Optional<Planning> PlanningOpt = getPlanningById(id);
		if (PlanningOpt.isPresent()) {
			planningRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Planning updatePlanning(Integer id, Planning PlanningOpt) {
	    Optional<Planning> Planning1 = planningRepository.findById(id);
	    
	    if (Planning1.isPresent()) {
	    	Planning Planning = Planning1.get();
	    	Planning.setDate_creation(PlanningOpt.getDate_creation());
	    	Planning.setDate_debut(PlanningOpt.getDate_debut());
	    	Planning.setDate_fin(PlanningOpt.getDate_fin());
	    	
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return planningRepository.save(Planning);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
	 public Planning savePlanning(Planning Planning) {
			return planningRepository.save(Planning);
		}
	
	 public Optional<Planning> getPlanningById(Integer id) {
			return planningRepository.findById(id);
			
		}
	 
	 
	 
	 public Planning ajouterUtilisateurAPlanning(Long utilisateurId, Integer planningId ) {
		 Optional<Planning> planningOpt = planningRepository.findById(planningId);
		 Optional<User> utilisateurOpt = utilisateurRepository.findById(utilisateurId);
		 
		 if(planningOpt.isPresent() && utilisateurOpt.isPresent()) {
			 Planning planning = planningOpt.get();
			 User utilisateur = utilisateurOpt.get();
			 
			 planning.setUtilisateur(utilisateur);
			 return planningRepository.save(planning);	 
		 }else {
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "planing or utilisateur not found");
			 
		 }
		 
	 }
	 
	 public Planning ajouterAdminAPlanning(Long adminId, Integer planningId ) {
		 Optional<Planning> planningOpt = planningRepository.findById(planningId);
		 Optional<Admin> adminOpt = adminRepository.findById(adminId);
		 
		 if(planningOpt.isPresent() && adminOpt.isPresent()) {
			 Planning planning = planningOpt.get();
			 Admin admin = adminOpt.get();
			 
			 planning.setAdmin(admin);
			 return planningRepository.save(planning);	 
		 }else {
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "planing or ADMIN not found");
			 
		 }
		 
	 }
	 
	 public Planning ajouterFonctionAPlanning(Integer fonctionId, Integer planningId ) {
		 Optional<Planning> planningOpt = planningRepository.findById(planningId);
		 Optional<Fonction> fonctionOpt = fonctionRepository.findById(fonctionId);
		 
		 if(planningOpt.isPresent() && fonctionOpt.isPresent()) {
			 Planning planning = planningOpt.get();
			 Fonction fonction = fonctionOpt.get();
			 
			 planning.setFonction(fonction);
			 return planningRepository.save(planning);	 
		 }else {
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "planing or ADMIN not found");
			 
		 }
		 
	 }
	 
	 public Planning ajouterTypePlanningAPlanning(Integer typePlanningId, Integer planningId ) {
		 Optional<Planning> planningOpt = planningRepository.findById(planningId);
		 Optional<TypePlanning> typePlanningOpt = typePlanningRepository.findById(typePlanningId);
		 
		 if(planningOpt.isPresent() && typePlanningOpt.isPresent()) {
			 Planning planning = planningOpt.get();
			 TypePlanning typePlanning = typePlanningOpt.get();
			 
			 planning.setTypePlanning(typePlanning);
			 return planningRepository.save(planning);	 
		 }else {
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "planing or ADMIN not found");
			 
		 }
		 
	 }
	
 
}
