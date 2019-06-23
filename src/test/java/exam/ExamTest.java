
package exam;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ApplicationService;
import services.EntityExamService;
import utilities.AbstractTest;
import domain.Application;
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
	private ApplicationService	applicationService;


	@Test
	public void ExamTest() {
		// a) A company lists the examentities of one of their applications (POSITIVE)
		// b) Positive use case
		// c) 97.7%
		// d) A company lists the examentities of one of their applications correctly
		this.entityExamCompanyListTest("company1", "application6", null);
		// a) A company lists the examentities of a null application (NEGATIVE)
		// b) Negative use case
		// c) 97.7%
		// d) He/she cannot list the entityexams because the application doesn't exist
		this.entityExamCompanyListTest("company1", null, NullPointerException.class);
		// a) A rookie lists the examentities of one of their applications (POSITIVE)
		// b) Positive use case
		// c) 97.7%
		// d) A rookie lists the examentities of one of their applications correctly
		this.entityExamRookieListTest("rookie1", "application6", null);
		// a) A rookie lists the examentities of a null application (NEGATIVE)
		// b) Negative use case
		// c) 97.7%
		// d) He/she cannot list the entityexams because the application doesn't exist
		this.entityExamRookieListTest("rookie1", null, NullPointerException.class);
		// a) A rookie displays one of his/her examentities (POSITIVE)
		// b) Positive use case
		// c) 100%
		// d) He/she displays the examentity correctly.
		this.entityExamDisplayTest("rookie1", "entityExam1TEST", null);
		// a) A rookie displays one of his/her examentities (NEGATIVE)
		// b) Negative use case
		// c) 100%
		// d) He/she cannot displays the examentity because it doesn't exists
		this.entityExamDisplayTest("rookie1", null, IllegalArgumentException.class);
		// a) A rookie creates one entityexam for one of his/her applications (POSITIVE)
		// b) Positive use case
		// c) 100%
		// d) He/she creates the entityexam correctly.
		this.entityExamCreateTest("rookie1", null);
		// a) A rookie creates one entityexam for another actor's applications (NEGATIVE)
		// b) Negative use case
		// c) 100%
		// d) He/she cannot create the entityexam for an application that isn't his or hers.
		this.entityExamCreateTest("rookie2", IllegalArgumentException.class);
		// a) A rookie edits one of his/her entityexams (POSITIVE)
		// b) Positive use case
		// c) 98.5%
		// d) He/she edits the entityexam correctly.
		this.entityExamEditTest("rookie1", null);
		// a) A rookie edits another actor's entityexam (NEGATIVE)
		// b) Negative use case
		// c) 98.5%
		// d) He/she cannot edit another actor's entityexam.
		this.entityExamEditTest("rookie2", IllegalArgumentException.class);
		// a) A rookie deletes another actor's entityexam (NEGATIVE)
		// b) Negative use case
		// c) 100%
		// d) He/she cannot delete another actor's entityexam.
		this.entityExamDeleteTest("rookie2", IllegalArgumentException.class);
		// a) A rookie deletes one of his/her entityexams (POSITIVE)
		// b) Positive use case
		// c) 100%
		// d) He/she deletes the entityexam correctly.
		this.entityExamDeleteTest("rookie1", null);
	}

	private void entityExamRookieListTest(final String username, final String applicationId, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			Application application = null;
			if (applicationId != null)
				application = this.applicationService.findOne(super.getEntityId(applicationId));
			final Collection<EntityExam> entityExams = this.entityExamService.findAllByApplication(application.getId());
			Assert.isTrue(entityExams.size() == 2);
		} catch (final Throwable t) {
			caught = t.getClass();
		}
		super.checkExceptions(expected, caught);
		super.unauthenticate();
	}

	private void entityExamCompanyListTest(final String username, final String applicationId, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			Application application = null;
			if (applicationId != null)
				application = this.applicationService.findOne(super.getEntityId(applicationId));
			final Collection<EntityExam> entityExams = this.entityExamService.findAllByApplicationWithoutDraft(application.getId());
			Assert.isTrue(entityExams.size() == 1);
		} catch (final Throwable t) {
			caught = t.getClass();
		}
		super.checkExceptions(expected, caught);
		super.unauthenticate();
	}

	private void entityExamCreateTest(final String username, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final Application application = this.applicationService.findOne(super.getEntityId("application6"));
			final EntityExam entityExam = this.entityExamService.create();
			entityExam.setApplication(application);
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

	private void entityExamEditTest(final String username, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final Application application = this.applicationService.findOne(super.getEntityId("application6"));
			final EntityExam entityExam = this.entityExamService.findOne(super.getEntityId("entityExam2TEST"));
			final Integer oldVersion = entityExam.getVersion();
			entityExam.setApplication(application);
			entityExam.setBody("body");
			entityExam.setPicture("https://picture");
			final EntityExam saved = this.entityExamService.save(entityExam);
			Assert.notNull(this.entityExamService.findOne(saved.getId()));
			Assert.isTrue(oldVersion + 1 == saved.getVersion());
		} catch (final Throwable t) {
			caught = t.getClass();
		}
		super.checkExceptions(expected, caught);
		super.unauthenticate();
	}

	private void entityExamDeleteTest(final String username, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			final EntityExam entityExam = this.entityExamService.findOne(super.getEntityId("entityExam2TEST"));
			this.entityExamService.delete(entityExam);
			Assert.isNull(this.entityExamService.findOne(entityExam.getId()));
		} catch (final Throwable t) {
			caught = t.getClass();
		}
		super.checkExceptions(expected, caught);
		super.unauthenticate();
	}

	private void entityExamDisplayTest(final String username, final String entityExamId, final Class<?> expected) {
		super.authenticate(username);
		Class<?> caught = null;
		try {
			EntityExam entityExam = null;
			if (entityExamId != null)
				entityExam = this.entityExamService.findOne(super.getEntityId(entityExamId));
			Assert.notNull(entityExam);
		} catch (final Throwable t) {
			caught = t.getClass();
		}
		super.checkExceptions(expected, caught);
		super.unauthenticate();
	}

}
