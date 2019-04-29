
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.AuditorRepository;
import domain.Auditor;

@Service
@Transactional
public class AuditorService {

	//Repository-----------------------------------------------------------------

	@Autowired
	private AuditorRepository	auditorRepository;


	//Utiles----------------------------------------------------------------------

	public Auditor findAuditorByUserAcountId(final int userAccountId) {
		return this.auditorRepository.findAuditorByUserAcountId(userAccountId);
	}

}
