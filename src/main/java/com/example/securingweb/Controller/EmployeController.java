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

import com.example.securingweb.DTO.EmployeDTO;
import com.example.securingweb.DTO.PlanningDTO;
import com.example.securingweb.Models.Employe;
import com.example.securingweb.Models.Planning;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RefreshTokenRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;
import com.example.securingweb.Response.RefreshTokenResponse;
import com.example.securingweb.Service.EmployeService;
import com.example.securingweb.Service.PlanningService;
import com.example.securingweb.Service.RefreshTokenService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@SecurityRequirements() 
@RestController
@RequestMapping("/employe")
public class EmployeController {
	
	@Autowired
	  private  RefreshTokenService refreshTokenService ;
	@Autowired
	  private  AuthenticationManager authenticationManager;
	
	@Autowired
	private EmployeService employeService;
	
	@Autowired
	private PlanningService planningService;
	

		@GetMapping("/{id}")
	    public ResponseEntity<EmployeDTO> getEmployeById(@PathVariable Long id) {
	        try {
	            Optional<Employe> employe = employeService.getEmployeById(id);
	            if (employe.isPresent()) {
	            	EmployeDTO employeDTO = employe.get().toEmployeDTO();
	                return ResponseEntity.ok(employeDTO);
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
	        	employeService.deleteEmploye(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

	    @GetMapping("/")
	    public ResponseEntity<List<EmployeDTO>> findAll() {
	        try {
	            Iterable<Employe> employes = employeService.getAllEmploye();
	            List<EmployeDTO> employeDTOs = new ArrayList<>();
	            for (Employe employe : employes) {
	            	employeDTOs.add(employe.toEmployeDTO());
	            }
	            return ResponseEntity.ok(employeDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

		   

		
	    @PostMapping("/register")
		  public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
		    return ResponseEntity.ok(employeService.register(request));
		 }
		 
		  @PostMapping("/authenticate")
		  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		    return ResponseEntity.ok(employeService.authenticate(request));
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
		    public ResponseEntity<EmployeDTO> updateEmploye(@PathVariable Long id, @RequestBody EmployeDTO employeDto) {
		        try {
		            Optional<Employe> employeOpt = employeService.getEmployeById(id);
		            if (employeOpt.isPresent()) {
		            	Employe employe = employeOpt.get();
		                
		            	employe = employeDto.toEmploye();
		                
		            	employe = employeService.updateEmploye(id, employe);
		                
		                EmployeDTO employeResponse = employe.toEmployeDTO();
		                
		                return ResponseEntity.ok(employeResponse);
		            } else {
		                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		            }
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }
		    
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
		    	
		    @ExceptionHandler(ResponseStatusException.class)
		    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
		        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
		    }

		    @ExceptionHandler(Exception.class)
		    public ResponseEntity<String> handleException(Exception ex) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		    }

	}



