
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProviderRepository;
import domain.CreditCard;
import domain.Item;
import domain.Provider;
import domain.SocialProfile;
import domain.Sponsorship;

@Service
@Transactional
public class ProviderService {

	//Repository-----------------------------------------------------------------------

	@Autowired
	private ProviderRepository		providerRepository;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private ItemService				itemService;

	@Autowired
	private SponsorshipService		sponsorshipService;


	//CRUD----------------------------------------------------------------------------

	//Other methods--------------------------------------------------------------------------------

	public Provider findProviderByUserAcountId(final int userAccountId) {
		return this.providerRepository.findProviderByUserAcountId(userAccountId);
	}

	public void deleteProvider(final Provider provider) {
		Assert.notNull(provider);
		this.serviceUtils.checkActor(provider);
		this.serviceUtils.checkAuthority("PROVIDER");

		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findProfileByActorId(provider.getId());
		final CreditCard a = this.creditCardService.findCreditCardByActor(provider.getId());
		final List<Item> items = this.itemService.findAllByPrincipal();
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findSponsorshipsByProviderId(provider.getId());

		for (final Item i : items)
			this.itemService.delete(i);

		for (final Sponsorship s : sponsorships) {
			final CreditCard c = s.getCreditCard();
			this.creditCardService.delete1(c);
			this.sponsorshipService.delete(s);
		}
		this.messageService.deleteMyMessages();

		for (final SocialProfile s : socialProfiles)
			this.socialProfileService.delete(s);

		if (a != null)
			this.creditCardService.delete(a);

		this.providerRepository.delete(provider.getId());
		this.providerRepository.flush();

	}

}
