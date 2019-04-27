
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SponsorshipRepository;
import domain.Provider;

@Service
@Transactional
public class SponsorshipService {

	//Repository---------------------------------------------------------------

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	//Services------------------------------------------------------------------

	//CRUD----------------------------------------------------------------------

	//DASHBOARD QUERIES--------------------------------------------------------------
	public Double queryRookiesA1AVG() {
		return this.sponsorshipRepository.queryRookiesA1AVG();
	}

	public Double queryRookiesA1MAX() {
		return this.sponsorshipRepository.queryRookiesA1MAX();
	}

	public Double queryRookiesA1MIN() {
		return this.sponsorshipRepository.queryRookiesA1MIN();
	}

	public Double queryRookiesA1STDDEV() {
		return this.sponsorshipRepository.queryRookiesA1STDDEV();
	}

	public Double queryRookiesA2AVG() {
		return this.sponsorshipRepository.queryRookiesA2AVG();
	}

	public Double queryRookiesA2MAX() {
		return this.sponsorshipRepository.queryRookiesA2MAX();
	}

	public Double queryRookiesA2MIN() {
		return this.sponsorshipRepository.queryRookiesA2MIN();
	}

	public Double queryRookiesA2STDDEV() {
		return this.sponsorshipRepository.queryRookiesA2STDDEV();
	}

	public Collection<Provider> queryRookiesA3() {
		return this.sponsorshipRepository.queryRookiesA3();
	}

}
