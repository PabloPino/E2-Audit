
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

	//CRUD----------------------------------------------------------------------

	//Others------------------------------------------------------------------------

	//DASHBOARD QUERIES--------------------------------------------------------------
	public Double queryRookiesC1AVG() {
		return this.auditRepository.queryRookiesC1AVG();
	}

	public Double queryRookiesC1MAX() {
		return this.auditRepository.queryRookiesC1MAX();
	}

	public Double queryRookiesC1MIN() {
		return this.auditRepository.queryRookiesC1MIN();
	}

	public Double queryRookiesC1STDDEV() {
		return this.auditRepository.queryRookiesC1STDDEV();
	}

	public Double queryRookiesC2AVG() {
		return this.auditRepository.queryRookiesC2AVG();
	}

	public Double queryRookiesC2MAX() {
		return this.auditRepository.queryRookiesC2MAX();
	}

	public Double queryRookiesC2MIN() {
		return this.auditRepository.queryRookiesC2MIN();
	}

	public Double queryRookiesC2STDDEV() {
		return this.auditRepository.queryRookiesC2STDDEV();
	}

	public List<String> queryRookiesC3() {
		return this.auditRepository.queryRookiesC3();
	}

	public Double queryRookiesC4() {
		return this.auditRepository.queryRookiesC4();
	}

}
