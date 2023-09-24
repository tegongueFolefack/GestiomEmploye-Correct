package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

import com.example.securingweb.DTO.AvantagesDTO;
import com.example.securingweb.DTO.ComptableDTO;
import com.example.securingweb.DTO.PaiementDTO;
import com.example.securingweb.DTO.PlanningDTO;
import com.example.securingweb.DTO.*;
import com.example.securingweb.Service.AvantagesService;
import com.example.securingweb.Service.ComptableService;
import com.example.securingweb.Service.PaiementService;
import com.example.securingweb.Service.PlanningService;
import com.example.securingweb.Service.RefreshTokenService;
import com.example.securingweb.Service.UtilisateurService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.example.securingweb.Models.*;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RefreshTokenRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;
import com.example.securingweb.Response.RefreshTokenResponse;


@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@SecurityRequirements() 
@RestController
@RequestMapping("/comptable")
public class ComptableController {
	
	@Autowired
	ComptableService comptableService;
	
	@Autowired
	  private  RefreshTokenService refreshTokenService ;
	@Autowired
	  private  AuthenticationManager authenticationManager;
	
	@Autowired
	private UtilisateurService utilisateurService;
	
	@Autowired
	private PlanningService planningService;
	
	@Autowired
	private AvantagesService avantagesService;
	
	@Autowired
	private PaiementService paiementService;

	@GetMapping("/{id}")
    public ResponseEntity<ComptableDTO> getComptableById(@PathVariable Long id) {
        try {
            Optional<Comptable> comptable = comptableService.getComptableById(id);
            if (comptable.isPresent()) {
                ComptableDTO comptableDTO = comptable.get().toComptableDTO();
                return ResponseEntity.ok(comptableDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comptable not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteComptable(@PathVariable Long id) {
        try {
            comptableService.deleteComptable(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ComptableDTO>> findAll() {
        try {
            Iterable<Comptable> comptables = comptableService.getAllComptable();
            List<ComptableDTO> comptableDTOs = new ArrayList<>();
            for (Comptable comptable : comptables) {
                comptableDTOs.add(comptable.toComptableDTO());
            }
            return ResponseEntity.ok(comptableDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   

	
    @PostMapping("/register")
	  public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
	    return ResponseEntity.ok(comptableService.register(request));
	 }
	 
	  @PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
	    return ResponseEntity.ok(comptableService.authenticate(request));
	 }
	  @PostMapping("/refresh-token")
	  public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
	    return ResponseEntity.ok(refreshTokenService.generateNewToken(request));
	 }
	  @GetMapping("/info")
	    public Authentication getAuthentication(@RequestBody AuthenticationRequest request){
	        return authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
	    }

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<ComptableDTO> updateComptable(@PathVariable Long id, @RequestBody ComptableDTO comptableDto) {
	        try {
	            Optional<Comptable> comptableOpt = comptableService.getComptableById(id);
	            if (comptableOpt.isPresent()) {
	                Comptable comptable = comptableOpt.get();
	                
	                comptable = comptableDto.toComptable();
	                
	                comptable = comptableService.updateComptable(id, comptable);
	                
	                ComptableDTO comptableResponse = comptable.toComptableDTO();
	                
	                return ResponseEntity.ok(comptableResponse);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
//api user
	    
	    @GetMapping("getUserById/{id}")
	    public ResponseEntity<UserDTO> getUtilisateurById(@PathVariable Long id) {
	        try {
	            Optional<User> utilisateur = utilisateurService.getUtilisateurById(id);
	            if (utilisateur.isPresent()) {
	            	UserDTO utilisateurDTO = utilisateur.get().toUtilisateurDTO();
	                return ResponseEntity.ok(utilisateurDTO);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    @GetMapping("getAllUser/")
	    public ResponseEntity<List<UserDTO>> findAllUser() {
	        try {
	            Iterable<User> utilisateurs = utilisateurService.getAllUtilisateur();
	            List<UserDTO> utilisateurDTOs = new ArrayList<>();
	            for (User utilisateur : utilisateurs) {
	            	utilisateurDTOs.add(utilisateur.toUtilisateurDTO());
	            }
	            return ResponseEntity.ok(utilisateurDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
//api planning
	    
	    @GetMapping("getPlanningById/{id}")
	    public ResponseEntity<PlanningDTO> getPlanningById(@PathVariable Integer id) {
	        try {
	            Optional<Planning> Planning = planningService.getPlanningById(id);
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
	    
	    @GetMapping("getAllPlanning/")
	    public ResponseEntity<List<PlanningDTO>> getAllPlanning() {
	        try {
	            Iterable<Planning> Plannings = planningService.getAllPlanning();
	            List<PlanningDTO> PlanningDTOs = new ArrayList<>();
	            for (Planning Planning: Plannings) {
	            	PlanningDTOs.add(Planning.toPlanningDTO());
	            }
	            return ResponseEntity.ok(PlanningDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
//api avantages 
	    
	    @GetMapping("getAvantagesById/{id}")
	    public ResponseEntity<AvantagesDTO> getAvantagesById(@PathVariable Integer id) {
	        try {
	            Optional<Avantages> avantages = avantagesService.getAvantagesById(id);
	            if (avantages.isPresent()) {
	            	AvantagesDTO avantagesDTO = avantages.get().toAvantagesDTO();
	                return ResponseEntity.ok(avantagesDTO);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "admin not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @DeleteMapping("deleteAvantages/{id}")
	    public ResponseEntity<Void> deleteAvantages(@PathVariable Integer id) {
	        try {
	        	avantagesService.deleteAvantages(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @GetMapping("getAllAvanatages/")
	    public ResponseEntity<List<AvantagesDTO>> findAllAvanatages() {
	        try {
	            Iterable<Avantages> admins = avantagesService.getAllAvantages();
	            List<AvantagesDTO> adminDTOs = new ArrayList<>();
	            for (Avantages admin : admins) {
	            	adminDTOs.add(admin.toAvantagesDTO());
	            }
	            return ResponseEntity.ok(adminDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

		   

		
	    @PostMapping("addAvanatages/")
	    public ResponseEntity<String> addAvantages(@RequestBody AvantagesDTO adminDto) {
	    	Avantages admin =adminDto.toAvantages();
	    	Avantages savedAvantages = avantagesService.saveAvantages(admin);
	        
	        // You can customize the confirmation message here
	        String confirmationMessage = "Comptable with ID " + savedAvantages.getId() + " has been added successfully.";
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
	    }

		

	    @PutMapping("updateAvanatages/{id}")
	    public ResponseEntity<AvantagesDTO> updateAvantages(@PathVariable Integer id, @RequestBody AvantagesDTO AvantagesDto) {
	        try {
	            Optional<Avantages> AvantagesOpt = avantagesService.getAvantagesById(id);
	            if (AvantagesOpt.isPresent()) {
	            	Avantages avantages = AvantagesOpt.get();
	                
	            	avantages = AvantagesDto.toAvantages();
	                
	            	avantages = avantagesService.updateAvantages(id, avantages);
	                
	            	AvantagesDTO avantagesResponse = avantages.toAvantagesDTO();
	                
	                return ResponseEntity.ok(avantagesResponse);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
//api paiement
	    
	    @PostMapping("add/")
	    public ResponseEntity<String> addPaiement(@RequestBody PaiementDTO employeDTOs) {
	    	Paiement employe = employeDTOs.toPaiement();
	    	Paiement savedEmploye = paiementService.savePaiement(employe);
	        
	        // You can customize the confirmation message here
	        String confirmationMessage = "Paiement with ID " + savedEmploye.getId() + " has been added successfully.";
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
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
