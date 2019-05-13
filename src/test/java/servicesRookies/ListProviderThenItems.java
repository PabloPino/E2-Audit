
package servicesRookies;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ItemService;
import services.ProviderService;
import utilities.AbstractTest;
import domain.Item;
import domain.Provider;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ListProviderThenItems extends AbstractTest {

	@Autowired
	private ProviderService	providerService;
	@Autowired
	private ItemService		itemService;


	@Test
	public void listProviderThenItemsTest() {
		// An actor list the providers, then it list a provider's items
		this.listProviderThenItemsDriver(null, null, null);
		// An actor list the providers, then it list a nonexistent provider's items
		this.listProviderThenItemsDriver(1, null, NullPointerException.class);
	}

	public void listProviderThenItemsDriver(final Integer providerId, final String username, final Class<?> expected) {
		Class<?> caught = null;
		try {
			super.authenticate(username);
			final List<Provider> providers = this.providerService.findAll();
			Provider provider = null;
			if (providerId == null)
				provider = providers.get(0);
			else
				provider = this.providerService.findOne(providerId);
			final Collection<Item> items = this.itemService.findItemsByProviderId(provider.getId());
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
