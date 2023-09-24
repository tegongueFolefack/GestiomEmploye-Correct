package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.securingweb.DTO.PlanningDTO;

import com.example.securingweb.DTO.UserDTO;
import com.example.securingweb.Models.Planning;

import com.example.securingweb.Models.User;
import com.example.securingweb.Service.PlanningService;
import com.example.securingweb.Service.UtilisateurService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@SecurityRequirements() 
@RestController
@RequestMapping("/planning")
public class PlanningController {
	
	@Autowired
	private PlanningService PlanningService;
	
	@Autowired
	private UtilisateurService utilisateurService;
	
	@GetMapping("/{id}")
    public ResponseEntity<PlanningDTO> getRoleById(@PathVariable Integer id) {
        try {
            Optional<Planning> Planning = PlanningService.getPlanningById(id);
            if (Planning.isPresent()) {
            	PlanningDTO PlanningDTO = Planning.get().toPlanningDTO();
                return ResponseEntity.ok(PlanningDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Planning not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deletePlanning(@PathVariable Integer id) {
        try {
        	PlanningService.deletePlanning(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<PlanningDTO>> findAll() {
        try {
            Iterable<Planning> Plannings = PlanningService.getAllPlanning();
            List<PlanningDTO> PlanningDTOs = new ArrayList<>();
            for (Planning Planning: Plannings) {
            	PlanningDTOs.add(Planning.toPlanningDTO());
            }
            return ResponseEntity.ok(PlanningDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("add/")
    public ResponseEntity<String> PlanningRole(@RequestBody PlanningDTO PlanningDto) {
    	Planning Planning = PlanningDto.toPlanning();
    	Planning savedPlanning = PlanningService.savePlanning(Planning);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Planning with ID " + savedPlanning.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<PlanningDTO> updatePlanning(@PathVariable Integer id, @RequestBody PlanningDTO PlanningDto) {
	        try {
	            Optional<Planning> PlanningOpt = PlanningService.getPlanningById(id);
	            if (PlanningOpt.isPresent()) {
	            	Planning Planning = PlanningOpt.get();
	                
	            	Planning =PlanningDto.toPlanning();
	                
	            	Planning = PlanningService.updatePlanning(id, Planning);
	                
	            	PlanningDTO PlanningResponse = Planning.toPlanningDTO();
	                
	                return ResponseEntity.ok(PlanningResponse);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    @PostMapping("/{planningId}/utilisateurs/{utilisateurId}")
	    public ResponseEntity<PlanningDTO> ajouterUtilisateurAPlanning( @PathVariable Long utilisateurId,@PathVariable  Integer planningId) {
	        try {
	            Planning Planning = PlanningService.ajouterUtilisateurAPlanning( utilisateurId,planningId);
	            PlanningDTO planningDTO = Planning.toPlanningDTO();
	            return ResponseEntity.ok(planningDTO);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	     
	    @PostMapping("/{planningId}/admins/{adminId}")
	    public ResponseEntity<PlanningDTO> ajouterAdminAPlanning( @PathVariable Long adminId,@PathVariable  Integer planningId) {
	        try {
	            Planning Planning = PlanningService.ajouterAdminAPlanning( adminId,planningId);
	            PlanningDTO planningDTO = Planning.toPlanningDTO();
	            return ResponseEntity.ok(planningDTO);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    @PostMapping("/{planningId}/fonctions/{fonctionId}")
	    public ResponseEntity<PlanningDTO> ajouterFonctionAPlanning( @PathVariable Integer fonctionId,@PathVariable  Integer planningId) {
	        try {
	            Planning Planning = PlanningService.ajouterFonctionAPlanning( fonctionId,planningId);
	            PlanningDTO planningDTO = Planning.toPlanningDTO();
	            return ResponseEntity.ok(planningDTO);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    @PostMapping("/{planningId}/typePlanning/{typePlanningId}")
	    public ResponseEntity<PlanningDTO> ajoutertypePlanningAPlanning( @PathVariable Integer typePlanningId,@PathVariable  Integer planningId) {
	        try {
	            Planning Planning = PlanningService.ajouterTypePlanningAPlanning( typePlanningId,planningId);
	            PlanningDTO planningDTO = Planning.toPlanningDTO();
	            return ResponseEntity.ok(planningDTO);
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
