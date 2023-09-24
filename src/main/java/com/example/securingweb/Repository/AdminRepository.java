package com.example.securingweb.Repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.securingweb.Models.Admin;
import com.example.securingweb.Models.User;



public interface AdminRepository extends CrudRepository<Admin, Long> {
	Optional<Admin> findByEmail(String email);

	User save(User admin);

}
