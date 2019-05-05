
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ProviderService;
import domain.Actor;
import domain.Provider;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	@Autowired
	private ProviderService	providerService;
	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/deleteProvider", method = RequestMethod.GET)
	public ModelAndView deleteAllData() {

		ModelAndView result;
		final Actor s = this.actorService.findPrincipal();
		try {
			this.providerService.deleteProvider((Provider) s);
			result = new ModelAndView("redirect:/j_spring_security_logout");
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			result = new ModelAndView("redirect:/welcome/index.do");
			System.out.println("NO SE HA PODIDO BORRAR EL USUARIO");
		}
		return result;
	}

}
