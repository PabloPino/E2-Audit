
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EntityExam;

@Repository
public interface EntityExamRepository extends JpaRepository<EntityExam, Integer> {

	@Query("select ee from EntityExam ee where ee.application.id=?1 and ee.draft = FALSE")
	public Collection<EntityExam> findAllByApplicationWithoutDraft(final Integer applicationId);

	@Query("select ee from EntityExam ee where ee.application.id=?1")
	public Collection<EntityExam> findAllByApplication(final Integer applicationId);

	@Query("select ee from EntityExam ee where ee.ticker=?1")
	public EntityExam findByTicker(String ticker);

}
