
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ItemRepository;

@Service
@Transactional
public class ItemService {

	//Repository---------------------------------------------------------------

	@Autowired
	private ItemRepository	itemRepository;

	//Services------------------------------------------------------------------
	@Autowired
	ServiceUtils			serviceUtils;


	//CRUD----------------------------------------------------------------------

	//Others------------------------------------------------------------------------

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
