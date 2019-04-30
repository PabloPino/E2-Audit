
package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AuditService;
import services.AuditorService;
import services.CompanyService;
import services.ConfigurationService;
import services.HackerService;
import services.MessageService;
import services.PositionService;
import services.ProblemService;
import services.ServiceUtils;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	//-----------------Services-------------------------

	@Autowired
	AuditService			auditService;

	@Autowired
	AuditorService			auditorService;

	@Autowired
	CompanyService			companyService;

	@Autowired
	ConfigurationService	configurationService;

	@Autowired
	ProblemService			problemService;

	@Autowired
	PositionService			positionService;

	@Autowired
	MessageService			messageService;

	@Autowired
	HackerService			hackerService;

	@Autowired
	ServiceUtils			serviceUtils;


	//-------------------------- List ----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer auditorId) {
		ModelAndView modelAndView;
		final Auditor auditor = this.auditorService.findAuditorByUserAcountId(LoginService.getPrincipal().getId());
		final List<Audit> audits = this.auditService.findAuditsByAuditor(auditor);

		modelAndView = new ModelAndView("audit/list");
		modelAndView.addObject("audits", audits);
		modelAndView.addObject("requestURI", "audit/list.do");
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());

		return modelAndView;

	}
	//-------------------------- List ----------------------------------
	//	@RequestMapping(value = "/listtwo", method = RequestMethod.GET)
	//	public ModelAndView listtwo() {
	//		ModelAndView modelAndView;
	//		Date now;
	//
	//		now = new Date(System.currentTimeMillis() - 1000);
	//		final Collection<Position> audits = this.auditService.findFinalPositionsWithoutDeadline();
	//		final int userAccountId = LoginService.getPrincipal().getId();
	//		final Hacker hacker = this.hackerService.findHackerByUserAcountId(userAccountId);
	//		final int hackerId = hacker.getId();
	//		final List<Position> audits2 = this.auditService.findPositionByHackerId(hackerId);
	//		audits.removeAll(audits2);
	//
	//		modelAndView = new ModelAndView("audit/list");
	//		modelAndView.addObject("audits", audits);
	//		modelAndView.addObject("now", now);
	//		modelAndView.addObject("requestURI", "audit/listtwo.do");
	//		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
	//
	//		return modelAndView;
	//
	//	}

	//Searching-------------------------------------------------------------

	//	@RequestMapping(value = "/searchResult", method = RequestMethod.POST, params = "search")
	//	public ModelAndView search(@RequestParam final String keyword) {
	//		final ModelAndView modelAndView = this.list(null);
	//
	//		final List<Position> audits = this.auditService.searchingPositions(keyword);
	//
	//		modelAndView.addObject("audits", audits);
	//		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());
	//
	//		return modelAndView;
	//	}

	//	//-----------------Display-------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditId) {
		ModelAndView result;
		final Audit audit;

		audit = this.auditService.findOne(auditId);

		result = new ModelAndView("audit/display");
		result.addObject("audit", audit);
		result.addObject("banner", this.configurationService.findOne().getBanner());

		return result;
	}

	//	//------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int positionId) {
		ModelAndView result;
		Audit audit;
		audit = this.auditService.create(positionId);
		final Auditor auditor = this.auditorService.findAuditorByUserAcountId(LoginService.getPrincipal().getId());
		final Position position = this.positionService.findOne(positionId);

		result = this.createEditModelAndView(audit);
		//		result.addObject("position", position);
		//		result.addObject("auditor", auditor);

		return result;

	}
	//
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView result;
		final Audit audit;

		audit = this.auditService.findOne(auditId);
		Assert.notNull(audit);

		result = this.createEditModelAndView(audit);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Audit audit, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(audit);
			System.out.println(binding.getAllErrors());
			audit.setFinalMode(false);

			if (binding.getAllErrors().toString().contains("borrador"))
				result = this.createEditModelAndView(audit, "audit.finalMode.error.borrador");
			else
				result = this.createEditModelAndView(audit);

		} else
			try {
				this.auditService.save(audit);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				audit.setFinalMode(false);
				result = this.createEditModelAndView(audit, "audit.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Audit audit, final String message) {
		final ModelAndView result;
		Boolean readonly = null;

		if (audit.isFinalMode() == true)
			readonly = true;

		result = new ModelAndView("audit/edit");
		result.addObject("audit", audit);

		result.addObject("banner", this.configurationService.findOne().getBanner());
		result.addObject("message", message);
		result.addObject("readonly", readonly);

		result.addObject("requestURI", "audit/edit.do");

		return result;
	}
	//	//
	protected ModelAndView createEditModelAndView(final Audit audit) {
		ModelAndView result;

		result = this.createEditModelAndView(audit, null);

		return result;
	}

	//
	//
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit, final BindingResult binding) {
		ModelAndView result;
		try {
			this.auditService.delete(audit);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(audit, "audit.commit.error");
			System.out.println("El error en el delete de audit es por --->" + binding.hasErrors());
			System.out.println(binding.getAllErrors());

		}
		return result;
	}

	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assign(final int positionId) {
		ModelAndView result;
		final Audit audit = this.auditService.create(positionId);
		final Position position = this.positionService.findOne(positionId);
		position.setAudit(audit);
		this.positionService.saveForAudits(position);

		result = this.createEditModelAndView(audit);
		//result.addObject("auditor", auditor);

		return result;

	}
}
