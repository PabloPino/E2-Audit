/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ConfigurationService;
import services.CurriculaService;
import services.FinderService;
import services.PositionService;
import controllers.AbstractController;

@Controller
@RequestMapping("/administrator")
public class AdministratorDashboardController extends AbstractController {

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private FinderService			finderService;


	// Constructors -----------------------------------------------------------

	public AdministratorDashboardController() {
		super();
	}

	// Dashboard---------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() throws ParseException {
		ModelAndView result;
		result = new ModelAndView("administrator/dashboard");
		final DecimalFormat df = new DecimalFormat("0.00");

		result.addObject("banner", this.configurationService.findOne().getBanner());

		//DASHBOARD ACME-HackerRank
		//QueryC1
		final Double queryC1AVG = this.positionService.queryC1AVG();
		final Double queryC1MAX = this.positionService.queryC1MAX();
		final Double queryC1MIN = this.positionService.queryC1MIN();
		final Double queryC1STDDEV = this.positionService.queryC1STDDEV();

		if (queryC1AVG != null)
			result.addObject("queryC1AVG", df.format(queryC1AVG));
		else
			result.addObject("queryC1AVG", 0.0);

		if (queryC1MAX != null)
			result.addObject("queryC1MAX", df.format(queryC1MAX));
		else
			result.addObject("queryC1MAX", 0.0);

		if (queryC1MIN != null)
			result.addObject("queryC1MIN", df.format(queryC1MIN));
		else
			result.addObject("queryC1MIN", 0.0);

		if (queryC1STDDEV != null)
			result.addObject("queryC1STDDEV", df.format(queryC1STDDEV));
		else
			result.addObject("queryC1STDDEV", 0.0);

		//QUERY C2
		final Double queryC2AVG = this.applicationService.queryC2AVG();
		final Double queryC2MAX = this.applicationService.queryC2MAX();
		final Double queryC2MIN = this.applicationService.queryC2MIN();
		final Double queryC2STDDEV = this.applicationService.queryC2STDDEV();

		if (queryC2AVG != null)
			result.addObject("queryC2AVG", df.format(queryC2AVG));
		else
			result.addObject("queryC2AVG", 0.0);

		if (queryC2MAX != null)
			result.addObject("queryC2MAX", df.format(queryC2MAX));
		else
			result.addObject("queryC2MAX", 0.0);

		if (queryC2MIN != null)
			result.addObject("queryC2MIN", df.format(queryC2MIN));
		else
			result.addObject("queryC2MIN", 0.0);

		if (queryC2STDDEV != null)
			result.addObject("queryC2STDDEV", df.format(queryC2STDDEV));
		else
			result.addObject("queryC2STDDEV", 0.0);

		//QUERY C3
		final List<String> queryC3 = this.positionService.queryC3();
		if (!queryC3.isEmpty())
			result.addObject("queryC3", queryC3);

		//QUERY C4
		final List<String> queryC4 = this.applicationService.queryC4();
		if (!queryC4.isEmpty())
			result.addObject("queryC4", queryC4);

		//Query C5
		final Object[] queryC5 = this.positionService.queryC5();

		final Double avgC5 = (Double) queryC5[0];
		final Double maxC5 = (Double) queryC5[1];
		final Double minC5 = (Double) queryC5[2];
		final Double stddevC5 = (Double) queryC5[3];

		if (avgC5 != null)
			result.addObject("avgC5", df.format(avgC5));
		else
			result.addObject("avgC5", 0.0);

		if (maxC5 != null)
			result.addObject("maxC5", df.format(maxC5));
		else
			result.addObject("maxC5", 0.0);

		if (minC5 != null)
			result.addObject("minC5", df.format(minC5));
		else
			result.addObject("minC5", 0.0);

		if (stddevC5 != null)
			result.addObject("stddevC5", df.format(stddevC5));
		else
			result.addObject("stddevC5", 0.0);

		//QUERY C3
		final String queryC6Best = this.positionService.queryC6Best().get(0);
		if (!queryC6Best.isEmpty())
			result.addObject("queryC6Best", queryC6Best);

		//QUERY C4
		final String queryC6Worst = this.positionService.queryC6Worst().get(0);
		if (!queryC6Worst.isEmpty())
			result.addObject("queryC6Worst", queryC6Worst);

		//QUERY B1
		final Double queryB1AVG = this.curriculaService.queryB1AVG();
		final Double queryB1MAX = this.curriculaService.queryB1MAX();
		final Double queryB1MIN = this.curriculaService.queryB1MIN();
		final Double queryB1STDDEV = this.curriculaService.queryB1STDDEV();

		if (queryB1AVG != null)
			result.addObject("queryB1AVG", df.format(queryB1AVG));
		else
			result.addObject("queryB1AVG", 0.0);

		if (queryB1MAX != null)
			result.addObject("queryB1MAX", df.format(queryB1MAX));
		else
			result.addObject("queryB1MAX", 0.0);

		if (queryB1MIN != null)
			result.addObject("queryB1MIN", df.format(queryB1MIN));
		else
			result.addObject("queryB1MIN", 0.0);

		if (queryC2STDDEV != null)
			result.addObject("queryB1STDDEV", df.format(queryB1STDDEV));
		else
			result.addObject("queryB1STDDEV", 0.0);

		//QUERY B2
		final Double queryB2AVG = this.finderService.queryB2AVG();
		final Double queryB2MAX = this.finderService.queryB2MAX();
		final Double queryB2MIN = this.finderService.queryB2MIN();
		final Double queryB2STDDEV = this.finderService.queryB2STDDEV();

		if (queryB2AVG != null)
			result.addObject("queryB2AVG", df.format(queryB2AVG));
		else
			result.addObject("queryB2AVG", 0.0);

		if (queryB2MAX != null)
			result.addObject("queryB2MAX", df.format(queryB2MAX));
		else
			result.addObject("queryB2MAX", 0.0);

		if (queryB2MIN != null)
			result.addObject("queryB2MIN", df.format(queryB2MIN));
		else
			result.addObject("queryB2MIN", 0.0);

		if (queryB2STDDEV != null)
			result.addObject("queryB2STDDEV", df.format(queryB2STDDEV));
		else
			result.addObject("queryB2STDDEV", 0.0);

		final Double queryB3 = this.finderService.queryB3();
		if (queryB3 != null)
			result.addObject("queryB3", df.format(queryB3));
		else
			result.addObject("queryB3", 0.0);

		return result;
	}
}
