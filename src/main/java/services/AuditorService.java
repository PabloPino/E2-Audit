
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.CreditCard;
import domain.SocialProfile;
import repositories.AuditorRepository;

@Service
@Transactional
public class AuditorService {

	//Repository-----------------------------------------------------------------

	@Autowired
	private AuditorRepository		auditorRepository;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private AuditService			auditService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private ActorService			actorService;


	//Utiles----------------------------------------------------------------------

	public Auditor findAuditorByUserAcountId(final int userAccountId) {
		return this.auditorRepository.findAuditorByUserAcountId(userAccountId);
	}

	public void deleteAuditor(final Auditor auditor) {
		Assert.notNull(auditor);
		this.serviceUtils.checkActor(auditor);
		this.serviceUtils.checkAuthority("AUDITOR");

		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findProfileByActorId(auditor.getId());
		final CreditCard creditCard = this.creditCardService.findCreditCardByActor(auditor.getId());
		final List<Audit> audits = this.auditService.findAuditsByAuditor(auditor);

		for (final Audit a : audits)
			this.auditService.delete(a);

		this.messageService.deleteMyMessages();

		for (final SocialProfile s : socialProfiles)
			this.socialProfileService.delete(s);

		this.creditCardService.delete(creditCard);
		this.auditorRepository.delete(auditor.getId());
		this.auditorRepository.flush();
		final Collection<Actor> actors = this.actorService.findAll();
		Assert.isTrue(!(actors.contains(auditor)));
	}

	public Auditor findOne(final Integer id) {
		return this.auditorRepository.findOne(id);
	}

}
