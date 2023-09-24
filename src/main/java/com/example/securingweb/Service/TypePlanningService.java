package com.example.securingweb.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import com.example.securingweb.Models.TypePlanning;
import com.example.securingweb.Repository.TypePlanningRepository;

@Service
public class TypePlanningService {
	
	@Autowired
	private TypePlanningRepository typeplanningRepository;
	
	public Iterable<TypePlanning> getAllTypePlanning() {
		return typeplanningRepository.findAll();
	}

	public boolean deleteTypePlanning(Integer id) {
		Optional<TypePlanning> TypePlanningOpt = getTypePlanningById(id);
		if (TypePlanningOpt.isPresent()) {
			typeplanningRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public TypePlanning updateTypePlanning(Integer id, TypePlanning TypePlanningOpt) {
	    Optional<TypePlanning> TypePlanning1 = typeplanningRepository.findById(id);
	    
	    if (TypePlanning1.isPresent()) {
	    	TypePlanning TypePlanning = TypePlanning1.get();
	    	TypePlanning.setNom_type(TypePlanningOpt.getNom_type());
	    	
	        
	        // Sauvegarder les modifications dans la base de données
	        return typeplanningRepository.save(TypePlanning);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	    }
	}

 
 public TypePlanning saveTypePlanning(TypePlanning TypePlanning) {
		return typeplanningRepository.save(TypePlanning);
	}

 public Optional<TypePlanning> getTypePlanningById(Integer id) {
		return typeplanningRepository.findById(id);
		
	}

}
