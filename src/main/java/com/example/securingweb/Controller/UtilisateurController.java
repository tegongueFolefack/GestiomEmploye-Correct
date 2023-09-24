package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
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

import com.example.securingweb.DTO.UserDTO;
import com.example.securingweb.Models.Departement;

import com.example.securingweb.Models.User;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;
import com.example.securingweb.Service.AuthenticationService;
import com.example.securingweb.Service.RefreshTokenService;
import com.example.securingweb.Service.UtilisateurService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
	
	@Autowired
	private UtilisateurService utilisateurService;
	
	@Autowired
	 private  AuthenticationService authenticationService;
	 
	

	   
	 
	 
	  @PostMapping("/register")
	  public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
	    return ResponseEntity.ok(authenticationService.register(request));
	 }
	 
	  @PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
	    return ResponseEntity.ok(authenticationService.authenticate(request));
	 }

	@GetMapping("/{id}")
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

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        try {
        	utilisateurService.deleteUtilisateur(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> findAll() {
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

	   

	
   

	
	    @PutMapping("update/{id}")
	    public ResponseEntity<UserDTO> updateUtilisateur(@PathVariable Long id, @RequestBody UserDTO utilisateurDto) {
	        try {
	            Optional<User> utilisateurOpt = utilisateurService.getUtilisateurById(id);
	            if (utilisateurOpt.isPresent()) {
	            	User utilisateur = utilisateurOpt.get();
	                
	            	utilisateur = utilisateurDto.toUtilisateur();
	                
	            	utilisateur = utilisateurService.updateUtilisateur(id, utilisateur);
	                
	            	UserDTO utilisateurResponse = utilisateur.toUtilisateurDTO();
	                
	                return ResponseEntity.ok(utilisateurResponse);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    @PostMapping("/{utilisateurId}/departements/{departementId}")
	    public ResponseEntity<UserDTO> ajouterDepartementAUtilisateur(@PathVariable Long utilisateurId, @PathVariable Integer departementId) {
	        try {
	        	User utilisateur = utilisateurService.ajouterDepartementAUtilisateur(utilisateurId, departementId);
	        	UserDTO utilisateurDTO = utilisateur.toUtilisateurDTO();
	            return ResponseEntity.ok(utilisateurDTO);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

//	    @PostMapping("/{utilisateurId}/roles/{roleId}")
//	    public ResponseEntity<UtilisateurDTO> ajouterRoleAUtilisateur(@PathVariable Long utilisateurId, @PathVariable Integer roleId) {
//	        try {
//	            Utilisateur utilisateur = utilisateurService.ajouterRoleAUtilisateur(utilisateurId, roleId);
//	            UtilisateurDTO utilisateurDTO = utilisateur.toUtilisateurDTO();
//	            return ResponseEntity.ok(utilisateurDTO);
//	        } catch (Exception e) {
//	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
//	        }
//	    }
	    	
	    @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
	        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleException(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	    }


}
