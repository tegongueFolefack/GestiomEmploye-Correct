package com.example.securingweb.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.securingweb.Models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);

}
