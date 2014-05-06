package com.excilys.servlet;

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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.dao.ComputerDAOImpl;
import com.excilys.dto.ComputerDTO;
import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.om.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;
@Component
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
	}
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerService computerService;
	
	
	public AddComputerServlet() {
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// on récupère les attributs du formulaire
		String name = request.getParameter("name");
		DateTime introducedDate = null;
		DateTime discontinuedDate = null;
		StringBuilder msg = new StringBuilder();

		try{
			introducedDate = new DateTime(request.getParameter("introducedDate"));
			discontinuedDate = new DateTime(request.getParameter("discontinuedDate"));
		}catch(IllegalArgumentException iae){
			msg.append("La date doit être dans le bon format (YYYY-MM-DD).<br/>");
		}

		Long idCompany = Long.parseLong(request.getParameter("company"));

		// -------------------------------------------------------------------------------------------------

		ComputerDTO cdto = new ComputerDTO();
		cdto.setName(name);
		cdto.setIntroducedDate(request.getParameter("introducedDate"));
		cdto.setDiscontinuedDate(request.getParameter("discontinuedDate"));
		cdto.setIdCompany(request.getParameter("company"));

		// On verifie les parametres
		HashMap<String, Integer> errorList = ComputerValidator.validateField(cdto);

		// empty si aucune erreur
		if(errorList.isEmpty()){
			int code;
			int page = 0, interval = 20;
			// recupere les paramètres de page
			String sPage = request.getParameter("page");
			String sInterval = request.getParameter("interval");
			String sFiltre = request.getParameter("filter");
			
			// vérifie les paramètres et les initialise sinon
			if(sPage!=null)
				page = Integer.parseInt(sPage);
			if(sInterval!=null)
				interval = Integer.parseInt(sInterval);
			if(sFiltre==null)
				sFiltre = "";
			if(request.getParameter("codeTri")!=null)
				code = Integer.parseInt(request.getParameter("codeTri"));
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
			request.setAttribute("nbComputer", nbComputer);

			// liste les Computers
			List<Computer> computerList = computerService.searchComputersByFilteringAndOrderingWithRange(sFiltre, page, interval, code, true);
			request.setAttribute("computerList", computerList);
			
			int nbPage = (int) Math.ceil(computerService.getListComputers().size()/interval);

			Page<Computer> laPage = new Page<>(nbComputer, page, interval, code, nbPage, sFiltre, computerList);
			request.setAttribute("pageComputer", laPage);
			
			
			this.getServletContext().getRequestDispatcher( "/WEB-INF/dashboard.jsp" ).forward( request, response );
			//this.getServletContext().getRequestDispatcher( "/RedirectIndexServlet" ).forward( request, response );
		}
		else{ 
			
			// on envoi directement la map d'erreur
			request.setAttribute("errorList", errorList);
			
			// On envoi le message d'erreur
			request.setAttribute("msg", msg.toString());
			List<Company> companyList = companyService.getListCompany();
			request.setAttribute("companyList", companyList);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/addComputer.jsp" ).forward( request, response );
		}

	}

}
