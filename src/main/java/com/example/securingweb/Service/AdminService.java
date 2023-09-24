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
import com.example.securingweb.Models.User;
import com.example.securingweb.Repository.AdminRepository;

import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;

@Service
public class AdminService {
	
	 @Autowired
	    private AdminRepository AdminRepository;
	 @Autowired
	 private  PasswordEncoder passwordEncoder;
	 
	 @Autowired
	  private  JwtService jwtService;
	 
	 
	 
	 @Autowired
	  private  AuthenticationManager authenticationManager;
	 
	 @Autowired
	  private  RefreshTokenService refreshTokenService;
	 

		public Optional<Admin> getAdminById(Long id) {
			return AdminRepository.findById(id);
		}

		public Iterable<Admin> getAllAdmin() {
			return AdminRepository.findAll();
		}

		public boolean deleteAdmin(Long id) {
			Optional<Admin> adminOpt = getAdminById(id);
			if (adminOpt.isPresent()) {
				AdminRepository.deleteById(id);
				return true;
			} else {
				return false;
			}
		}
		
		public Admin updateAdmin(Long id, Admin admin) {
		    Optional<Admin> adminOpt = AdminRepository.findById(id);
		    
		    if (adminOpt.isPresent()) {
		    	Admin admin1 = adminOpt.get();
		        
		        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
		    	admin1.setPassword(admin.getPassword());
		    	admin1.setNom(admin.getNom());
		    	admin1.setPrenom(admin.getPrenom());
		    	admin1.setEmail(admin.getEmail());
//		    	admin1.setLogin(admin.getLogin());
//		    	admin1.setDate_creation(admin.getDate_creation());
//		    	admin1.setGenre(admin.getGenre());
//		    	admin1.setEtat_Civil(admin.getEtat_Civil());
//		    	admin1.setTelephone(admin.getTelephone());
//		    	admin1.setMatricule(admin.getMatricule());
//		    	admin1.setCompteIBAN(admin.getCompteIBAN());
//		    	admin1.setAddresse(admin.getAddresse());
		    	
		        
		        // Sauvegarder les modifications dans la base de données
		        return AdminRepository.save(admin1);
		    } else {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		    }
		}

	 

	 
	  
	  
	
	  public AuthenticationResponse register(RegisterRequest request) {
		  Admin admin = new Admin(
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
		            Role.ADMIN 
		        );
	       
			
	        admin = AdminRepository.save(admin);
	        var jwt = jwtService.generateToken(admin);
	        var refreshToken = refreshTokenService.createRefreshToken(admin.getId());

	        var roles = admin.getRole().getAuthorities()
	                .stream()
	                .map(SimpleGrantedAuthority::getAuthority)
	                .toList();

	        return AuthenticationResponse.builder()
	                .accessToken(jwt)
	                .email(admin.getEmail())
	                .id(admin.getId())
	                .refreshToken(refreshToken.getToken())
	                .roles(roles)
	                .tokenType( TokenType.BEARER.name())
	                .build();
	    }
	   
	  public AuthenticationResponse authenticate(AuthenticationRequest request) {
		    authenticationManager.authenticate(
		            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		    // Utilisez .orElseThrow() pour gérer l'Optional
		    Admin admin = AdminRepository.findByEmail(request.getEmail())
		            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
		    
		    // Récupérez les rôles de l'administrateur
		    var roles = admin.getRole().getAuthorities()
		            .stream()
		            .map(SimpleGrantedAuthority::getAuthority)
		            .toList();

		    // Générez le jeton JWT et le jeton de rafraîchissement
		    var jwt = jwtService.generateToken(admin);
		    var refreshToken = refreshTokenService.createRefreshToken(admin.getId());

		    return AuthenticationResponse.builder()
		            .accessToken(jwt)
		            .roles(roles)
		            .email(admin.getEmail())
		            .id(admin.getId())
		            .refreshToken(refreshToken.getToken())
		            .tokenType(TokenType.BEARER.name())
		            .build();
		}


}
