package jags.backend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jags.backend.entities.Lieu;
import jags.backend.services.LieuService;

@RestController
@RequestMapping("lieux")
@CrossOrigin
public class LieuController {

	@Autowired
	LieuService service;
	
	@GetMapping("")
	public List<Lieu> findAll(){
		return this.service.findAll();
	}
	
	@DeleteMapping("{id}")
	public void deleteById(@PathVariable Long id) {
		this.service.deleteById(id);
	}
	
//	@GetMapping("/disponibilite/{bool}")
//	public List<Lieu> findByDisponibilite(@PathVariable Boolean bool){
//		return this.service.finbByDisponibilite(bool);
//	}
	
	@GetMapping("/session/{sessionId}")
	public List<Lieu> findLieuBySessionId(@PathVariable Long sessionId){
		return this.service.findLieuBySessionsId(sessionId);
	}
}
