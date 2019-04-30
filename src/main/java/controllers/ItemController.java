
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Item;
import services.ConfigurationService;
import services.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	//-----------------Services-------------------------

	@Autowired
	private ItemService				itemService;

	@Autowired
	private ConfigurationService	configurationService;


	//-------------------------- List ----------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView;
		final List<Item> items = this.itemService.findAll();

		modelAndView = new ModelAndView("item/list");
		modelAndView.addObject("items", items);
		modelAndView.addObject("requestURI", "item/list.do");
		modelAndView.addObject("banner", this.configurationService.findOne().getBanner());

		return modelAndView;

	}
}
