package jags.backend.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import jags.backend.DTO.AjoutParticipant;
import jags.backend.entities.BilanParticipantSession;
import jags.backend.entities.Coordonnee;
import jags.backend.entities.Entreprise;
import jags.backend.entities.Participant;
import jags.backend.repositories.ParticipantRepository;

@Service
public class ParticipantService {
	
	@Autowired
	private ParticipantRepository repository;
	
	@Autowired
	private EntrepriseService entrepriseService;
	
	@Autowired
	private BilanParticipantSessionService bilanParticipantSessionService;

	@Autowired
	private Entreprise entreprise;
	@Autowired
	private Participant participant;

	/**
	 * Récupère tout les participants contenu dans la bdd
	 * @return la liste de tout les participants au format DTO AjoutParticipant
	 */
	public List<AjoutParticipant> findAll() {
		List<Participant> participants = this.repository.findAll();		
		return recupererListeParticipantsDTO(participants);		 
	}

	/**
	 * Ajoute un participant en base de donnée 
	 * @param participant participant que l'on veut ajouter
	 * @return le participant ajoutée
	 */
	public AjoutParticipant saveNouveauParticipant(AjoutParticipant nouveauParticipant) {
		this.participant = save(ajoutParticipantToParticipant(nouveauParticipant));
		return ParticipantToAjoutParticipant(this.participant);
	}
	
	public Participant ajoutParticipantToParticipant(AjoutParticipant nouveauParticipant) {
		this.participant = new Participant();
		this.participant.setNom(nouveauParticipant.getNom());
		this.participant.setPrenom(nouveauParticipant.getPrenom());
		this.participant.setCivilite(nouveauParticipant.getCivilite());
		this.participant.setDateNaissance(nouveauParticipant.getDateNaissance());
		this.participant.setIdentifiant(nouveauParticipant.getIdentifiant());
		this.participant.setMdp(nouveauParticipant.getMdp());
		return this.participant;
	}
	
	public AjoutParticipant ParticipantToAjoutParticipant(Participant participant) {
		AjoutParticipant ajoutParticipant = new AjoutParticipant();
		ajoutParticipant.setId(participant.getId());
		ajoutParticipant.setNom(participant.getNom());
		ajoutParticipant.setPrenom(participant.getPrenom());
		ajoutParticipant.setCivilite(participant.getCivilite());
		ajoutParticipant.setDateNaissance(participant.getDateNaissance());
		ajoutParticipant.setIdentifiant(participant.getIdentifiant());
		ajoutParticipant.setMdp(participant.getMdp());
		return ajoutParticipant;
	}
	/**
	 * Ajoute un participant en base de donnée 
	 * @param participant participant que l'on veut ajouter
	 * @return le participant ajoutée
	 */
	public Participant save(Participant participant) {
		return this.repository.save(participant);
	}

	/**
	 * Récupération d'un participant par son id
	 * @param participantId Ide du participant que l'on recherche
	 * @return le participant qui correspond à l'id recherché
	 */
	public Participant findById(Long participantId) {
		return this.repository.findById(participantId)
							.orElseThrow(() ->  new ResponseStatusException (HttpStatus.NOT_FOUND));
	}
	
	/**
	 * Permet de récupérer les participants d'une entreprise par l'ID de l'entreprise
	 * @param entrepriseId un entier (Long) corespondant à l'ID de l'entreprise 
	 * @return Une liste de Participant qui appartiennent à l'entreprise dont l'id = entreprise id
	 */
	public List<Participant> findByEntreprise(Long entrepriseId) {
		entreprise = this.entrepriseService.findById(entrepriseId);
		return this.repository.findByEntreprise(entreprise);
	}

	public List<Participant> findParticipantBySessionId(Long sessionId) {
		List<Long> idParticipant = new ArrayList<Long>();
		for (BilanParticipantSession bilan : this.bilanParticipantSessionService.findParticipantBySessionId(sessionId)) {
			idParticipant.add(bilan.getParticipant().getId());
		}
		return this.repository.findAllById(idParticipant);
	}
	
	/**
	 * Recupération d'une liste au format DTO ajoutParticipant
	 * @param une liste de participants au format Participant
	 * @return une liste de participants au format DTO ajoutParticipant
	 */
	public List<AjoutParticipant> recupererListeParticipantsDTO(List<Participant> participants){
		List<AjoutParticipant> listeAjoutParticipant = new ArrayList<>();
		for (Participant participant : participants) {
			AjoutParticipant ajoutParticipant =ParticipantToAjoutParticipant(participant);
			listeAjoutParticipant.add(ajoutParticipant);
		}
		return listeAjoutParticipant;
	}
	public Participant findIdParticipantByCoordonneeId(Coordonnee coordonnee) {
		return this.repository.findByCoordonnee(coordonnee);
	}
}
