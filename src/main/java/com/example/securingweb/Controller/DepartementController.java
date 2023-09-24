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

import com.example.securingweb.DTO.*;
import com.example.securingweb.Models.*;
import com.example.securingweb.Repository.FonctionRepository;
import com.example.securingweb.Service.DepartementService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")

@SecurityRequirements()
@RestController
@RequestMapping("/departement")
public class DepartementController {
	
	@Autowired
	private DepartementService departementService;
	
	@Autowired
	private FonctionRepository fonctionService;
	
	@GetMapping("/{id}")
    public ResponseEntity<DepartementDTO> getDepartementById(@PathVariable Integer id) {
        try {
            Optional<Departement> Departement = departementService.getDepartementById(id);
            if (Departement.isPresent()) {
            	DepartementDTO DepartementDTO = Departement.get().toDepartementDTO();
                return ResponseEntity.ok(DepartementDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "admin not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable Integer id) {
        try {
        	departementService.deleteDepartement(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<DepartementDTO>> findAll() {
        try {
            Iterable<Departement> Departements = departementService.getAllDepartement();
            List<DepartementDTO> DepartementDTOs = new ArrayList<>();
            for (Departement Departement : Departements) {
            	DepartementDTOs.add(Departement.toDepartementDTO());
            }
            return ResponseEntity.ok(DepartementDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("add/")
    public ResponseEntity<String> addDepartement(@RequestBody DepartementDTO DepartementDto) {
    	Departement Departement =DepartementDto.toDepartement();
    	Departement savedDepartement = departementService.saveDepartement(Departement);
        
        // You can customize the confirmation message here
        String confirmationMessage = "Departement with ID " + savedDepartement.getId() + " has been added successfully.";
        
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
    }

	

    @PutMapping("update/{id}")
    public ResponseEntity<DepartementDTO> updateDepartement(@PathVariable Integer id, @RequestBody DepartementDTO DepartementDto) {
        try {
            Optional<Departement> DepartementOpt = departementService.getDepartementById(id);
            if (DepartementOpt.isPresent()) {
            	Departement Departement = DepartementOpt.get();
                
            	Departement = DepartementDto.toDepartement();
                
            	Departement = departementService.updateDepartement(id, Departement);
                
            	DepartementDTO DepartementResponse = Departement.toDepartementDTO();
                
                return ResponseEntity.ok(DepartementResponse);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }
    
    
    @PostMapping("/{departementId}/fonction/{fonctionId}")
    public ResponseEntity<Departement> ajouterTypeDepartementAFonction( @PathVariable Integer fonctionId,
            @PathVariable Integer departementId
           ) {
        try {
        	Departement Departement = departementService.ajouterFonctionADepartement(fonctionId,departementId );
            return ResponseEntity.ok(Departement);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }
    @PostMapping("/{departementId}/admin/{adminId}")
    public ResponseEntity<DepartementDTO> ajouterAdminAUtilisateur(@PathVariable Long adminId, @PathVariable Integer departementId) {
        try {
            Departement departement = departementService.ajouterAdminADepartement(adminId, departementId);
            DepartementDTO departementDTO = departement.toDepartementDTO();
            return ResponseEntity.ok(departementDTO);
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
