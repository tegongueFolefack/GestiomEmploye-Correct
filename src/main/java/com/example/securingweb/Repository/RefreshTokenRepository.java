package com.example.securingweb.Repository;

import java.util.Optional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securingweb.Models.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	 
	  Optional<RefreshToken> findByToken(String token);

}
