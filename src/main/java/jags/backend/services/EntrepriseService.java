package jags.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jags.backend.entities.Entreprise;
import jags.backend.repositories.EntrepriseRepository;

@Service
public class EntrepriseService {

	@Autowired
	private EntrepriseRepository repository;
	
	public List<Entreprise> findAll(){
		return this.repository.findAll();
	}
	
	public Entreprise save(Entreprise entity) {
		return this.repository.save(entity);
	}

	public Entreprise findBySiret(String siret) {
		return this.repository.findBySiret(siret);
		
	}
	public Boolean existsEntrepriseBySiret(String siret) {
		return this.repository.existsEntrepriseBySiret(siret);
	}
}
