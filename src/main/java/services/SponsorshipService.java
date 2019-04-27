
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
	@Autowired
	ServiceUtils					serviceUtils;


	//CRUD----------------------------------------------------------------------

	//DASHBOARD QUERIES--------------------------------------------------------------
	public Double queryRookiesA1AVG() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.sponsorshipRepository.queryRookiesA1AVG();
	}

	public Double queryRookiesA1MAX() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.sponsorshipRepository.queryRookiesA1MAX();
	}

	public Double queryRookiesA1MIN() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.sponsorshipRepository.queryRookiesA1MIN();
	}

	public Double queryRookiesA1STDDEV() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.sponsorshipRepository.queryRookiesA1STDDEV();
	}

	public Double queryRookiesA2AVG() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.sponsorshipRepository.queryRookiesA2AVG();
	}

	public Double queryRookiesA2MAX() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.sponsorshipRepository.queryRookiesA2MAX();
	}

	public Double queryRookiesA2MIN() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.sponsorshipRepository.queryRookiesA2MIN();
	}

	public Double queryRookiesA2STDDEV() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.sponsorshipRepository.queryRookiesA2STDDEV();
	}

	public Collection<Provider> queryRookiesA3() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.sponsorshipRepository.queryRookiesA3();
	}

}
