
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EntityExam;

@Repository
public interface EntityExamRepository extends JpaRepository<EntityExam, Integer> {

	@Query("select ee from EntityExam ee where ee.audit.id=?1 and ee.draft = FALSE")
	public Collection<EntityExam> findAllByAuditWithoutDraft(final Integer auditId);

	@Query("select ee from EntityExam ee where ee.audit.id=?1")
	public Collection<EntityExam> findAllByAudit(final Integer auditId);

	@Query("select ee from EntityExam ee where ee.audit.id=?1 and (ee.draft=FALSE or ee.audit.auditor.id = ?2)")
	public Collection<EntityExam> findAllByAuditPrincipal(final Integer auditId, Integer principalId);

	@Query("select ee from EntityExam ee where ee.ticker=?1")
	public EntityExam findByTicker(String ticker);

}
