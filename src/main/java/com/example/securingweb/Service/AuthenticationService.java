package com.example.securingweb.Service;

import lombok.RequiredArgsConstructor;

import java.util.Date;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.securingweb.Enums.TokenType;
import com.example.securingweb.Models.User;
import com.example.securingweb.Repository.UserRepository;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;
 
 
 
@Service @Transactional
@RequiredArgsConstructor
public class AuthenticationService {
	
	 private final PasswordEncoder passwordEncoder;
	  private final JwtService jwtService;
	  private final UserRepository userRepository;
	  private final AuthenticationManager authenticationManager;
	  private final RefreshTokenService refreshTokenService;
	  
	  
	
	  public AuthenticationResponse register(RegisterRequest request) {
	        var user = User.builder()
	                .nom(request.getNom())
	                .prenom(request.getPrenom())
	                .email(request.getEmail())
	                .password(passwordEncoder.encode(request.getPassword()))
	                .login(request.getLogin())
	                .date_creation(request.getDate_creation())
	                .genre(request.getGenre())
	                .etat_Civil(request.getEtat_Civil())
	                .telephone(request.getTelephone())
	                .matricule(request.getMatricule())
	                .compteIBAN(request.getCompteIBAN())
	                .addresse(request.getAddresse())
	                .role(request.getRole())
	                .build();
	       
			
	        user = userRepository.save(user);
	        var jwt = jwtService.generateToken(user);
	        var refreshToken = refreshTokenService.createRefreshToken(user.getId());

	        var roles = user.getRole().getAuthorities()
	                .stream()
	                .map(SimpleGrantedAuthority::getAuthority)
	                .toList();

	        return AuthenticationResponse.builder()
	                .accessToken(jwt)
	                .email(user.getEmail())
	                .id(user.getId())
	                .refreshToken(refreshToken.getToken())
	                .roles(roles)
	                .tokenType( TokenType.BEARER.name())
	                .build();
	    }
	   
	  public AuthenticationResponse authenticate(AuthenticationRequest request) {
	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

	        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
	        var roles = user.getRole().getAuthorities()
	                .stream()
	                .map(SimpleGrantedAuthority::getAuthority)
	                .toList();
	        var jwt = jwtService.generateToken(user);
	        var refreshToken = refreshTokenService.createRefreshToken(user.getId());
	        return AuthenticationResponse.builder()
	                .accessToken(jwt)
	                .roles(roles)
	                .email(user.getEmail())
	                .id(user.getId())
	                .refreshToken(refreshToken.getToken())
	                .tokenType( TokenType.BEARER.name())
	                .build();
	    }
}
