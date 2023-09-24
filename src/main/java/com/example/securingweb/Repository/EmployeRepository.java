package com.example.securingweb.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.securingweb.Models.Admin;
import com.example.securingweb.Models.Employe;

public interface EmployeRepository extends CrudRepository<Employe, Long>{
	Optional<Employe> findByEmail(String email);
}
