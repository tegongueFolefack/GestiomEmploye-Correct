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
import com.example.securingweb.Repository.ComptableRepository;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;




@Service
public class ComptableService {
	
	 @Autowired
	    private ComptableRepository comptableRepository;
	 
	 @Autowired
	 private  PasswordEncoder passwordEncoder;
	 
	 @Autowired
	  private  JwtService jwtService;
	 
	 @Autowired
	  private  AuthenticationManager authenticationManager;
	 
	 @Autowired
	  private  RefreshTokenService refreshTokenService;
	 

		public Optional<Comptable> getComptableById(Long id) {
			return comptableRepository.findById(id);
		}

		public Iterable<Comptable> getAllComptable() {
			return comptableRepository.findAll();
		}

		public boolean deleteComptable(Long id) {
			Optional<Comptable> comptableOpt = getComptableById(id);
			if (comptableOpt.isPresent()) {
				comptableRepository.deleteById(id);
				return true;
			} else {
				return false;
			}
		}
		
		public Comptable updateComptable(Long id, Comptable comptable2) {
		    Optional<Comptable> comptableOpt = comptableRepository.findById(id);
		    
		    if (comptableOpt.isPresent()) {
		        Comptable comptable = comptableOpt.get();
		        
		        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
		        comptable.setPassword(comptable2.getPassword());
		        comptable.setNom(comptable2.getNom());
		        comptable.setPrenom(comptable2.getPrenom());
		        comptable.setEmail(comptable2.getEmail());
//		        comptable.setLogin(comptable2.getLogin());
//		        comptable.setDate_creation(comptable2.getDate_creation());
//		        comptable.setGenre(comptable2.getGenre());
//		        comptable.setEtat_Civil(comptable2.getEtat_Civil());
//		        comptable.setTelephone(comptable2.getTelephone());
//		        comptable.setMatricule(comptable2.getMatricule());
//		       
//		        comptable.setCompteIBAN(comptable2.getCompteIBAN());
//		        comptable.setAddresse(comptable2.getAddresse());
//		        		        
		        // Sauvegarder les modifications dans la base de données
		        return comptableRepository.save(comptable);
		    } else {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		    }
		}

	 
		public AuthenticationResponse register(RegisterRequest request) {
			  Comptable comptable = new Comptable(
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
			            Role.COMPTABLE 
			        );
		       
				
			  comptable = comptableRepository.save(comptable);
		        var jwt = jwtService.generateToken(comptable);
		        var refreshToken = refreshTokenService.createRefreshToken(comptable.getId());

		        var roles = comptable.getRole().getAuthorities()
		                .stream()
		                .map(SimpleGrantedAuthority::getAuthority)
		                .toList();

		        return AuthenticationResponse.builder()
		                .accessToken(jwt)
		                .email(comptable.getEmail())
		                .id(comptable.getId())
		                .refreshToken(refreshToken.getToken())
		                .roles(roles)
		                .tokenType( TokenType.BEARER.name())
		                .build();
		    }
		   
		  public AuthenticationResponse authenticate(AuthenticationRequest request) {
			    authenticationManager.authenticate(
			            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			    // Utilisez .orElseThrow() pour gérer l'Optional
			    Comptable comptable = comptableRepository.findByEmail(request.getEmail())
			            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
			    
			    // Récupérez les rôles de l'administrateur
			    var roles = comptable.getRole().getAuthorities()
			            .stream()
			            .map(SimpleGrantedAuthority::getAuthority)
			            .toList();

			    // Générez le jeton JWT et le jeton de rafraîchissement
			    var jwt = jwtService.generateToken(comptable);
			    var refreshToken = refreshTokenService.createRefreshToken(comptable.getId());

			    return AuthenticationResponse.builder()
			            .accessToken(jwt)
			            .roles(roles)
			            .email(comptable.getEmail())
			            .id(comptable.getId())
			            .refreshToken(refreshToken.getToken())
			            .tokenType(TokenType.BEARER.name())
			            .build();
			}

}
