package com.excilys.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.dao.ComputerDAOImpl;
import com.excilys.dto.ComputerDTO;
import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.om.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;
@Controller
@RequestMapping("/AddComputerServlet")
public class AddComputerServlet {
	
	@Autowired
	private CompanyService companyService; 
	
	@Autowired
	private ComputerService computerService;
	
	
	public AddComputerServlet() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(method=RequestMethod.POST)
	protected String doPost(ModelMap modelMap, 
							@RequestParam(value="name", required=true) String paramName,
							@RequestParam(value="introducedDate", required=false) String paramIntroducedDate,
							@RequestParam(value="discontinuedDate", required=false) String paramDiscontinuedDate,
							@RequestParam(value="company", required=true) String paramCompany,
							@RequestParam(value="page", required=false) String paramPage,
							@RequestParam(value="interval", required=false) String paramInterval,
							@RequestParam(value="filter", required=false) String paramFilter,
							@RequestParam(value="codeTri", required=false) String paramCodeTri){
		// on récupère les attributs du formulaire
		String name = paramName;
		DateTime introducedDate = null;
		DateTime discontinuedDate = null;
		StringBuilder msg = new StringBuilder();

		try{
			introducedDate = new DateTime(paramIntroducedDate);
			discontinuedDate = new DateTime(paramDiscontinuedDate);
		}catch(IllegalArgumentException iae){
			msg.append("La date doit être dans le bon format (YYYY-MM-DD).<br/>");
		}

		Long idCompany = Long.parseLong(paramCompany);

		// -------------------------------------------------------------------------------------------------

		ComputerDTO cdto = new ComputerDTO();
		cdto.setName(name);
		cdto.setIntroducedDate(paramIntroducedDate);
		cdto.setDiscontinuedDate(paramDiscontinuedDate);
		cdto.setIdCompany(paramCompany);

		// On verifie les parametres
		HashMap<String, Integer> errorList = ComputerValidator.validateField(cdto);

		// empty si aucune erreur
		if(errorList.isEmpty()){
			int code;
			int page = 0, interval = 20;
			// recupere les paramètres de page
			String sPage = paramPage;
			String sInterval = paramInterval;
			String sFiltre = paramFilter;
			
			// vérifie les paramètres et les initialise sinon
			if(sPage!=null)
				page = Integer.parseInt(sPage);
			if(sInterval!=null)
				interval = Integer.parseInt(sInterval);
			if(sFiltre==null)
				sFiltre = "";
			if(paramCodeTri!=null)
				code = Integer.parseInt(paramCodeTri);
			else
				code = 0;
			
			// On crée le Computer
			Computer c = new Computer();
			c.setName(name);
			c.setIntroducedDate(introducedDate);
			c.setDiscontinuedDate(discontinuedDate);
			// On récupère la Company correspondante
			c.setCompany(companyService.findCompanyById(idCompany));

			// On insère le computer dans la base
			computerService.insertComputer(c);

			// compte le nb de Computer dans la base
			int nbComputer = computerService.getNbComputer();
			modelMap.addAttribute("nbComputer", nbComputer);

			// liste les Computers
			List<Computer> computerList = computerService.searchComputersByFilteringAndOrderingWithRange(sFiltre, page, interval, code, true);
			modelMap.addAttribute("computerList", computerList);
			
			int nbPage = (int) Math.ceil(computerService.getListComputers().size()/interval);

			Page<Computer> laPage = new Page<>(nbComputer, page, interval, code, nbPage, sFiltre, computerList);
			modelMap.addAttribute("pageComputer", laPage);
			
			
			//this.getServletContext().getRequestDispatcher( "/WEB-INF/dashboard.jsp" ).forward( request, response );
			return "dashboard";
		}
		else{ 
			
			// on envoi directement la map d'erreur
			modelMap.addAttribute("errorList", errorList);
			
			// On envoi le message d'erreur
			modelMap.addAttribute("msg", msg.toString());
			List<Company> companyList = companyService.getListCompany();
			modelMap.addAttribute("companyList", companyList);
			//this.getServletContext().getRequestDispatcher( "/WEB-INF/addComputer.jsp" ).forward( request, response );
			return "addComputer";
		}

	}

}
