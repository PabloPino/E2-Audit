
package controllers.provider;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ConfigurationService;
import services.PositionService;
import services.ProviderService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Provider;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/provider")
public class SponsorshipProviderController extends AbstractController {

	//-----------------Services-------------------------
	@Autowired
	SponsorshipService		sponsorshipService;

	@Autowired
	ProviderService			providerService;

	@Autowired
	ConfigurationService	configurationService;

	@Autowired
	PositionService			positionService;


	//-------------------------- List ----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView;
		final Provider provider = this.providerService.findProviderByUserAcountId(LoginService.getPrincipal().getId());

		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findSponsorshipsByProviderId(provider.getId());

		modelAndView = new ModelAndView("sponsorship/list");
		modelAndView.addObject("sponsorships", sponsorships);
		modelAndView.addObject("requestURI", "sponsorships/list.do");
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());

		return modelAndView;

	}

	//	//------------------------------------------
	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView create() {
	//		ModelAndView result;
	//		final Sponsorship sponsorship;
	//
	//		sponsorship = this.sponsorshipService.create();
	//
	//		result = this.createEditModelAndView(sponsorship);
	//
	//		return result;
	//
	//	}
	//
	//	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
	//		ModelAndView result;
	//
	//		result = this.createEditModelAndView(sponsorship, null);
	//
	//		return result;
	//	}
	//
	//	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String messageCode) {
	//		final ModelAndView result;
	//		Provider provider;
	//		provider = this.providerService.findProviderByUserAcountId(LoginService.getPrincipal().getId());
	//		Collection<Position> positions = new ArrayList<>();
	//		positions = this.positionService.findPositionsByProviderIdNotCanceled(provider.getId());
	//
	//		result = new ModelAndView("sponsorship/edit");
	//		result.addObject("sponsorship", sponsorship);
	//		result.addObject("positions", positions);
	//		result.addObject("message", messageCode);
	//		result.addObject("banner", this.configurationService.findOne().getBanner());
	//
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		if (binding.hasErrors()) {
	//			result = this.createEditModelAndView(sponsorship);
	//			System.out.println(binding.getAllErrors());
	//			sponsorship.setFinalMode(false);
	//
	//			if (binding.getAllErrors().toString().contains("URL"))
	//				result = this.createEditModelAndView(sponsorship, "sponsorship.URL.error");
	//			else
	//				result = this.createEditModelAndView(sponsorship);
	//
	//		} else
	//			try {
	//				if (this.sponsorshipService.checkEquals(sponsorship)) {
	//					sponsorship.setFinalMode(false);
	//					result = this.createEditModelAndView(sponsorship, "sponsorship.equalsRecord.error");
	//				} else {
	//					this.sponsorshipService.save(sponsorship);
	//					result = new ModelAndView("redirect:/sponsorship/provider/list.do");
	//				}
	//			} catch (final Throwable oops) {
	//				sponsorship.setFinalMode(false);
	//				System.out.println(oops.getMessage() + "--" + oops.getLocalizedMessage() + "--" + oops.getCause());
	//				result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
	//			}
	//		return result;
	//	}
	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam final int sponsorshipId) {
	//		ModelAndView result;
	//		Sponsorship sponsorship;
	//		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
	//		Assert.notNull(sponsorship);
	//		if (this.sponsorshipService.isFinalMode(sponsorship))
	//			result = new ModelAndView("redirect:/");
	//		else
	//			result = this.createEditModelAndView(sponsorship);
	//
	//		return result;
	//
	//	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	//	public ModelAndView delete(final Sponsorship sponsorship, final BindingResult binding) {
	//		ModelAndView result;
	//		try {
	//			Assert.isTrue(!this.sponsorshipService.isFinalMode(sponsorship));
	//			this.sponsorshipService.delete(sponsorship);
	//			result = new ModelAndView("redirect:list.do");
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
	//
	//		}
	//		return result;
	//	}
	//
}
