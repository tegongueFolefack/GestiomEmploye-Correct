package com.example.securingweb.Service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Enums.Role;
import com.example.securingweb.Enums.TokenType;
import com.example.securingweb.Models.Admin;
import com.example.securingweb.Models.Comptable;
import com.example.securingweb.Models.Employe;
import com.example.securingweb.Repository.ComptableRepository;
import com.example.securingweb.Repository.EmployeRepository;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;

@Service
public class EmployeService {
	
	
		
		 @Autowired
		    private EmployeRepository employeRepository;
		 
		 @Autowired
		 private  PasswordEncoder passwordEncoder;
		 
		 @Autowired
		  private  JwtService jwtService;
		 
		 
		 
		 @Autowired
		  private  AuthenticationManager authenticationManager;
		 
		 @Autowired
		  private  RefreshTokenService refreshTokenService;
		 

			

			public Iterable<Employe> getAllEmploye() {
				return employeRepository.findAll();
			}

			public boolean deleteEmploye(Long id) {
				Optional<Employe> comptableOpt = getEmployeById(id);
				if (comptableOpt.isPresent()) {
					employeRepository.deleteById(id);
					return true;
				} else {
					return false;
				}
			}
			
			public Employe updateEmploye(Long id, Employe employeOpt) {
			    Optional<Employe> employe1 = employeRepository.findById(id);
			    
			    if (employe1.isPresent()) {
			    	Employe employe = employe1.get();
			    	employe.setPassword(employeOpt.getPassword());
			    	employe.setEmail(employeOpt.getEmail());
			    	employe.setNom(employeOpt.getNom());
			    	employe.setPrenom(employeOpt.getPrenom());
//			    	employe.setLogin(employeOpt.getLogin());
//			    	employe.setDate_creation(employeOpt.getDate_creation());
//			    	employe.setGenre(employeOpt.getGenre());
//			    	employe.setEtat_Civil(employeOpt.getEtat_Civil());
//			    	employe.setTelephone(employeOpt.getTelephone());
//			    	employe.setMatricule(employeOpt.getMatricule());
//			    	
//			    	employe.setCompteIBAN(employeOpt.getCompteIBAN());
//			    	employe.setAddresse(employeOpt.getAddresse());
			    	
			        
			        // Sauvegarder les modifications dans la base de données
			        return employeRepository.save(employe);
			    } else {
			        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
			    }
			}

		 
		 public AuthenticationResponse register(RegisterRequest request) {
				  Employe employe = new Employe(
				            null, // Laissez l'ID généré automatiquement
				            request.getNom(),
				            request.getPrenom(),
				            request.getEmail(),
				            passwordEncoder.encode(request.getPassword()),
				            request.getLogin(),
				            request.getDate_creation(),
				            request.getGenre(),
				            request.getEtat_Civil(),
				            request.getTelephone(),
				            request.getMatricule(),
				            request.getCompteIBAN(),
				            request.getAddresse(),
				            0.0, 
				            0.0,
				            0,
				            false,
				            new ArrayList<>(), 
				            null, 
				            new ArrayList<>(), 
				            Role.EMPLOYE 
				        );
			       
					
				  employe = employeRepository.save(employe);
			        var jwt = jwtService.generateToken(employe);
			        var refreshToken = refreshTokenService.createRefreshToken(employe.getId());

			        var roles = employe.getRole().getAuthorities()
			                .stream()
			                .map(SimpleGrantedAuthority::getAuthority)
			                .toList();

			        return AuthenticationResponse.builder()
			                .accessToken(jwt)
			                .email(employe.getEmail())
			                .id(employe.getId())
			                .refreshToken(refreshToken.getToken())
			                .roles(roles)
			                .tokenType( TokenType.BEARER.name())
			                .build();
			    }
			   
			  public AuthenticationResponse authenticate(AuthenticationRequest request) {
				    authenticationManager.authenticate(
				            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

				    // Utilisez .orElseThrow() pour gérer l'Optional
				    Employe employe = employeRepository.findByEmail(request.getEmail())
				            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
				    
				    // Récupérez les rôles de l'administrateur
				    var roles = employe.getRole().getAuthorities()
				            .stream()
				            .map(SimpleGrantedAuthority::getAuthority)
				            .toList();

				    // Générez le jeton JWT et le jeton de rafraîchissement
				    var jwt = jwtService.generateToken(employe);
				    var refreshToken = refreshTokenService.createRefreshToken(employe.getId());

				    return AuthenticationResponse.builder()
				            .accessToken(jwt)
				            .roles(roles)
				            .email(employe.getEmail())
				            .id(employe.getId())
				            .refreshToken(refreshToken.getToken())
				            .tokenType(TokenType.BEARER.name())
				            .build();
				}


		 public Optional<Employe> getEmployeById(Long id) {
				return employeRepository.findById(id);
				
			}
}

	

	

	

	
	
	
