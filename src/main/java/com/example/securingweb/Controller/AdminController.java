package com.example.securingweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.DTO.*;

import com.example.securingweb.DTO.*;
import com.example.securingweb.Models.*;
import com.example.securingweb.Request.AuthenticationRequest;
import com.example.securingweb.Request.RefreshTokenRequest;
import com.example.securingweb.Request.RegisterRequest;
import com.example.securingweb.Response.AuthenticationResponse;
import com.example.securingweb.Response.RefreshTokenResponse;
import com.example.securingweb.Service.AdminService;
import com.example.securingweb.Service.AuthenticationService;
import com.example.securingweb.Service.DepartementService;
import com.example.securingweb.Service.FonctionService;
import com.example.securingweb.Service.PaiementService;
import com.example.securingweb.Service.UtilisateurService;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.example.securingweb.Service.PlanningService;
import com.example.securingweb.Service.RefreshTokenService;




@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@SecurityRequirements() 
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	
	@Autowired
	  private  RefreshTokenService refreshTokenService ;
	@Autowired
	  private  AuthenticationManager authenticationManager;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UtilisateurService utilisateurService;
	
	@Autowired
	private PlanningService PlanningService;
	
//	@Autowired
//	private RoleService roleService;
	
	
	@Autowired
	private PaiementService paiementService;
	
	@Autowired
	private FonctionService fonctionService;
	
	@Autowired
	private DepartementService departementService;

//api Admin
	
	@GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        try {
            Optional<Admin> admin = adminService.getAdminById(id);
            if (admin.isPresent()) {
            	AdminDTO adminDTO = admin.get().toAdminDTO();
                return ResponseEntity.ok(adminDTO);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "admin not found with ID: " + id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        try {
        	adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<AdminDTO>> findAll() {
        try {
            Iterable<Admin> admins = adminService.getAllAdmin();
            List<AdminDTO> adminDTOs = new ArrayList<>();
            for (Admin admin : admins) {
            	adminDTOs.add(admin.toAdminDTO());
            }
            return ResponseEntity.ok(adminDTOs);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
        }
    }

	   
    @PostMapping("/register")
	  public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
	    return ResponseEntity.ok(adminService.register(request));
	 }
	 
	  @PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
	    return ResponseEntity.ok(adminService.authenticate(request));
	 }
	  @PostMapping("/refresh-token")
	  public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
	    return ResponseEntity.ok(refreshTokenService.generateNewToken(request));
	 }
	  @GetMapping("/info")
	    public Authentication getAuthentication(@RequestBody AuthenticationRequest request){
	        return authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
	    }
	
//    @PostMapping("add/")
//    public ResponseEntity<String> addAdmin(@RequestBody AdminDTO adminDto) {
//    	Admin admin =adminDto.toAdmin();
//    	Admin savedAdmin = adminService.saveAdmin(admin);
//        
//        // You can customize the confirmation message here
//        String confirmationMessage = "Comptable with ID " + savedAdmin.getId() + " has been added successfully.";
//        
//        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
//    }

	
	    @PutMapping("/update/{id}")
	    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDto) {
	        try {
	            Optional<Admin> adminOpt = adminService.getAdminById(id);
	            if (adminOpt.isPresent()) {
	            	Admin admin = adminOpt.get();
	                
	            	admin = adminDto.toAdmin();
	                
	                admin = adminService.updateAdmin(id, admin);
	                
	                AdminDTO adminResponse = admin.toAdminDTO();
	                
	                return ResponseEntity.ok(adminResponse);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    
//api utilisateur
	    @GetMapping("/getUserById/{id}")
	    public ResponseEntity<UserDTO> getUtilisateurById(@PathVariable Long id) {
	        try {
	            Optional<User> utilisateur = utilisateurService.getUtilisateurById(id);
	            if (utilisateur.isPresent()) {
	            	UserDTO utilisateurDTO = utilisateur.get().toUtilisateurDTO();
	                return ResponseEntity.ok(utilisateurDTO);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur not found with ID: " + id);
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    @GetMapping("/getAllUser/")
	    public ResponseEntity<List<UserDTO>> findAllUser() {
	        try {
	            Iterable<User> utilisateurs = utilisateurService.getAllUtilisateur();
	            List<UserDTO> utilisateurDTOs = new ArrayList<>();
	            for (User utilisateur : utilisateurs) {
	            	utilisateurDTOs.add(utilisateur.toUtilisateurDTO());
	            }
	            return ResponseEntity.ok(utilisateurDTOs);
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }

//	    @PostMapping("addUtilisateur/")
//	    public ResponseEntity<String> addUtilisateur(@RequestBody UserDTO utilisateurDto) {
//	    	User utilisateur =utilisateurDto.toUtilisateur();
//	    	User savedUtilisateur = utilisateurService.saveUtilisateur(utilisateur);
//	        
//	        // You can customize the confirmation message here
//	        String confirmationMessage = "Utilisateur with ID " + savedUtilisateur.getId() + " has been added successfully.";
//	        
//	        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
//	    }

	    @PutMapping("/updateUtilisateur/{id}")
	    public ResponseEntity<UserDTO> updateUtilisateur(@PathVariable Long id, @RequestBody UserDTO utilisateurDto) {
	        try {
	            Optional<User> utilisateurOpt = utilisateurService.getUtilisateurById(id);
	            if (utilisateurOpt.isPresent()) {
	            	User utilisateur = utilisateurOpt.get();
	                
	            	utilisateur = utilisateurDto.toUtilisateur();
	                
	            	utilisateur = utilisateurService.updateUtilisateur(id, utilisateur);
	                
	            	UserDTO utilisateurResponse = utilisateur.toUtilisateurDTO();
	                
	                return ResponseEntity.ok(utilisateurResponse);
	            } else {
	                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
	            }
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	    
	    @DeleteMapping("/deleteUtilisateur/{id}")
	    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
	        try {
	        	utilisateurService.deleteUtilisateur(id);
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
	      
	    
//api planning
	    @PostMapping("/addPlanning/")
	    public ResponseEntity<String> PlanningRole(@RequestBody PlanningDTO PlanningDto) {
	    	Planning Planning = PlanningDto.toPlanning();
	    	Planning savedPlanning = PlanningService.savePlanning(Planning);
	        
	        // You can customize the confirmation message here
	        String confirmationMessage = "Planning with ID " + savedPlanning.getId() + " has been added successfully.";
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
	    }

	   

		   
		 
		 
		
		  
	    
		    @PutMapping("updatePlanning/{id}")
		    public ResponseEntity<PlanningDTO> updatePlanning(@PathVariable Integer id, @RequestBody PlanningDTO PlanningDto) {
		        try {
		            Optional<Planning> PlanningOpt = PlanningService.getPlanningById(id);
		            if (PlanningOpt.isPresent()) {
		            	Planning Planning = PlanningOpt.get();
		                
		            	Planning =PlanningDto.toPlanning();
		                
		            	Planning = PlanningService.updatePlanning(id, Planning);
		                
		            	PlanningDTO PlanningResponse = Planning.toPlanningDTO();
		                
		                return ResponseEntity.ok(PlanningResponse);
		            } else {
		                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		            }
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }
		    
		    @GetMapping("getPlanningById/{id}")
		    public ResponseEntity<PlanningDTO> getPlanningById(@PathVariable Integer id) {
		        try {
		            Optional<Planning> Planning = PlanningService.getPlanningById(id);
		            if (Planning.isPresent()) {
		            	PlanningDTO PlanningDTO = Planning.get().toPlanningDTO();
		                return ResponseEntity.ok(PlanningDTO);
		            } else {
		                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Planning not found with ID: " + id);
		            }
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }

		    @DeleteMapping("deletePlanning/{id}")
		    public ResponseEntity<Void> deletePlanning(@PathVariable Integer id) {
		        try {
		        	PlanningService.deletePlanning(id);
		            return ResponseEntity.noContent().build();
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }

		    @GetMapping("getAllPlanning/")
		    public ResponseEntity<List<PlanningDTO>> getAllPlanning() {
		        try {
		            Iterable<Planning> Plannings = PlanningService.getAllPlanning();
		            List<PlanningDTO> PlanningDTOs = new ArrayList<>();
		            for (Planning Planning: Plannings) {
		            	PlanningDTOs.add(Planning.toPlanningDTO());
		            }
		            return ResponseEntity.ok(PlanningDTOs);
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }
		    
//api paiement
		    
		    @GetMapping("getPaiementById/{id}")
		    public ResponseEntity<PaiementDTO> getEmployeById(@PathVariable Integer id) {
		        try {
		            Optional<Paiement> employe = paiementService.getPaiementById(id);
		            if (employe.isPresent()) {
		            	PaiementDTO employeDTO = employe.get().toPaiementDTO();
		                return ResponseEntity.ok(employeDTO);
		            } else {
		                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comptable not found with ID: " + id);
		            }
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }
			
			 

		    @DeleteMapping("deletePaiement/{id}")
		    public ResponseEntity<Void> deletePaiement(@PathVariable Integer id) {
		        try {
		        	paiementService.deletePaiement(id);
		            return ResponseEntity.noContent().build();
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }

		    @GetMapping("geteAllPaiement/")
		    public ResponseEntity<List<PaiementDTO>> findAllPaiement() {
		        try {
		            Iterable<Paiement> employes = paiementService.getAllPaiement();
		            List<PaiementDTO> employeDTOs = new ArrayList<>();
		            for (Paiement employe : employes) {
		            	employeDTOs.add(employe.toPaiementDTO());
		            }
		            return ResponseEntity.ok(employeDTOs);
		        } catch (Exception e) {
		            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
		        }
		    }

			   

			
		    @PostMapping("addPaiement/")
		    public ResponseEntity<String> addPaiement(@RequestBody PaiementDTO employeDTOs) {
		    	Paiement employe = employeDTOs.toPaiement();
		    	Paiement savedEmploye = paiementService.savePaiement(employe);
		        
		        // You can customize the confirmation message here
		        String confirmationMessage = "Paiement with ID " + savedEmploye.getId() + " has been added successfully.";
		        
		        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
		    }

			
			    @PutMapping("updatePaiement/{id}")
			    public ResponseEntity<PaiementDTO> updatePaiement(@PathVariable Integer id, @RequestBody PaiementDTO employeDto) {
			        try {
			            Optional<Paiement> employeOpt = paiementService.getPaiementById(id);
			            if (employeOpt.isPresent()) {
			            	Paiement employe = employeOpt.get();
			                
			            	employe = employeDto.toPaiement();
			                
			            	employe = paiementService.updatPaiement(id, employe);
			                
			            	PaiementDTO employeResponse = employe.toPaiementDTO();
			                
			                return ResponseEntity.ok(employeResponse);
			            } else {
			                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
			            }
			        } catch (Exception e) {
			            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
			        }
			    }
			    

//api fonction
			   
			    @GetMapping("getFonctionById/{id}")
			    public ResponseEntity<FonctionDTO> getFonctionById(@PathVariable Integer id) {
			        try {
			            Optional<Fonction> fonction = fonctionService.getFonctionById(id);
			            if (fonction.isPresent()) {
			            	FonctionDTO fonctionDTO = fonction.get().toFonctionDTO();
			                return ResponseEntity.ok(fonctionDTO);
			            } else {
			                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "fonction not found with ID: " + id);
			            }
			        } catch (Exception e) {
			            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
			        }
			    }

			    @DeleteMapping("deleteFonction/{id}")
			    public ResponseEntity<Void> deleteFonction(@PathVariable Integer id) {
			        try {
			        	fonctionService.deleteFonction(id);
			            return ResponseEntity.noContent().build();
			        } catch (Exception e) {
			            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
			        }
			    }

			    @GetMapping("getAllFonction/")
			    public ResponseEntity<List<FonctionDTO>> findAllFonction() {
			        try {
			            Iterable<Fonction> fonctions = fonctionService.getAllFonction();
			            List<FonctionDTO> fonctionDTOs = new ArrayList<>();
			            for (Fonction fonction: fonctions) {
			            	fonctionDTOs.add(fonction.toFonctionDTO());
			            }
			            return ResponseEntity.ok(fonctionDTOs);
			        } catch (Exception e) {
			            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
			        }
			    }

				   

				
			    @PostMapping("addFonction/")
			    public ResponseEntity<String> addFonction(@RequestBody FonctionDTO fonctionDto) {
			        Fonction fonction = fonctionDto.toFonction();
			        Fonction savedFonction = fonctionService.saveFonction(fonction);
			        
			        // You can customize the confirmation message here
			        String confirmationMessage = "fonction with ID " + savedFonction.getId() + " has been added successfully.";
			        
			        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
			    }

				
				    @PutMapping("updateFonction/{id}")
				    public ResponseEntity<FonctionDTO> updateFonction(@PathVariable Integer id, @RequestBody FonctionDTO fonctionDto) {
				        try {
				            Optional<Fonction> fonctionOpt = fonctionService.getFonctionById(id);
				            if (fonctionOpt.isPresent()) {
				            	Fonction fonction = fonctionOpt.get();
				                
				            	fonction = fonctionDto.toFonction();
				                
				            	fonction = fonctionService.updatFonction(id, fonction);
				                
				                FonctionDTO fonctionResponse = fonction.toFonctionDTO();
				                
				                return ResponseEntity.ok(fonctionResponse);
				            } else {
				                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
				            }
				        } catch (Exception e) {
				            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
				        }
				    } 
				    
				    
//api departement
				    
				    @GetMapping("getDepartementByID/{id}")
				    public ResponseEntity<DepartementDTO> getDepartementById(@PathVariable Integer id) {
				        try {
				            Optional<Departement> Departement = departementService.getDepartementById(id);
				            if (Departement.isPresent()) {
				            	DepartementDTO DepartementDTO = Departement.get().toDepartementDTO();
				                return ResponseEntity.ok(DepartementDTO);
				            } else {
				                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "admin not found with ID: " + id);
				            }
				        } catch (Exception e) {
				            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
				        }
				    }

				    @DeleteMapping("deleteDeparatement/{id}")
				    public ResponseEntity<Void> deleteDepartement(@PathVariable Integer id) {
				        try {
				        	departementService.deleteDepartement(id);
				            return ResponseEntity.noContent().build();
				        } catch (Exception e) {
				            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
				        }
				    }

				    @GetMapping("geteAllDepartement/")
				    public ResponseEntity<List<DepartementDTO>> findAllDepartement() {
				        try {
				            Iterable<Departement> Departements = departementService.getAllDepartement();
				            List<DepartementDTO> DepartementDTOs = new ArrayList<>();
				            for (Departement Departement : Departements) {
				            	DepartementDTOs.add(Departement.toDepartementDTO());
				            }
				            return ResponseEntity.ok(DepartementDTOs);
				        } catch (Exception e) {
				            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
				        }
				    }

					   

					
				    @PostMapping("addDepartement/")
				    public ResponseEntity<String> addDepartement(@RequestBody DepartementDTO DepartementDto) {
				    	Departement Departement =DepartementDto.toDepartement();
				    	Departement savedDepartement = departementService.saveDepartement(Departement);
				        
				        // You can customize the confirmation message here
				        String confirmationMessage = "Departement with ID " + savedDepartement.getId() + " has been added successfully.";
				        
				        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
				    }

					

				    @PutMapping("updateDepartement/{id}")
				    public ResponseEntity<DepartementDTO> updateDepartement(@PathVariable Integer id, @RequestBody DepartementDTO DepartementDto) {
				        try {
				            Optional<Departement> DepartementOpt = departementService.getDepartementById(id);
				            if (DepartementOpt.isPresent()) {
				            	Departement Departement = DepartementOpt.get();
				                
				            	Departement = DepartementDto.toDepartement();
				                
				            	Departement = departementService.updateDepartement(id, Departement);
				                
				            	DepartementDTO DepartementResponse = Departement.toDepartementDTO();
				                
				                return ResponseEntity.ok(DepartementResponse);
				            } else {
				                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
				            }
				        } catch (Exception e) {
				            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
				        }
				    }
				    
//api role
				    
//				    @GetMapping("getRoleById/{id}")
//				    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Integer id) {
//				        try {
//				            Optional<Role> Role = roleService.getRoleById(id);
//				            if (Role.isPresent()) {
//				            	RoleDTO RoleDTO = Role.get().toRoleDTO();
//				                return ResponseEntity.ok(RoleDTO);
//				            } else {
//				                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found with ID: " + id);
//				            }
//				        } catch (Exception e) {
//				            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
//				        }
//				    }
//
//				    @DeleteMapping("deleteRole/{id}")
//				    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
//				        try {
//				        	roleService.deleteRole(id);
//				            return ResponseEntity.noContent().build();
//				        } catch (Exception e) {
//				            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
//				        }
//				    }
//
//				    @GetMapping("/getAllRole")
//				    public ResponseEntity<List<RoleDTO>> findAllRole() {
//				        try {
//				            Iterable<Role> Roles = roleService.getAllRole();
//				            List<RoleDTO> RoleDTOs = new ArrayList<>();
//				            for (Role Role: Roles) {
//				            	RoleDTOs.add(Role.toRoleDTO());
//				            }
//				            return ResponseEntity.ok(RoleDTOs);
//				        } catch (Exception e) {
//				            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
//				        }
//				    }
//
//					   
//
//					
//				    @PostMapping("addRole/")
//				    public ResponseEntity<String> addRole(@RequestBody RoleDTO RoleDto) {
//				    	Role Role = RoleDto.toRole();
//				    	Role savedRole = roleService.saveRole(Role);
//				        
//				        // You can customize the confirmation message here
//				        String confirmationMessage = "Role with ID " + savedRole.getId() + " has been added successfully.";
//				        
//				        return ResponseEntity.status(HttpStatus.CREATED).body(confirmationMessage);
//				    }
//
//					
//					    @PutMapping("updateRole/{id}")
//					    public ResponseEntity<RoleDTO> updateFonction(@PathVariable Integer id, @RequestBody RoleDTO RoleDto) {
//					        try {
//					            Optional<Role> RoleOpt = roleService.getRoleById(id);
//					            if (RoleOpt.isPresent()) {
//					            	Role Role = RoleOpt.get();
//					                
//					            	Role = RoleDto.toRole();
//					                
//					            	Role = roleService.updateRole(id, Role);
//					                
//					            	RoleDTO RoleResponse = Role.toRoleDTO();
//					                
//					                return ResponseEntity.ok(RoleResponse);
//					            } else {
//					                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
//					            }
//					        } catch (Exception e) {
//					            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");
//					        }
//					    }
//			    
	    @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
	        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleException(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	    }

}
