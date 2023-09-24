package com.example.securingweb.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.securingweb.Models.Comptable;



public interface ComptableRepository extends CrudRepository<Comptable, Long> {
	Optional<Comptable> findByEmail(String email);
}
