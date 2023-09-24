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

import com.example.securingweb.DTO.TypePlanningDTO;
import com.example.securingweb.Models.TypePlanning;
import com.example.securingweb.Service.TypePlanningService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@SecurityRequirements() 
@RestController
@RequestMapping("/typePlanning")
public class TypePlanningController {
	
	@Autowired
	private TypePlanningService typePlanningService;
	
	@GetMapping("/{id}")
    public ResponseEntity<TypePlanningDTO> getTypePlanningById(@PathVariable Integer id) {
        try {
            Optional<TypePlanning> TypePlanning = typePlanningService.getTypePlanningById(id);
            if (TypePlanning.isPresent()) {
            	TypePlanningDTO TypePlanningDTO = TypePlanning.get().toTypePlanningDTO();
                return ResponseEntity.ok(TypePlanningDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TypePlanning not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteTypePlanning(@PathVariable Integer id) {
        try {
        	typePlanningService.deleteTypePlanning(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<TypePlanningDTO>> findAll() {
        try {
            Iterable<TypePlanning> TypePlannings = typePlanningService.getAllTypePlanning();
            List<TypePlanningDTO> TypePlanningDTOs = new ArrayList<>();
            for (TypePlanning TypePlanning: TypePlannings) {
            	TypePlanningDTOs.add(TypePlanning.toTypePlanningDTO());
            }
            return ResponseEntity.ok(TypePlanningDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("add/")
    public ResponseEntity<String> addTypePlanning(@RequestBody TypePlanningDTO TypePlanningDto) {
    	TypePlanning TypePlanning = TypePlanningDto.toTypePlanning();
    	TypePlanning savedTypePlanning = typePlanningService.saveTypePlanning(TypePlanning);
        
        // You can customize the confirmation message here
        String confirmationMessage = "TypePlanning with ID " + savedTypePlanning.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<TypePlanningDTO> updateFonction(@PathVariable Integer id, @RequestBody TypePlanningDTO TypePlanningDto) {
	        try {
	            Optional<TypePlanning> TypePlanningOpt = typePlanningService.getTypePlanningById(id);
	            if (TypePlanningOpt.isPresent()) {
	            	TypePlanning TypePlanning =TypePlanningOpt.get();
	                
	            	TypePlanning = TypePlanningDto.toTypePlanning();
	                
	            	TypePlanning = typePlanningService.updateTypePlanning(id, TypePlanning);
	                
	            	TypePlanningDTO TypePlanningResponse = TypePlanning.toTypePlanningDTO();
	                
	                return ResponseEntity.ok(TypePlanningResponse);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	            }
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
