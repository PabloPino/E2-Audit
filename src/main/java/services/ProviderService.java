
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Provider;
import repositories.ProviderRepository;

@Service
@Transactional
public class ProviderService {

	//Repository-----------------------------------------------------------------------

	@Autowired
	private ProviderRepository providerRepository;

	//CRUD----------------------------------------------------------------------------


	//Other methods--------------------------------------------------------------------------------

	public Provider findProviderByUserAcountId(final int userAccountId) {
		return this.providerRepository.findProviderByUserAcountId(userAccountId);
	}
}
