package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.DTO.HeureSupplementaireDTO;
import com.example.securingweb.Models.HeureSupplementaire;
import com.example.securingweb.Service.HeureSupplementaireService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@SecurityRequirements() 
@RestController
@RequestMapping("/heureSupp")
public class HeureSupplementaireController {
	
	@Autowired
	private HeureSupplementaireService heure_SupplementaireService;
	
	@GetMapping("/{id}")
    public ResponseEntity<HeureSupplementaireDTO> getHeure_SupplementaireById(@PathVariable Integer id) {
        try {
            Optional<HeureSupplementaire> Heure_Supplementaire = heure_SupplementaireService.getHeure_SupplementaireById(id);
            if (Heure_Supplementaire.isPresent()) {
            	HeureSupplementaireDTO Heure_SupplementaireDTO = Heure_Supplementaire.get().toHeure_SupplementaireDTO();
                return ResponseEntity.ok(Heure_SupplementaireDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteHeure_Supplementaire(@PathVariable Integer id) {
        try {
        	heure_SupplementaireService.deleteHeure_Supplementaire(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<HeureSupplementaireDTO>> findAll() {
        try {
            Iterable<HeureSupplementaire> Heure_Supplementaires = heure_SupplementaireService.getAllHeure_Supplementaire();
            List<HeureSupplementaireDTO> Heure_SupplementaireDTOs = new ArrayList<>();
            for (HeureSupplementaire Heure_Supplementaire: Heure_Supplementaires) {
            	Heure_SupplementaireDTOs.add(Heure_Supplementaire.toHeure_SupplementaireDTO());
            }
            return ResponseEntity.ok(Heure_SupplementaireDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("add/")
    public ResponseEntity<String> addHeure_Supplementaire(@RequestBody HeureSupplementaireDTO Heure_SupplementaireDto) {
    	HeureSupplementaire Heure_Supplementaire = Heure_SupplementaireDto.toHeure_Supplementaire();
    	HeureSupplementaire savedHeure_Supplementaire = heure_SupplementaireService.saveHeure_Supplementaire(Heure_Supplementaire);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Heure_Supplementaire with ID " + savedHeure_Supplementaire.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<HeureSupplementaireDTO> updateFonction(@PathVariable Integer id, @RequestBody HeureSupplementaireDTO Heure_SupplementaireDto) {
	        try {
	            Optional<HeureSupplementaire> Heure_SupplementaireOpt = heure_SupplementaireService.getHeure_SupplementaireById(id);
	            if (Heure_SupplementaireOpt.isPresent()) {
	            	HeureSupplementaire Heure_Supplementaire = Heure_SupplementaireOpt.get();
	                
	            	Heure_Supplementaire = Heure_SupplementaireDto.toHeure_Supplementaire();
	                
	            	Heure_Supplementaire = heure_SupplementaireService.updateHeure_Supplementaire(id, Heure_Supplementaire);
	                
	            	HeureSupplementaireDTO Heure_SupplementaireResponse = Heure_Supplementaire.toHeure_SupplementaireDTO();
	                
	                return ResponseEntity.ok(Heure_SupplementaireResponse);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    @PostMapping("/{heureSupplementaireId}/utilisateur/{utilisateurId}")
	    public ResponseEntity<HeureSupplementaireDTO> ajouterUtilisateurAHeureSupplementaire( @PathVariable Long utilisateurId,@PathVariable Integer heureSupplementaireId) {
	        
	            HeureSupplementaire heureSupplementaire = heure_SupplementaireService.ajouterUtilisateurAHeure_Supplementaire(utilisateurId,heureSupplementaireId);
	            HeureSupplementaireDTO heureSupplementaireDTO = heureSupplementaire.toHeure_SupplementaireDTO();
	            return ResponseEntity.ok(heureSupplementaireDTO);
	       
	    }

	    
	    
	    	
	    @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
	        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleException(Exception ex) {
	        ex.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
	    }




}
