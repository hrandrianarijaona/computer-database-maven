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

import com.excilys.dto.ComputerDTO;
import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.om.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;



/**
 * Servlet implementation class EditComputerServlet
 */
@Controller
@RequestMapping("/EditComputerServlet")
public class EditComputerServlet{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditComputerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@RequestMapping(method=RequestMethod.GET)
	protected String doGet(ModelMap modelMap, @RequestParam(value="id", required=true) String paramId){

		// On recupère le Computer à éditer
		Computer computer = computerService.findComputerById(Long.parseLong(paramId));
		modelMap.addAttribute("computer", computer);

		// On recupere la liste des Company
		List<Company> companyList = companyService.getListCompany();		
		modelMap.addAttribute("companyList", companyList);

//		this.getServletContext().getRequestDispatcher( "/WEB-INF/editComputer.jsp" ).forward( request, response );
		return "editComputer";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@RequestMapping(method=RequestMethod.POST)
	protected String doPost(ModelMap modelMap, 
							@RequestParam(value="name", required=true) String paramName,
							@RequestParam(value="introducedDate", required=false) String paramIntroducedDate,
							@RequestParam(value="discontinuedDate", required=false) String paramDiscontinuedDate,
							@RequestParam(value="company", required=true) String paramCompany,
							@RequestParam(value="page", required=false) String paramPage,
							@RequestParam(value="interval", required=false) String paramInterval,
							@RequestParam(value="filter", required=false) String paramFilter,
							@RequestParam(value="codeTri", required=false) String paramCodeTri,
							@RequestParam(value="id", required=true) String paramId
							){

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

		// Verification des paramètres
		ComputerDTO cdto = new ComputerDTO();
		cdto.setName(name);
		cdto.setIntroducedDate(paramIntroducedDate);
		cdto.setDiscontinuedDate(paramDiscontinuedDate);
		cdto.setIdCompany(paramCompany);
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
			c.setId(Long.parseLong(paramId));
			c.setName(name);
			c.setIntroducedDate(introducedDate);
			c.setDiscontinuedDate(discontinuedDate);
			// On récupère la Company correspondante
			c.setCompany(companyService.findCompanyById(idCompany));

			// On update le computer dans la base 
			computerService.updateComputer(c);

			// compte le nb de Computer dans la base
			int nbComputer = computerService.getNbComputer();
			//			request.setAttribute("nbComputer", nbComputer);

			// liste les Computers
			List<Computer> computerList = computerService.searchComputersByFilteringAndOrderingWithRange(sFiltre, page, interval, code, true);
			//			request.setAttribute("computerList", computerList);

			int nbPage = (int) Math.ceil(computerService.getListComputers().size()/interval);

			Page<Computer> laPage = new Page<>(nbComputer, page, interval, code, nbPage, sFiltre, computerList);
			modelMap.addAttribute("pageComputer", laPage);

//			this.getServletContext().getRequestDispatcher( "/WEB-INF/dashboard.jsp" ).forward( request, response );
			
			return "dashboard";
			
		}
		else{
			
			// on envoi directement la map d'erreur
			modelMap.addAttribute("errorList", errorList);
			
			// on créer un objet computer contenant les champs précédent pour les retransmettre dans le formulaire
			// On recupère le Computer à éditer
			Computer computer = computerService.findComputerById(Long.parseLong(paramId));
			modelMap.addAttribute("computer", computer);
			
			// On envoi le message d'erreur
			modelMap.addAttribute("msg", msg.toString());
			List<Company> companyList = companyService.getListCompany();
			modelMap.addAttribute("companyList", companyList);
//			this.getServletContext().getRequestDispatcher( "/WEB-INF/editComputer.jsp" ).forward( request, response );
			
			return "editComputer";
			
		}




		// Mis à jour

		// Redirection vers le dashboard
	}

}
