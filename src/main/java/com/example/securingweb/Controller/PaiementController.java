package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

import com.example.securingweb.DTO.DepartementDTO;
import com.example.securingweb.DTO.PaiementDTO;
import com.example.securingweb.Models.Departement;
import com.example.securingweb.Models.Paiement;
import com.example.securingweb.Models.User;
import com.example.securingweb.Repository.UserRepository;
import com.example.securingweb.Service.PaiementService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;



@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@RestController
@RequestMapping("/paiement")
@SecurityRequirements() 
public class PaiementController {
	
	@Autowired
	private PaiementService paiementService;
	
	@Autowired
	private UserRepository utilisateurRepository;
	

		@GetMapping("/{id}")
	    public ResponseEntity<PaiementDTO> getEmployeById(@PathVariable Integer id) {
	        try {
	            Optional<Paiement> employe = paiementService.getPaiementById(id);
	            if (employe.isPresent()) {
	            	PaiementDTO employeDTO = employe.get().toPaiementDTO();
	                return ResponseEntity.ok(employeDTO);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comptable not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
		
		 

	    @DeleteMapping("delete/{id}")
	    public ResponseEntity<Void> deletePaiement(@PathVariable Integer id) {
	        try {
	        	paiementService.deletePaiement(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @GetMapping("/")
	    public ResponseEntity<List<PaiementDTO>> findAll() {
	        try {
	            Iterable<Paiement> employes = paiementService.getAllPaiement();
	            List<PaiementDTO> employeDTOs = new ArrayList<>();
	            for (Paiement employe : employes) {
	            	employeDTOs.add(employe.toPaiementDTO());
	            }
	            return ResponseEntity.ok(employeDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

		   

		
	    @PostMapping("add/")
	    public ResponseEntity<String> addPaiement(@RequestBody PaiementDTO employeDTOs) {
	    	Paiement employe = employeDTOs.toPaiement();
	    	Paiement savedEmploye = paiementService.savePaiement(employe);
	        
	        // You can customize the confirmation message here
	        String confirmationMessage = "Paiement with ID " + savedEmploye.getId() + " has been added successfully.";
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
	    }

		
		    @PutMapping("update/{id}")
		    public ResponseEntity<PaiementDTO> updatePaiement(@PathVariable Integer id, @RequestBody PaiementDTO employeDto) {
		        try {
		            Optional<Paiement> employeOpt = paiementService.getPaiementById(id);
		            if (employeOpt.isPresent()) {
		            	Paiement employe = employeOpt.get();
		                
		            	employe = employeDto.toPaiement();
		                
		            	employe = paiementService.updatPaiement(id, employe);
		                
		            	PaiementDTO employeResponse = employe.toPaiementDTO();
		                
		                return ResponseEntity.ok(employeResponse);
		            } else {
		                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		            }
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }
		    
		    @PostMapping("/{paiementId}/admin/{adminId}")
		    public ResponseEntity<PaiementDTO> ajouterAdminAPaiement(@PathVariable Long adminId, @PathVariable Integer paiementId) {
		        try {
		            Paiement paiement = paiementService.ajouterAdminAPaiement(adminId, paiementId);
		            PaiementDTO PaiementDTO = paiement.toPaiementDTO();
		            return ResponseEntity.ok(PaiementDTO);
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }
		    
		    @PostMapping("/{paiementId}/utilisateur/{utilisateurId}")
		    public ResponseEntity<PaiementDTO> ajouterUtilisateurAPaiement(@PathVariable Long utilisateurId, @PathVariable Integer paiementId) {
		        try {
		            Paiement paiement = paiementService.ajouterUtilisateurAPaiement(utilisateurId, paiementId);
		            PaiementDTO PaiementDTO = paiement.toPaiementDTO();
		            return ResponseEntity.ok(PaiementDTO);
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }
		    
		    @PostMapping("/{paiementId}/comptable/{comptableId}")
		    public ResponseEntity<PaiementDTO> ajouterComptableAPaiement(@PathVariable Long comptableId, @PathVariable Integer paiementId) {
		        try {
		            Paiement paiement = paiementService.ajouterComptableAPaiement(comptableId, paiementId);
		            PaiementDTO PaiementDTO = paiement.toPaiementDTO();
		            return ResponseEntity.ok(PaiementDTO);
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }
		    
//		    @GetMapping("/salaireBase/{utilisateurId}")
//		    public ResponseEntity<Double> getSalaireBase(@PathVariable Long utilisateurId) {
//		        try {
//		            User utilisateur = utilisateurRepository.findById(utilisateurId)
//		                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found"));
//
//		            double salaireBase = paiementService.salaireBase(utilisateur);
//		            return ResponseEntity.ok(salaireBase);
//		        } catch (Exception e) {
//		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
//		        }
//		    }
//		    
//		    @GetMapping("/sursalaire/{utilisateurId}/{heuresTravaillees}")
//		    public ResponseEntity<Double> getSursalaireJourFer(
//		            @PathVariable Long utilisateurId,
//		            @PathVariable int heuresTravaillees) {
//		    	try {
//		    	
//			    	User utilisateur = utilisateurRepository.findById(utilisateurId)
//			                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found"));
//	
//			        double sursalaire = paiementService.Sursalaire_jour_fer(utilisateur, heuresTravaillees);
//			        return ResponseEntity.ok(sursalaire);
//		    	 } catch (Exception e) {
//			            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
//			        }
//		    }
//		    
//		    @GetMapping("/heuresSupplementaires/{utilisateurId}")
//		    public ResponseEntity<Integer> getNbHeuresSupplementaires(@PathVariable Long utilisateurId) {
//		    	try {
//			    	Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
//			                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur not found"));
//	
//	
//			        int nbHeuresSupplementaires = paiementService.nbHS(utilisateur);
//			        return ResponseEntity.ok(nbHeuresSupplementaires);
//		    	} catch (Exception e) {
//		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
//		        }
//		    }
		    
		    	
		    @ExceptionHandler(ResponseStatusException.class)
		    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
		        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
		    }

		    @ExceptionHandler(Exception.class)
		    public ResponseEntity<String> handleException(Exception ex) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		    }


}
