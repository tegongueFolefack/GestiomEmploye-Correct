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

import com.example.securingweb.DTO.FonctionDTO;
import com.example.securingweb.Models.Fonction;
import com.example.securingweb.Service.FonctionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@SecurityRequirements() 
@RestController
@RequestMapping("/fonction")
public class FonctionController {
	
	@Autowired
	private FonctionService fonctionService;
	
	@GetMapping("/{id}")
    public ResponseEntity<FonctionDTO> getFonctionById(@PathVariable Integer id) {
        try {
            Optional<Fonction> fonction = fonctionService.getFonctionById(id);
            if (fonction.isPresent()) {
            	FonctionDTO fonctionDTO = fonction.get().toFonctionDTO();
                return ResponseEntity.ok(fonctionDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "fonction not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteFonction(@PathVariable Integer id) {
        try {
        	fonctionService.deleteFonction(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<FonctionDTO>> findAll() {
        try {
            Iterable<Fonction> fonctions = fonctionService.getAllFonction();
            List<FonctionDTO> fonctionDTOs = new ArrayList<>();
            for (Fonction fonction: fonctions) {
            	fonctionDTOs.add(fonction.toFonctionDTO());
            }
            return ResponseEntity.ok(fonctionDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("add/")
    public ResponseEntity<String> addFonction(@RequestBody FonctionDTO fonctionDto) {
        Fonction fonction = fonctionDto.toFonction();
        Fonction savedFonction = fonctionService.saveFonction(fonction);
        
        // You can customize the confirmation message here
        String confirmationMessage = "fonction with ID " + savedFonction.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<FonctionDTO> updateFonction(@PathVariable Integer id, @RequestBody FonctionDTO fonctionDto) {
	        try {
	            Optional<Fonction> fonctionOpt = fonctionService.getFonctionById(id);
	            if (fonctionOpt.isPresent()) {
	            	Fonction fonction = fonctionOpt.get();
	                
	            	fonction = fonctionDto.toFonction();
	                
	            	fonction = fonctionService.updatFonction(id, fonction);
	                
	                FonctionDTO fonctionResponse = fonction.toFonctionDTO();
	                
	                return ResponseEntity.ok(fonctionResponse);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    @PostMapping("/{fonctionId}/departement/{departementId}")
	    public ResponseEntity<Fonction> ajouterTypeDepartementAFonction( @PathVariable Integer fonctionId,
	            @PathVariable Integer departementId
	           ) {
	        try {
	            Fonction fonction = fonctionService.ajouterDepartementAFonction(fonctionId,departementId );
	            return ResponseEntity.ok(fonction);
	        } catch (ResponseStatusException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    @PostMapping("/{fonctionId}/avantages/{avanatagesId}")
	    public ResponseEntity<Fonction> ajouterAvantagesAFonction( @PathVariable Integer fonctionId,
	            @PathVariable Integer avanatagesId
	           ) {
	        try {
	            Fonction fonction = fonctionService.ajouterAvantagesAFonction(fonctionId,avanatagesId );
	            return ResponseEntity.ok(fonction);
	        } catch (ResponseStatusException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    	
	    @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
	        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleException(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	    }


}
