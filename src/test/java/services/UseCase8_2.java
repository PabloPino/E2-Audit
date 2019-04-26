
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.DomainEntityRepository;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Company;
import domain.CreditCard;
import domain.Hacker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class UseCase8_2 extends AbstractTest {

	// An actor who is not authenticated must be able to edit to the system as a company or a hacker.

	// Services

	private static Integer			uniqueness	= 10;

	@Autowired
	private CompanyService			companyService;
	@Autowired
	private HackerService			hackerService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private CreditCardService		creditCardService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private DomainEntityRepository	flushRep;


	// Tests

	@Test
	public void testUseCase8_2() {
		// Una company edita sus datos personales (POSITIVO)
		this.editCompanyDriver("company1", null);
		// Una company edita los datos personales de otra company (NEGATIVO)
		this.editCompanyDriver("company2", IllegalArgumentException.class);
		// Un hacker edita sus datos personales (POSITIVO)
		this.editHackerDriver("hacker1", null);
		// Un hacker edita los datos personales de otro hacker (NEGATIVO)
		this.editHackerDriver("hacker2", IllegalArgumentException.class);
		// Un administrator edita sus datos personales (POSITIVO)
		this.editAdminDriver("admin1", null);
		// Un administrator edita los datos personales de otro administrator (NEGATIVO)
		this.editAdminDriver("admin2", IllegalArgumentException.class);
	}

	// Drivers

	private void editCompanyDriver(final String username, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final Company company = this.companyService.findOne(super.getEntityId("company1"));
			final CreditCard creditCard = company.getCreditCard();
			final Integer companyVersionBefore = company.getVersion();
			final Integer creditCardVersionBefore = creditCard.getVersion();
			company.setAddress("address");
			company.setComercialName("comercialName");
			company.setEmail("email@email.com");
			company.setName("name");
			company.setPhone("phone");
			company.setPhoto("http://photo");
			company.setSurname("surname");
			company.setVATNumber("vATNumber");
			company.getUserAccount().setPassword("newCompany" + UseCase8_2.uniqueness);
			UseCase8_2.uniqueness++;
			creditCard.setCVVCode(123);
			creditCard.setExpirationMonth(12);
			creditCard.setExpirationYear(2021);
			creditCard.setHolderName("holderName");
			creditCard.setMakeName("makeName");
			creditCard.setNumber("1111222233334444");
			company.setCreditCard(creditCard);
			final Company savedCompany = this.companyService.save(company);
			Assert.notNull(this.companyService.findOne(savedCompany.getId()));
			Assert.isTrue(savedCompany.getVersion() > companyVersionBefore);
			Assert.isTrue(savedCompany.getCreditCard().getVersion() > creditCardVersionBefore);
		} catch (final Throwable t) {
			caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.unauthenticate();
	}
	private void editHackerDriver(final String username, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final Hacker hacker = this.hackerService.findOne(super.getEntityId("hacker1"));
			final CreditCard creditCard = hacker.getCreditCard();
			final Integer hackerVersionBefore = hacker.getVersion();
			final Integer creditCardVersionBefore = creditCard.getVersion();
			hacker.setAddress("address");
			hacker.setEmail("email@email.com");
			hacker.setName("name");
			hacker.setPhone("phone");
			hacker.setPhoto("http://photo");
			hacker.setSurname("surname");
			hacker.setVATNumber("vATNumber");
			hacker.getUserAccount().setPassword("newHacker" + UseCase8_2.uniqueness);
			UseCase8_2.uniqueness++;
			creditCard.setCVVCode(123);
			creditCard.setExpirationMonth(12);
			creditCard.setExpirationYear(2021);
			creditCard.setHolderName("holderName");
			creditCard.setMakeName("makeName");
			creditCard.setNumber("1111222233334444");
			hacker.setCreditCard(creditCard);
			final Hacker savedHacker = this.hackerService.save(hacker);
			this.flushRep.flush();
			Assert.notNull(this.hackerService.findOne(savedHacker.getId()));
			Assert.isTrue(savedHacker.getVersion() > hackerVersionBefore);
			Assert.isTrue(savedHacker.getCreditCard().getVersion() > creditCardVersionBefore);
		} catch (final Throwable t) {
			caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.unauthenticate();
	}

	private void editAdminDriver(final String username, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final Administrator administrator = this.administratorService.findOne(super.getEntityId("administrator1"));
			final CreditCard creditCard = administrator.getCreditCard();
			final Integer administratorVersionBefore = administrator.getVersion();
			final Integer creditCardVersionBefore = creditCard.getVersion();
			administrator.setAddress("address");
			administrator.setEmail("email@email.com");
			administrator.setName("name");
			administrator.setPhone("phone");
			administrator.setPhoto("http://photo");
			administrator.setSurname("surname");
			administrator.setVATNumber("vATNumber");
			administrator.getUserAccount().setPassword("newAdministrator" + UseCase8_2.uniqueness);
			UseCase8_2.uniqueness++;
			creditCard.setCVVCode(123);
			creditCard.setExpirationMonth(12);
			creditCard.setExpirationYear(2021);
			creditCard.setHolderName("holderName");
			creditCard.setMakeName("makeName");
			creditCard.setNumber("1111222233334444");
			administrator.setCreditCard(creditCard);
			final Administrator savedAdministrator = this.administratorService.save(administrator);
			this.flushRep.flush();
			Assert.notNull(this.administratorService.findOne(savedAdministrator.getId()));
			Assert.isTrue(savedAdministrator.getVersion() > administratorVersionBefore);
			Assert.isTrue(savedAdministrator.getCreditCard().getVersion() > creditCardVersionBefore);
		} catch (final Throwable t) {
			caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.unauthenticate();
	}
}
