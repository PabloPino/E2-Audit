
package services;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EntityExamRepository;
import domain.Application;
import domain.EntityExam;
import forms.EntityExamForm;

@Service
@Transactional
public class EntityExamService {

	// Repository

	@Autowired
	private EntityExamRepository	repository;

	// Services

	@Autowired
	private ApplicationService		applicationService;
	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired(required = false)
	private Validator				validator;


	// CRUD

	public EntityExam findOne(final Integer id) {
		return this.repository.findOne(id);
	}

	public Collection<EntityExam> findAll(final Collection<Integer> ids) {
		return this.repository.findAll(ids);
	}

	public Collection<EntityExam> findAll() {
		return this.repository.findAll();
	}

	public EntityExam create() {
		final EntityExam res = new EntityExam();
		res.setDraft(true);
		final Date creationMoment = new Date(System.currentTimeMillis() - 1000);
		res.setTicker(this.generateTicker(creationMoment));
		return res;
	}

	public EntityExam save(final EntityExam ee) {
		final EntityExam entityExam = (EntityExam) this.serviceUtils.checkObjectSave(ee);
		// Se comprueba que tanto la application fuente con destino pertenecen al principal
		this.serviceUtils.checkActor(ee.getApplication().getRookie());
		this.serviceUtils.checkActor(entityExam.getApplication().getRookie());
		// Para ser modificado debe estar en borrador
		Assert.isTrue(entityExam.isDraft());
		// Si es nuevo se ponen parámetros por defecto
		if (entityExam.getId() == 0) {
			final Date creationMoment = new Date(System.currentTimeMillis() - 1000);
			ee.setDraft(true);
			ee.setTicker(this.generateTicker(creationMoment));
		}
		// Si se está editando, hay que tener en cuenta los parámetros no editables
		else {
			ee.setApplication(entityExam.getApplication());
			ee.setPublicationMoment(entityExam.getPublicationMoment());
			ee.setTicker(entityExam.getTicker());
		}
		// Si se guarda y no es borrador, se le da una fecha de publicación
		if (!ee.isDraft() && entityExam.getPublicationMoment() == null)
			ee.setPublicationMoment(new Date(System.currentTimeMillis() - 1000));
		final EntityExam saved = this.repository.save(ee);
		return saved;
	}
	public void delete(final EntityExam ee) {
		final EntityExam entityExam = (EntityExam) this.serviceUtils.checkObject(ee);
		// Se comprueba que tanto la application pertenece al principal
		this.serviceUtils.checkActor(entityExam.getApplication().getRookie());
		// Para ser eliminado debe estar en borrador
		Assert.isTrue(entityExam.isDraft());
		this.repository.delete(entityExam);
	}

	private String generateTicker(final Date moment) {
		boolean unique = false;
		String res = "";
		while (!unique) {
			res = "";
			// Se hace al parte de ticker que depende de la fecha
			res = res + String.valueOf(moment.getYear()).substring(1);
			if (moment.getMonth() + 1 >= 10)
				res = res + String.valueOf(moment.getMonth() + 1);
			else
				res = res + "0" + String.valueOf(moment.getMonth() + 1);
			if (moment.getDate() >= 10)
				res = res + String.valueOf(moment.getDate());
			else
				res = res + "0" + String.valueOf(moment.getDate());
			// Se añade el guión
			res = res + "-";
			// Se hace la parte del ticker que es aleatoria, sólo con máyus
			for (int i = 0; i < 4; i++) {
				final Random rnd = new Random();
				final int randomInt = rnd.nextInt(26) + 65;
				String randomCharacter = String.valueOf((char) randomInt);
				// Para incluir minúsculas
				if (rnd.nextDouble() < 0.5)
					randomCharacter = randomCharacter.toLowerCase();
				res = res + randomCharacter;
			}
			unique = this.findByTicker(res) == null;
		}
		return res;
	}

	public EntityExamForm deconstruct(final EntityExam entityExam) {
		final EntityExamForm form = new EntityExamForm();
		form.setBody(entityExam.getBody());
		form.setDraft(entityExam.isDraft());
		form.setPicture(entityExam.getPicture());
		form.setId(entityExam.getId());
		form.setVersion(entityExam.getVersion());
		return form;
	}

	public EntityExam reconstruct(final EntityExamForm form, final BindingResult binding) {
		EntityExam entityExam = null;
		if (form.getId() == 0)
			entityExam = this.create();
		else {
			entityExam = this.findOne(form.getId());
			Assert.notNull(entityExam);
		}
		entityExam.setApplication(form.getApplication());
		entityExam.setBody(form.getBody());
		entityExam.setDraft(form.isDraft());
		entityExam.setPicture(form.getPicture());
		this.validator.validate(entityExam, binding);
		return entityExam;
	}

	public EntityExam reconstructPruned(final EntityExam ee, final BindingResult binding) {
		if (ee.getId() == 0) {
			ee.setDraft(true);
			final Date creationMoment = new Date(System.currentTimeMillis() - 1000);
			ee.setTicker(this.generateTicker(creationMoment));
		} else {
			final EntityExam entityExam = this.findOne(ee.getId());
			Assert.notNull(entityExam);
			if (!entityExam.isDraft())
				ee.setDraft(entityExam.isDraft());
			ee.setPublicationMoment(entityExam.getPublicationMoment());
			ee.setTicker(entityExam.getTicker());
		}
		this.validator.validate(ee, binding);
		return ee;
	}

	public Collection<EntityExam> findAllByApplicationWithoutDraft(final Integer applicationId) {
		Assert.notNull(this.applicationService.findOne(applicationId));
		return this.repository.findAllByApplicationWithoutDraft(applicationId);
	}

	public Collection<EntityExam> findAllByApplication(final Integer applicationId) {
		Assert.notNull(this.applicationService.findOne(applicationId));
		return this.repository.findAllByApplication(applicationId);
	}

	public EntityExam findByTicker(final String ticker) {
		return this.repository.findByTicker(ticker);
	}

	public void deleteAllApplication(final Application a) {
		final Application application = (Application) this.serviceUtils.checkObject(a);
		for (final EntityExam entityExam : this.findAllByApplication(application.getId()))
			this.repository.delete(entityExam);
	}

}
