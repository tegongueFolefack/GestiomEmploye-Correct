package com.example.securingweb.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.example.securingweb.Enums.TokenType;
import com.example.securingweb.Exception.TokenException;
import com.example.securingweb.Models.RefreshToken;
import com.example.securingweb.Models.User;
import com.example.securingweb.Repository.RefreshTokenRepository;
import com.example.securingweb.Repository.UserRepository;
import com.example.securingweb.Request.RefreshTokenRequest;
import com.example.securingweb.Response.RefreshTokenResponse;

import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
 
@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {
	
	private final UserRepository userRepository;
	  private final RefreshTokenRepository refreshTokenRepository;
	  private final JwtService jwtService;
	 
	  @Value("${application.security.jwt.refresh-token.expiration}")
	  private long refreshExpiration;
	  
	 
	  public RefreshToken createRefreshToken(Long userId) {
	    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	    RefreshToken refreshToken = RefreshToken.builder()
	       .revoked(false)
	       .user(user)
	       .token(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()))
	       .expiryDate(Instant.now().plusMillis(refreshExpiration))
	       .build();
	    return refreshTokenRepository.save(refreshToken);
	 }
	 
	
	  public RefreshToken verifyExpiration(RefreshToken token) {
	    if(token == null){
	      log.error("Token is null");
	      throw new TokenException(null, "Token is null");
	   }
	    if(token.getExpiryDate().compareTo(Instant.now()) < 0 ){
	      refreshTokenRepository.delete(token);
	      throw new TokenException(token.getToken(), "Refresh token was expired. Please make a new authentication request");
	   }
	    return token;
	 }
	 
	
	  public RefreshTokenResponse generateNewToken(RefreshTokenRequest request) {
	    User user = refreshTokenRepository.findByToken(request.getRefreshToken())
	       .map(this::verifyExpiration)
	       .map(RefreshToken::getUser)
	       .orElseThrow(() -> new TokenException(request.getRefreshToken(),"Refresh token does not exist"));
	 
	    String token = jwtService.generateToken(user);
	    return RefreshTokenResponse.builder()
	       .accessToken(token)
	       .refreshToken(request.getRefreshToken())
	       .tokenType(TokenType.BEARER.name())
	       .build();
	 }

}
