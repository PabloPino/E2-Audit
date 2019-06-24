
package exam;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.AuditService;
import services.EntityExamService;
import utilities.AbstractTest;
import domain.Audit;
import domain.EntityExam;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ExamTest extends AbstractTest {

	@Autowired
	private EntityExamService	entityExamService;
	@Autowired
	private AuditService		auditService;


	@Test
	public void ExamTest() {
		// a) A auditor creates one entityexam for one of his/her audits (POSITIVE)
		// b) Positive use case
		// c) 100%
		// d) He/she creates the entityexam correctly.
		this.entityExamCreateTest("auditor1", null);
		// a) A auditor creates one entityexam for another actor's audits (NEGATIVE)
		// b) Negative use case
		// c) 100%
		// d) He/she cannot create the entityexam for an audit that isn't his or hers.
		this.entityExamCreateTest("auditor2", IllegalArgumentException.class);
	}

	private void entityExamCreateTest(final String username, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final Audit audit = this.auditService.findOne(super.getEntityId("audit1"));
			final EntityExam entityExam = this.entityExamService.create();
			entityExam.setAudit(audit);
			entityExam.setBody("body");
			entityExam.setPicture("https://picture");
			final EntityExam saved = this.entityExamService.save(entityExam);
			Assert.notNull(this.entityExamService.findOne(saved.getId()));
		} catch (final Throwable t) {
			caught = t.getClass();
		}
		super.checkExceptions(expected, caught);
		super.unauthenticate();
	}

}
