package com.example.securingweb.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Avantages;
import com.example.securingweb.Repository.AvantagesRepository;

@Service
public class AvantagesService {
	
	 @Autowired
	    private AvantagesRepository avantagesRepository;
	 

		public Optional<Avantages> getAvantagesById(Integer id) {
			return avantagesRepository.findById(id);
		}

		public Iterable<Avantages> getAllAvantages() {
			return avantagesRepository.findAll();
		}

		public boolean deleteAvantages(Integer id) {
			Optional<Avantages> avantagesOpt = getAvantagesById(id);
			if (avantagesOpt.isPresent()) {
				avantagesRepository.deleteById(id);
				return true;
			} else {
				return false;
			}
		}
		
		public Avantages updateAvantages(Integer id, Avantages avantages2) {
		    Optional<Avantages> avantagesOpt = avantagesRepository.findById(id);
		    
		    if (avantagesOpt.isPresent()) {
		    	Avantages avantages = avantagesOpt.get();
		        
		        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
		    	avantages.setType_avantage(avantages2.getType_avantage());
		    	
		        
		        // Sauvegarder les modifications dans la base de données
		        return avantagesRepository.save(avantages);
		    } else {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		    }
		}

	 
	 public Avantages saveAvantages(Avantages avantages) {
			return avantagesRepository.save(avantages);
		}

}
