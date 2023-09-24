package com.example.securingweb.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.securingweb.Models.Admin;
import com.example.securingweb.Models.Comptable;
import com.example.securingweb.Models.Departement;
import com.example.securingweb.Models.HeureSupplementaire;
import com.example.securingweb.Models.Paiement;
import com.example.securingweb.Models.*;
import com.example.securingweb.Repository.AdminRepository;
import com.example.securingweb.Repository.ComptableRepository;
import com.example.securingweb.Repository.HeureSupplementaireRepository;
import com.example.securingweb.Repository.PaiementRepository;
import com.example.securingweb.Repository.*;



@Service
public class PaiementService {
	
	
	 @Autowired
	    private PaiementRepository paiementRepository;
	 
	 @Autowired
	 private UserRepository utilisateurRepository;
	 
	 @Autowired
	 private AdminRepository adminRepository;
	 
	 @Autowired
	 private ComptableRepository comptableRepository;
	 
	
	 

		public Optional<Paiement> getPaiementById(Integer id) {
			return paiementRepository.findById(id);
		}

		public Iterable<Paiement> getAllPaiement() {
			return paiementRepository.findAll();
		}

		public boolean deletePaiement(Integer id) {
			Optional<Paiement> paiementOpt = getPaiementById(id);
			if (paiementOpt.isPresent()) {
				paiementRepository.deleteById(id);
				return true;
			} else {
				return false;
			}
		}
		
		public Paiement updatPaiement(Integer id, Paiement paiement2) {
		    Optional<Paiement> paiementOpt = paiementRepository.findById(id);
		    
		    if (paiementOpt.isPresent()) {
		    	Paiement paiement = paiementOpt.get();
		        
		        // Mise à jour des propriétés de l'objet Comptable avec les données du ComptableDTO
		    	paiement.setDate_paiement(paiement2.getDate_paiement());
		    	paiement.setPeriode_paiement(paiement2.getPeriode_paiement());
		    	paiement.setPrecompte_professionnel(paiement2.getPrecompte_professionnel());
		    	paiement.setCotisation_onss(paiement2.getCotisation_onss());
		    	paiement.setRetenue_retraite(paiement2.getRetenue_retraite());
		    	paiement.setRetenue_chaumage(paiement2.getRetenue_chaumage());
		    	paiement.setRetenu_total(paiement2.getRetenu_total());
		    	paiement.setOnss_renum(paiement2.getOnss_renum());
		    	paiement.setSuppl_transport(paiement2.getSuppl_transport());
		    	paiement.setPrimes_hs(paiement2.getPrimes_hs());
		    	paiement.setPrime_prestation(paiement2.getPrime_prestation());
		    	paiement.setPrime_equipe(paiement2.getPrime_equipe());
		    	paiement.setTotal_prime(paiement2.getTotal_prime());
		    	paiement.setTotal_brut(paiement2.getTotal_brut());
		    	paiement.setTotal_imposable(paiement2.getTotal_imposable());
		    	paiement.setTotal_net(paiement2.getTotal_net());
		    	paiement.setTotal_heure(paiement2.getTotal_heure());
		    	
		        
		        // Sauvegarder les modifications dans la base de données
		        return paiementRepository.save(paiement);
		    } else {
		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
		    }
		}

	 
	 public Paiement savePaiement(Paiement paiement) {
			return paiementRepository.save(paiement);
		}

	 public Paiement ajouterAdminAPaiement(Long adminId, Integer paiementId) {
	        Optional<Admin> adminOpt = adminRepository.findById(adminId);
	        Optional<Paiement> paiementOpt = paiementRepository.findById(paiementId);

	        if (adminOpt.isPresent() && paiementOpt.isPresent()) {
	            Admin admin = adminOpt.get();
	            Paiement paiement = paiementOpt.get();

	            paiement.setAdmin(admin);
	            return paiementRepository.save(paiement);
	        } else {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "paiement or admin not found");
	        }
	    }
	 
	 public Paiement ajouterUtilisateurAPaiement(Long utilisateurId, Integer paiementId) {
	        Optional<User> utilisateurOpt = utilisateurRepository.findById(utilisateurId);
	        Optional<Paiement> paiementOpt = paiementRepository.findById(paiementId);

	        if (utilisateurOpt.isPresent() && paiementOpt.isPresent()) {
	        	User admin = utilisateurOpt.get();
	            Paiement paiement = paiementOpt.get();

	            paiement.setUtilisateur(admin);
	            return paiementRepository.save(paiement);
	        } else {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "paiement or admin not found");
	        }
	    }
	 
	 public Paiement ajouterComptableAPaiement(Long comptableId, Integer paiementId) {
	        Optional<Comptable> comptableOpt = comptableRepository.findById(comptableId);
	        Optional<Paiement> paiementOpt = paiementRepository.findById(paiementId);

	        if (comptableOpt.isPresent() && paiementOpt.isPresent()) {
	        	Comptable comptable = comptableOpt.get();
	            Paiement paiement = paiementOpt.get();

	            paiement.setComptable(comptable);
	            return paiementRepository.save(paiement);
	        } else {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "paiement or admin not found");
	        }
	    }

//	 public double salaireBase(User utilisateur) {
//	        return utilisateur.getSalaireBase();
//	    }

//	    public double Sursalaire_jour_fer(User utilisateur, int heuresTravaillees) {
//	        double tauxSursalaire = 1.5; // Exemple de taux de sursalaire pour jours fériés
//	        double sursalaire = heuresTravaillees * utilisateur.getTauxHoraire() * tauxSursalaire;
//	        return sursalaire;
//	    }

// 	    public int nbHS(Utilisateur utilisateur) {
//	        List<HeureSupplementaire> heuresSupplementaires = utilisateur.getHeuresSupplementaires();
//	        int totalHeuresSupplementaires = 0;
//
//	        for (HeureSupplementaire heureSupplementaire : heuresSupplementaires) {
//	            totalHeuresSupplementaires += heureSupplementaire.getNombreHeures();
//	        }
//
//	        return totalHeuresSupplementaires;
//	    }
//
//
//	    public double salaireBrute(Utilisateur utilisateur) {
//	        double salaireBase = utilisateur.getSalaireBase();
//	        int heuresTravaillees = utilisateur.getHeuresTravailFixes();
//	        double tauxHoraire = utilisateur.getTauxHoraire();
//	        double montantHeuresSupplementaires = montantHS(utilisateur);
//
//	        double salaireBrute = (salaireBase + heuresTravaillees + montantHeuresSupplementaires) * tauxHoraire;
//	        return salaireBrute;
//	    }
//
//
//	    public void heur_travail_fix(Utilisateur utilisateur, int heuresFixes) {
//	        utilisateur.setHeuresTravailFixes(heuresFixes);
//	        utilisateurRepository.save(utilisateur);
//	    }
//
//	    public double ONSS_Renum(Utilisateur utilisateur) {
//	        double salaireBrute = salaireBrute(utilisateur);
//	        double tauxONSS = 0.13; // Exemple de taux ONSS
//	        double ONSS = salaireBrute * tauxONSS;
//	        return ONSS;
//	    }
//
//	    public double suppl_transport_prive(Utilisateur utilisateur) {
//	        double montant = 0;
//	        if (utilisateur.isTransportPrive()) {
//	            montant = 100; // Exemple de montant pour le transport privé
//	        }
//	        return montant;
//	    }
//
//	    public double partie_Exo_fisca_transp(Utilisateur utilisateur) {
//	        double montant = 0;
//	        if (utilisateur.isTransportPrive()) {
//	            montant = 50; // Exemple de montant exonéré fiscalement
//	        }
//	        return montant;
//	    }
//
//	    public void Adapt_Contrat(Utilisateur utilisateur, int nouvellesHeuresTravail) {
//	        utilisateur.setHeuresTravailFixes(nouvellesHeuresTravail);
//	        utilisateurRepository.save(utilisateur);
//	    }
//
//	    public double montantHS(Utilisateur utilisateur) {
//	        int heuresSupplementaires = nbHS(utilisateur);
//	        double tauxHoraire = utilisateur.getTauxHoraire();
//	        double montant = heuresSupplementaires * tauxHoraire;
//	        return montant;
//	    }
//
//	    public double retenueChaumage(Utilisateur utilisateur) {
//	        double tauxRetenue = 0.1; // Exemple de taux de retenue pour le chômage
//	        double retenue = salaireBrute(utilisateur) * tauxRetenue;
//	        return retenue;
//	    }
//
//	    public double retenueRetraite(Utilisateur utilisateur) {
//	        double tauxRetenue = 0.15;
//	        double salaireBrute = salaireBrute(utilisateur);
//	        double tauxTaxe = 0.02; // Exemple de taux de taxe d'assurance
//	        double taxe = salaireBrute * tauxTaxe;
//	        return taxe;
//	    }
//	    
//	    public double retenueTotales(Utilisateur utilisateur) {
//	        // Récupérez l'objet Paiement associé à l'utilisateur
//	        Paiement paiement = (Paiement) utilisateur.getPaiement();
//	        
//	        // Vérifiez si paiement est null pour éviter les erreurs
//	        if (paiement != null) {
//	            return paiement.getRetenu_total();
//	        } else {
//	            // Gérer le cas où l'objet Paiement est absent (peut-être renvoyer 0 ou une valeur par défaut)
//	            return 0.0;
//	        }
//	    }
//	    
//	    public double totalBrut(Utilisateur utilisateur) {
//	        double salaireBrute = salaireBrute(utilisateur);
//	        double sursalaire = Sursalaire_jour_fer(utilisateur, 0);
//	        double heuresSupplementaires = montantHS(utilisateur);
//	        double totalBrut = salaireBrute + sursalaire + heuresSupplementaires;
//	        return totalBrut;
//	    }
//	    
//	    public double totalImposable(Utilisateur utilisateur) {
//	        double totalBrut = totalBrut(utilisateur);
//	        double montantRepas = chaque_Repas(utilisateur);
//	        double cotAssGroup = cot_Ass_Group(utilisateur);
//	        double taxeAss = Taxe_Ass(utilisateur);
//	        double totalImposable = totalBrut + montantRepas + cotAssGroup + taxeAss;
//	        return totalImposable;
//	    }
//	    
//	    public double chaque_Repas(Utilisateur utilisateur) {
//	        double coutRepasParJour = 8.0; // Coût d'un repas par jour
//	        int joursTravaillesParMois = 20; // Nombre approximatif de jours travaillés par mois
//
//	        double montantRepas = coutRepasParJour * joursTravaillesParMois;
//	        return montantRepas;
//	    }
//
//	    public double cot_Ass_Group(Utilisateur utilisateur) {
//	        double tauxCotAssGroup = 0.05; // Exemple de taux de cotisation d'assurance groupe
//	        double cotAssGroup = salaireBrute(utilisateur) * tauxCotAssGroup;
//	        return cotAssGroup;
//	    }
//
//	    public double Taxe_Ass(Utilisateur utilisateur) {
//	        double tauxTaxeAss = 0.02; // Exemple de taux de taxe d'assurance
//	        double taxeAss = salaireBrute(utilisateur) * tauxTaxeAss;
//	        return taxeAss;
//	    }
//
//	    public double cotisation_ONSS_special(Utilisateur utilisateur) {
//	        double tauxCotONSSSpecial = 0.03; // Exemple de taux de cotisation spéciale ONSS
//	        double cotONSSSpecial = salaireBrute(utilisateur) * tauxCotONSSSpecial;
//	        return cotONSSSpecial;
//	    }
//	    
//	    public double totalNet(Utilisateur utilisateur) {
//	        double totalImposable = totalImposable(utilisateur);
//	        double retenuesTotal = retenueTotales(utilisateur);
//	        double ONSS = ONSS_Renum(utilisateur);
//	        double partieExoFisca = partie_Exo_fisca_transp(utilisateur);
//	        double cotONSSSpecial = cotisation_ONSS_special(utilisateur);
//	        double totalNet = totalImposable - retenuesTotal - ONSS - partieExoFisca - cotONSSSpecial;
//	        return totalNet;
//	    }
//	    
	   
}
