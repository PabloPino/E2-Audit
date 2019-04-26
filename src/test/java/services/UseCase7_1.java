
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
import domain.Company;
import domain.CreditCard;
import domain.Hacker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class UseCase7_1 extends AbstractTest {

	// An actor who is not authenticated must be able to register to the system as a company or a hacker.

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
	private DomainEntityRepository	flushRep;


	// Tests

	@Test
	public void testUseCase7_1() {
		// Un usuario anónimo se registra como company (POSITIVO)
		this.registerAsCompanyDriver(null, null);
		// Un usuario registrado se registra como company, no puede porque ya tiene una cuenta (NEGATIVO)
		this.registerAsCompanyDriver("company1", IllegalArgumentException.class);
		// Un usuario anónimo se registra como hacker (POSITIVO)
		this.registerAsHackerDriver(null, null);
		// Un usuario registrado se registra como hacker, no puede porque ya tiene una cuenta (NEGATIVO)
		this.registerAsHackerDriver("hacker1", IllegalArgumentException.class);
	}

	// Drivers

	private void registerAsCompanyDriver(final String username, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final Company company = this.companyService.create();
			final CreditCard creditCard = this.creditCardService.create();
			company.setAddress("address");
			company.setComercialName("comercialName");
			company.setEmail("email@email.com");
			company.setName("name");
			company.setPhone("phone");
			company.setPhoto("http://photo");
			company.setSurname("surname");
			company.setVATNumber("vATNumber");
			company.getUserAccount().setPassword("newCompany" + UseCase7_1.uniqueness);
			company.getUserAccount().setUsername("newCompany" + UseCase7_1.uniqueness);
			creditCard.setCVVCode(123);
			creditCard.setExpirationMonth(12);
			creditCard.setExpirationYear(2021);
			creditCard.setHolderName("holderName");
			creditCard.setMakeName("makeName");
			creditCard.setNumber("1111222233334444");
			UseCase7_1.uniqueness++;
			company.setCreditCard(creditCard);
			final Company savedCompany = this.companyService.save(company);
			Assert.notNull(this.companyService.findOne(savedCompany.getId()));
		} catch (final Throwable t) {
			caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.unauthenticate();
	}

	private void registerAsHackerDriver(final String username, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final Hacker hacker = this.hackerService.create();
			final CreditCard creditCard = this.creditCardService.create();
			hacker.setAddress("address");
			hacker.setEmail("email@email.com");
			hacker.setName("name");
			hacker.setPhone("phone");
			hacker.setPhoto("http://photo");
			hacker.setSurname("surname");
			hacker.setVATNumber("vATNumber");
			hacker.getUserAccount().setPassword("newHacker" + UseCase7_1.uniqueness);
			hacker.getUserAccount().setUsername("newHacker" + UseCase7_1.uniqueness);
			UseCase7_1.uniqueness++;
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
		} catch (final Throwable t) {
			caught = t.getClass();
			super.checkExceptions(expected, caught);
		}
		super.unauthenticate();
	}

}
