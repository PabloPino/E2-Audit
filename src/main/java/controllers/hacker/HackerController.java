
package controllers.hacker;

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
import security.UserAccount;
import services.ActorService;
import services.ConfigurationService;
import services.HackerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Hacker;
import forms.HackerForm;

@Controller
@RequestMapping("/hacker")
public class HackerController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public HackerController() {
		super();
	}


	//-----------------Services-------------------------

	@Autowired
	HackerService					hackerService;

	@Autowired
	ActorService					actorService;

	//	@Autowired
	//	SocialProfileService			socialProfileService;

	@Autowired
	private ConfigurationService	configurationService;


	//	@Autowired
	//	UserAccount				userAccountService;

	// Action-1 ---------------------------------------------------------------

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("hacker/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("hacker/action-2");

		return result;
	}

	//-----------------Display-------------------------

	//display creado para mostrar al hacker logueado
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Hacker hacker;

		hacker = (Hacker) this.actorService.findByUserAccount(LoginService.getPrincipal());
		result = new ModelAndView("hacker/edit");
		result.addObject("hacker", hacker);
		result.addObject("banner", this.configurationService.findOne().getBanner());

		return result;
	}

	//------------------Edit---------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int hackerId) {
		ModelAndView result;
		Hacker hacker;

		hacker = this.hackerService.findOne(hackerId);
		Assert.notNull(hacker);
		result = this.createEditModelAndView(hacker);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final HackerForm hackerForm, final BindingResult binding) {
		ModelAndView result;
		Hacker hacker = null;

		this.hackerService.validateForm(hackerForm, binding);
		if (binding.hasErrors()) {
			hacker = this.hackerService.deconstruct(hackerForm);
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(hacker);
			result.addObject("message", "hacker.commit.error");
		} else

			try {
				hacker = this.hackerService.deconstruct(hackerForm);
				this.hackerService.save(hacker);
				result = new ModelAndView("redirect:display.do");

			} catch (final Throwable oops) {
				final Actor test = this.actorService.findActorByUsername(hackerForm.getUsername());
				if (test != null)
					result = this.createEditModelAndView(hacker, "actor.userExists");
				else
					result = this.createEditModelAndView(hacker, "hacker.commit.error");

			}

		return result;
	}

	//---------------------------create------------------------------------------------------
	// Creation
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Hacker hacker;

		hacker = this.hackerService.create();

		result = new ModelAndView("hacker/create");
		result.addObject("hacker", hacker);
		return result;
	}

	@RequestMapping(value = "/deleteHacker", method = RequestMethod.GET)
	public ModelAndView deleteAllData() {

		ModelAndView result;
		final Actor s = this.actorService.findPrincipal();
		try {
			this.hackerService.deleteHacker((Hacker) s);
			result = new ModelAndView("redirect:/j_spring_security_logout");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
			System.out.println("NO SE HA PODIDO BORRAR EL USUARIO");
		}
		return result;
	}

	//-----------------List----------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {
		final ModelAndView result;
		//		final List<Hacker> hackers = this.hackerService.listHackersByBrotherhood(brotherhoodId);
		//
		result = new ModelAndView("hacker/list");
		//		result.addObject("requestURI", "hacker/list.do");
		//		result.addObject("hackers", hackers);

		return result;
	}
	//---------------------------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Hacker hacker) {
		ModelAndView result;

		result = this.createEditModelAndView(hacker, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Hacker hacker, final String messageCode) {
		final ModelAndView result;
		UserAccount userAccount = new UserAccount();
		userAccount = hacker.getUserAccount();

		result = new ModelAndView("hacker/edit");
		result.addObject("hacker", hacker);
		result.addObject("userAccount", userAccount);
		result.addObject("message", messageCode);

		return result;
	}

}
