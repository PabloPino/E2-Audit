
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.AuditRepository;

@Service
@Transactional
public class AuditService {

	//Repository---------------------------------------------------------------

	@Autowired
	private AuditRepository	auditRepository;

	//Services------------------------------------------------------------------
	@Autowired
	ServiceUtils			serviceUtils;


	//CRUD----------------------------------------------------------------------

	//Others------------------------------------------------------------------------

	//DASHBOARD QUERIES--------------------------------------------------------------
	public Double queryRookiesC1AVG() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.auditRepository.queryRookiesC1AVG();
	}

	public Double queryRookiesC1MAX() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.auditRepository.queryRookiesC1MAX();
	}

	public Double queryRookiesC1MIN() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.auditRepository.queryRookiesC1MIN();
	}

	public Double queryRookiesC1STDDEV() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.auditRepository.queryRookiesC1STDDEV();
	}

	public Double queryRookiesC2AVG() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.auditRepository.queryRookiesC2AVG();
	}

	public Double queryRookiesC2MAX() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.auditRepository.queryRookiesC2MAX();
	}

	public Double queryRookiesC2MIN() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.auditRepository.queryRookiesC2MIN();
	}

	public Double queryRookiesC2STDDEV() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.auditRepository.queryRookiesC2STDDEV();
	}

	public List<String> queryRookiesC3() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.auditRepository.queryRookiesC3();
	}

	public Double queryRookiesC4() {
		this.serviceUtils.checkAuthority("ADMIN");
		return this.auditRepository.queryRookiesC4();
	}

}
