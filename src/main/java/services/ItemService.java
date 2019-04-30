
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Item;
import domain.Provider;
import repositories.ItemRepository;
import security.LoginService;

@Service
@Transactional
public class ItemService {

	//Repository---------------------------------------------------------------

	@Autowired
	private ItemRepository	itemRepository;

	//Services------------------------------------------------------------------
	@Autowired
	ServiceUtils			serviceUtils;

	@Autowired
	private ProviderService	providerService;


	//CRUD----------------------------------------------------------------------

	public Item create() {
		final Item item = new Item();
		final Provider provider = this.providerService.findProviderByUserAcountId(LoginService.getPrincipal().getId());

		item.setProvider(provider);
		item.setPictures(new ArrayList<String>());
		item.setLink(new ArrayList<String>());

		return item;
	}

	public Item save(final Item item) {
		Assert.notNull(item);
		this.checkPrincipal(item);

		final Item saved = this.itemRepository.save(item);

		return saved;
	}

	public List<Item> findAll() {
		return this.itemRepository.findAll();
	}

	public Item findOne(final Integer id) {
		return this.itemRepository.findOne(id);
	}

	public void delete(final Item item) {
		this.checkPrincipal(item);
		this.itemRepository.delete(item);
	}

	//Others------------------------------------------------------------------------

	private boolean checkPrincipal(final Item item) {
		final Provider provider = item.getProvider();
		final Provider principal = this.providerService.findProviderByUserAcountId(LoginService.getPrincipal().getId());

		Assert.isTrue(provider.getId() == principal.getId());

		return true;
	}

	//DASHBOARD QUERIES--------------------------------------------------------------
	public Double queryRookiesB1AVG() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.itemRepository.queryRookiesB1AVG();
	}

	public Double queryRookiesB1MAX() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.itemRepository.queryRookiesB1MAX();
	}

	public Double queryRookiesB1MIN() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.itemRepository.queryRookiesB1MIN();
	}

	public Double queryRookiesB1STDDEV() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.itemRepository.queryRookiesB1STDDEV();
	}

	public List<String> queryRookiesB2() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.itemRepository.queryRookiesB2();
	}

}
