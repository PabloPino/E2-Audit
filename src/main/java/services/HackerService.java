
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import repositories.HackerRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;
import domain.Application;
import domain.CreditCard;
import domain.Curricula;
import domain.Finder;
import domain.Hacker;
import domain.SocialProfile;
import forms.HackerForm;

@Service
@Transactional
public class HackerService {

	@Autowired
	private HackerRepository			repository;

	@Autowired
	private ActorService				actorService;
	@Autowired
	private CreditCardService			creditCardService;
	@Autowired
	private UserAccountRepository		userAccountRepository;
	@Autowired
	private ServiceUtils				serviceUtils;
	@Autowired
	private MessageSource				messageSource;
	@Autowired
	private SocialProfileService		socialProfileService;
	@Autowired
	private ApplicationService			applicationService;
	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private PositionDataService			positionDataService;
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;
	@Autowired
	private EducationDataService		educationDataService;
	@Autowired
	private PersonalDataService			personalDataService;
	@Autowired
	private FinderService				finderService;

	@Autowired
	private MessageService				messageService;


	public Hacker findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Hacker> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public List<Hacker> findAll() {
		return this.repository.findAll();
	}

	public Hacker create() {
		this.serviceUtils.checkNoActor();
		final Hacker res = new Hacker();
		res.setBanned(false);
		res.setUserAccount(new UserAccount());
		return res;
	}

	public Hacker save(final Hacker h) {
		final Hacker hacker = (Hacker) this.serviceUtils.checkObjectSave(h);

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(h.getUserAccount().getPassword(), null);
		if (h.getId() == 0) {
			this.serviceUtils.checkNoActor();
			h.setBanned(false);
			h.setSpammer(null);
			//h.getUserAccount().setPassword(hash);

		} else {
			this.serviceUtils.checkAnyAuthority(new String[] {
				Authority.ADMIN, Authority.HACKER
			});
			if (this.actorService.findPrincipal() instanceof Hacker) {
				this.serviceUtils.checkActor(hacker);
				h.setBanned(hacker.getBanned());
				h.setSpammer(hacker.getSpammer());
				//if (hacker.getUserAccount().getPassword() != hash)
				//h.getUserAccount().setPassword(hash);
			} else {
				h.setEmail(hacker.getEmail());
				h.setName(hacker.getName());
				h.setPhone(hacker.getPhone());
				h.setPhoto(hacker.getPhoto());
				h.setSurname(hacker.getSurname());
				h.setUserAccount(hacker.getUserAccount());
				h.setAddress(hacker.getAddress());
			}
		}
		final UserAccount userAccount = this.userAccountRepository.save(h.getUserAccount());
		hacker.setUserAccount(userAccount);
		h.setCreditCard(hacker.getCreditCard());
		final CreditCard creditCard = this.creditCardService.save(h.getCreditCard());
		hacker.setCreditCard(creditCard);
		final Hacker res = this.repository.save(h);
		/*
		 * if (h.getId() == 0) {
		 * this.boxService.addSystemBox(res);
		 * }
		 */
		return res;
	}

	public void delete(final Hacker h) {
		final Hacker hacker = (Hacker) this.serviceUtils.checkObject(h);
		this.serviceUtils.checkActor(hacker);
		this.repository.delete(hacker);
	}

	public HackerForm construct(final Hacker h) {
		final HackerForm res = new HackerForm();
		res.setEmail(h.getEmail());
		res.setName(h.getName());
		res.setPhone(h.getPhone());
		res.setPhoto(h.getPhoto());
		res.setSurname(h.getSurname());
		res.setUsername(h.getUserAccount().getUsername());
		res.setId(h.getId());
		res.setVersion(h.getVersion());
		res.setAddress(h.getAddress());
		res.setVATNumber(h.getVATNumber());
		res.setCVVCode(h.getCreditCard().getCVVCode());
		res.setExpirationMonth(h.getCreditCard().getExpirationMonth());
		res.setExpirationYear(h.getCreditCard().getExpirationYear());
		res.setHolderName(h.getCreditCard().getHolderName());
		res.setMakeName(h.getCreditCard().getMakeName());
		res.setNumber(h.getCreditCard().getNumber());
		return res;
	}

	public void validateForm(final HackerForm form, final BindingResult binding) {
		if (form.getId() == 0 && !form.getAccept()) {
			/*
			 * binding.addError(new FieldError("hackerForm", "accept", form.getAccept(), false, new String[] {
			 * "hackerForm.accept", "accept"
			 * }, new Object[] {
			 * new DefaultMessageSourceResolvable(new String[] {
			 * "hackerForm.accept", "accept"
			 * }, new Object[] {}, "accept")
			 * }, "hacker.mustaccept"));
			 */
			final Locale locale = LocaleContextHolder.getLocale();
			final String errorMessage = this.messageSource.getMessage("company.mustaccept", new Object[] {
				form.getAccept()
			}, locale);
			binding.addError(new FieldError("hackerForm", "accept", errorMessage));
		}
		if (!form.getConfirmPassword().equals(form.getPassword())) {
			final Locale locale = LocaleContextHolder.getLocale();
			final String errorMessage = this.messageSource.getMessage("company.mustmatch", null, locale);
			binding.addError(new FieldError("hackerForm", "confirmPassword", errorMessage));
		}
		if (form.getEmail().endsWith("@") || form.getEmail().endsWith("@>")) {
			final Locale locale = LocaleContextHolder.getLocale();
			final String errorMessage = this.messageSource.getMessage("actor.bademail", new Object[] {
				form.getEmail()
			}, locale);
			binding.addError(new FieldError("hackerForm", "email", errorMessage));
		}
		final Date date = new Date(System.currentTimeMillis());
		final Integer year = date.getYear() + 1900;
		Boolean creditCardValid = form.getExpirationYear() >= year;
		if (form.getExpirationYear() == year)
			creditCardValid = form.getExpirationMonth() > date.getMonth();
		if (!creditCardValid) {
			final Locale locale = LocaleContextHolder.getLocale();
			final String errorMessageMonth = this.messageSource.getMessage("actor.creditcardexpired", new Object[] {
				form.getExpirationMonth()
			}, locale);
			final FieldError errorMonth = new FieldError("hackerForm", "expirationMonth", form.getExpirationMonth(), true, new String[] {
				"actor.creditcardexpired"
			}, new Object[] {
				form.getExpirationMonth()
			}, errorMessageMonth);
			binding.addError(errorMonth);
			final String errorMessageYear = this.messageSource.getMessage("actor.creditcardexpired", new Object[] {
				form.getExpirationYear()
			}, locale);
			final FieldError errorYear = new FieldError("hackerForm", "expirationYear", form.getExpirationYear(), true, new String[] {
				"actor.creditcardexpired"
			}, new Object[] {
				form.getExpirationYear()
			}, errorMessageYear);
			binding.addError(errorYear);
		}
	}

	public Hacker deconstruct(final HackerForm form) {
		Hacker res = null;
		if (form.getId() == 0)
			res = this.create();
		else {
			res = this.findOne(form.getId());
			Assert.notNull(res);
		}
		res.setVATNumber(form.getVATNumber());
		res.setAddress(form.getAddress());
		res.setEmail(form.getEmail());
		res.setName(form.getName());
		res.setPhone(form.getPhone());
		res.setPhoto(form.getPhoto());
		res.setSurname(form.getSurname());
		res.getUserAccount().setUsername(form.getUsername());
		res.getUserAccount().setPassword(form.getPassword());
		CreditCard creditCard = null;
		if (res.getId() == 0)
			creditCard = new CreditCard();
		else
			creditCard = res.getCreditCard();
		creditCard.setCVVCode(form.getCVVCode());
		creditCard.setExpirationMonth(form.getExpirationMonth());
		creditCard.setExpirationYear(form.getExpirationYear());
		creditCard.setHolderName(form.getHolderName());
		creditCard.setMakeName(form.getMakeName());
		creditCard.setNumber(form.getNumber());
		res.setCreditCard(creditCard);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.HACKER);
		authorities.add(auth);
		res.getUserAccount().setAuthorities(authorities);
		return res;
	}

	public void deleteHacker(final Hacker hacker) {
		Assert.notNull(hacker);
		this.serviceUtils.checkActor(hacker);
		this.serviceUtils.checkAuthority("HACKER");
		final Collection<Application> applications = this.applicationService.findApplicationByHacker(hacker);
		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findProfileByActorId(hacker.getId());
		final CreditCard creditCard = this.creditCardService.findCreditCardByActor(hacker.getId());

		final Finder f = this.finderService.findOneByPrincipal();

		this.messageService.deleteMyMessages();

		for (final Application a : applications) {
			if (a.getCurricula() != null)
				this.curriculaService.deleteCopy(this.curriculaService.findCurriculasByApplicationId(a.getId()).getId());
			this.applicationService.delete1(a);

			final Collection<Application> applications2 = this.applicationService.findAll();
			Assert.isTrue(!(applications2.contains(a)));
		}
		final Collection<Curricula> curriculas = this.curriculaService.findCurriculasByHackerId(hacker.getId());
		for (final Curricula c : curriculas)
			this.curriculaService.delete(c.getId());

		for (final SocialProfile s : socialProfiles)
			this.socialProfileService.delete(s);
		if (f != null)
			this.finderService.delete(f);
		if (creditCard != null)
			this.creditCardService.delete(creditCard);
		this.repository.delete(hacker.getId());
		this.repository.flush();
		final Collection<Actor> actors = this.actorService.findAll();
		Assert.isTrue(!(actors.contains(hacker)));
	}

	public Hacker findHackerByUserAcountId(final int userAccountId) {
		return this.repository.findHackerByUserAcountId(userAccountId);
	}

}
