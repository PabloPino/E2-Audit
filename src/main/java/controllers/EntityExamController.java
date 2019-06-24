
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AuditService;
import services.ConfigurationService;
import services.EntityExamService;
import domain.Actor;
import domain.Audit;
import domain.Auditor;
import domain.EntityExam;

@Controller
@RequestMapping("entityexam")
public class EntityExamController extends AbstractController {

	@Autowired
	private EntityExamService		entityExamService;
	@Autowired
	private AuditService			auditService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping("all/list")
	public ModelAndView list(@RequestParam(required = true) final Integer auditId) {
		final ModelAndView res = new ModelAndView("entityexam/list");
		final Audit audit = this.auditService.findOne(auditId);
		Integer principalId = null;
		try {
			principalId = this.actorService.findPrincipal().getId();
		} catch (final Throwable t) {
			principalId = 0;
		}
		res.addObject("entityExams", this.entityExamService.findAllByAuditPrincipal(auditId, principalId));
		res.addObject("requestURI", "entityexam/actor/list.do?auditId=" + auditId);
		res.addObject("audit", audit);
		res.addObject("banner", this.configurationService.findOne().getBanner());
		return res;
	}

	@RequestMapping("auditor/list")
	public ModelAndView listDraftIncluded(@RequestParam(required = true) final Integer auditId) {
		final ModelAndView res = new ModelAndView("entityexam/list");
		final Audit audit = this.auditService.findOne(auditId);
		Integer principalId = null;
		try {
			principalId = this.actorService.findPrincipal().getId();
		} catch (final Throwable t) {
			principalId = 0;
		}
		res.addObject("entityExams", this.entityExamService.findAllByAuditPrincipal(auditId, principalId));
		res.addObject("requestURI", "entityexam/auditor/list.do?auditId=" + auditId);
		res.addObject("audit", audit);
		res.addObject("banner", this.configurationService.findOne().getBanner());
		return res;
	}

	@RequestMapping("auditor/create")
	public ModelAndView create() {
		return this.createEditModelAndView(this.entityExamService.create(), true);
	}

	@RequestMapping("auditor/edit")
	public ModelAndView edit(@RequestParam(required = true) final Integer entityExamId) {
		final EntityExam entityExam = this.entityExamService.findOne(entityExamId);
		Assert.notNull(entityExam);
		return this.createEditModelAndView(entityExam, false);
	}

	@RequestMapping(value = "auditor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final EntityExam ee, final BindingResult binding) {
		ModelAndView res = null;
		final boolean creating = ee.getId() == 0;
		final EntityExam entityExam = this.entityExamService.reconstructPruned(ee, binding);
		if (binding.hasErrors())
			res = this.createEditModelAndView(entityExam, creating);
		else
			try {
				final EntityExam saved = this.entityExamService.save(entityExam);
				res = new ModelAndView("redirect:list.do?auditId=" + saved.getAudit().getId());
			} catch (final Throwable t) {
				res = this.createEditModelAndView(entityExam, "cannot.commit.error", creating);
			}
		return res;
	}

	@RequestMapping(value = "auditor/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView edit(final EntityExam ee) {
		ModelAndView res = null;
		try {
			this.entityExamService.delete(ee);
			res = new ModelAndView("redirect:/audit/list.do");
		} catch (final Throwable t) {
			res = this.createEditModelAndView(ee, "cannot.commit.error", false);
		}
		return res;
	}
	@RequestMapping("auditor/display")
	public ModelAndView display(@RequestParam(required = true) final Integer entityExamId) {
		final ModelAndView res = new ModelAndView("entityexam/display");
		final EntityExam entityExam = this.entityExamService.findOne(entityExamId);
		Assert.notNull(entityExam);
		res.addObject("entityExam", entityExam);
		res.addObject("banner", this.configurationService.findOne().getBanner());
		return res;
	}

	private ModelAndView createEditModelAndView(final EntityExam entityExam, final boolean creating) {
		return this.createEditModelAndView(entityExam, null, creating);
	}

	private ModelAndView createEditModelAndView(final EntityExam entityExam, final String message, final boolean creating) {
		ModelAndView res = null;
		if (creating)
			res = new ModelAndView("entityexam/create");
		else
			res = new ModelAndView("entityexam/edit");
		boolean isDraft = true;
		final Actor principal = this.actorService.findPrincipal();
		res.addObject("entityExam", entityExam);
		res.addObject("message", message);
		res.addObject("audits", this.auditService.findAuditsByAuditor((Auditor) principal));
		res.addObject("banner", this.configurationService.findOne().getBanner());
		try {
			if (entityExam.getId() > 0)
				isDraft = this.entityExamService.findOne(entityExam.getId()).isDraft();
		} catch (final Throwable t) {
		}
		String auditorUsername = null;
		try {
			final EntityExam saved = this.entityExamService.findOne(entityExam.getId());
			auditorUsername = saved.getAudit().getAuditor().getUserAccount().getUsername();
		} catch (final Throwable t) {
			auditorUsername = "";
		}
		res.addObject("auditorUsername", auditorUsername);
		res.addObject("isDraft", isDraft);
		return res;
	}
}
