
package servicesRookies;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.AuditorService;
import services.CreditCardService;
import utilities.AbstractTest;
import domain.Auditor;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterAuditor extends AbstractTest {

	@Autowired
	private AuditorService		auditorService;

	@Autowired
	private CreditCardService	creditCardService;

	private static Integer		randomInt	= 10;


	@Test
	public void registerAuditorTest() {
		// An administrator registers an auditor
		this.registerAuditorDriver("admin1", null);
		// An unauthenticated user registers an auditor
		this.registerAuditorDriver(null, IllegalArgumentException.class);
	}

	public void registerAuditorDriver(final String username, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(username);
			final Auditor auditor = this.auditorService.create();
			final CreditCard creditCard = this.creditCardService.create();
			creditCard.setCVVCode(100);
			creditCard.setExpirationMonth(12);
			creditCard.setExpirationYear(2024);
			creditCard.setHolderName("holderName");
			creditCard.setMakeName("makeName");
			creditCard.setNumber("1111222233334444");
			auditor.getUserAccount().setPassword("newAuditor" + RegisterAuditor.randomInt);
			auditor.getUserAccount().setUsername("newAuditor" + RegisterAuditor.randomInt);
			auditor.setAddress("address");
			auditor.setCreditCard(creditCard);
			auditor.setEmail("email@email");
			auditor.setName("anme");
			auditor.setPhone("1234");
			auditor.setPhoto("http://photo");
			auditor.setSurname("surname");
			auditor.setVATNumber("100");
			final Auditor savedAuditor = this.auditorService.save(auditor);
			Assert.notNull(this.auditorService.findOne(savedAuditor.getId()));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
		RegisterAuditor.randomInt++;
	}
}
