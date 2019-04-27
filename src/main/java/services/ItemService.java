
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

	//CRUD----------------------------------------------------------------------

	//Others------------------------------------------------------------------------

	//DASHBOARD QUERIES--------------------------------------------------------------
	public Double queryRookiesB1AVG() {
		return this.itemRepository.queryRookiesB1AVG();
	}

	public Double queryRookiesB1MAX() {
		return this.itemRepository.queryRookiesB1MAX();
	}

	public Double queryRookiesB1MIN() {
		return this.itemRepository.queryRookiesB1MIN();
	}

	public Double queryRookiesB1STDDEV() {
		return this.itemRepository.queryRookiesB1STDDEV();
	}

	public List<String> queryRookiesB2() {
		return this.itemRepository.queryRookiesB2();
	}

}
