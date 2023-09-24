package com.example.securingweb.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.securingweb.Enums.*;
import com.example.securingweb.Models.Admin;
import com.example.securingweb.Models.Comptable;
import com.example.securingweb.Models.Employe;
import com.example.securingweb.Repository.AdminRepository;
import com.example.securingweb.Repository.ComptableRepository;
import com.example.securingweb.Repository.EmployeRepository;
import com.example.securingweb.Repository.*;

@Service
public class BDInitService {
	
	@Autowired
	private UserRepository utilisateurRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	
	 
	 @Autowired
	 private ComptableRepository comptableRepository;
	 
	 @Autowired
	 private EmployeRepository employeRepository;

	 
	    
	    public void initializeUsers() {
	    	
	    	
	    	Role adminRole = Role.ADMIN;
	    	Role comptableRole = Role.COMPTABLE;
	    	Role employeRole = Role.EMPLOYE;

	    	        Optional<Admin> adminList = adminRepository.findByEmail("admin@example.com");
	    	        if (adminList.isEmpty()) {
	    	            Admin admin = new Admin();
	    	           // admin.setLogin("admin@example.com");
	    	            admin.setPassword("adminpassword");
//	    	            admin.setDate_creation(new Date());
//	    	            admin.setGenre("Homme");
//	    	            admin.setEtat_Civil("Célibataire");
//	    	            admin.setTelephone(123456789);
//	    	            admin.setMatricule(123);
	    	            admin.setEmail("admin@example.com");
//	    	            admin.setCompteIBAN("BE123456789");
//	    	            admin.setAddresse("123, Rue Principale");
	    	            admin.setNom("Admin");
	    	            admin.setPrenom("Admin");
	    	            admin.setRole(adminRole);
	    	            adminRepository.save(admin);
	    	        } 

	    	        Optional<Comptable> ComptableList = comptableRepository.findByEmail("comptable@example.com");
	    	        if (ComptableList.isEmpty()) {
	    	            Comptable comptable = new Comptable();
//	    	            comptable.setLogin("comptable@example.com");
    	            comptable.setPassword("comptablepassword");
//	    	            comptable.setDate_creation(new Date());
//	    	            comptable.setGenre("Homme");
//	    	            comptable.setEtat_Civil("Célibataire");
//	    	            comptable.setTelephone(123456789);
//	    	            comptable.setMatricule(123);
	    	            comptable.setEmail("comptable@example.com");
//	    	            comptable.setCompteIBAN("BE123456789");
//	    	            comptable.setAddresse("123, Rue Principale");
	    	            comptable.setNom("comptable");
	    	            comptable.setPrenom("comptable");
	    	            comptable.setRole(comptableRole);
	    	            comptableRepository.save(comptable);
	    	        }

	    	        // Check if employe user already exists
	    	        Optional<Employe> EmployeList = employeRepository.findByEmail("employe@example.com");
	    	        if (EmployeList.isEmpty()) {
	    	            Employe employe = new Employe();
//	    	            employe.setLogin("employe@example.com");
	    	            employe.setPassword("employepassword");
//	    	            employe.setDate_creation(new Date());
//	    	            employe.setGenre("Homme");
//	    	            employe.setEtat_Civil("Célibataire");
//	    	            employe.setTelephone(123456789);
//	    	            employe.setMatricule(123);
	    	            employe.setEmail("employe@example.com");
//	    	            employe.setCompteIBAN("BE123456789");
//	    	            employe.setAddresse("123, Rue Principale");
	    	            employe.setNom("employe");
	    	            employe.setPrenom("employe");
	    	            employe.setRole(employeRole);
	    	            employeRepository.save(employe);
	    	        }
	    	    }
}


