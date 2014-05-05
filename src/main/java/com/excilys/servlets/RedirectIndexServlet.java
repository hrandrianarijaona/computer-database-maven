package com.excilys.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.om.Computer;
import com.excilys.om.Page;
import com.excilys.services.ComputerService;
import com.excilys.validator.ComputerValidator;

/**
 * Servlet implementation class RedirectIndexServlet
 */
@WebServlet("/RedirectIndexServlet")
public class RedirectIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RedirectIndexServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		int c; // 0 => Par nom croissant, 1 => nom décroissant, 2 => introDate croissant, 3 => introDate décroissant, 4 => discDate croissant, 5 => discDate décroissant, 6 => company croissant, 7 => company décroissant
		int page = 0, interval = 20;
		
		// recupere les paramètres de page
		String sPage = request.getParameter("page");
		String sInterval = request.getParameter("interval");
		String sFiltre = request.getParameter("filter");
		
		// vérifie les paramètres et les initialise sinon
		if((sPage!=null)&&(ComputerValidator.isPositifNumber(sPage)))
			page = Integer.parseInt(sPage);
		if((sInterval!=null)&&(ComputerValidator.isPositifNumber(sInterval)))
			interval = Integer.parseInt(sInterval);
		if(sFiltre==null)
			sFiltre = "";
		
		// on rattache à la jsp
//		request.setAttribute("page", page);
//		request.setAttribute("interval", interval);
//		request.setAttribute("filterText", sFiltre);
		
		if(request.getParameter("codeTri")!=null)
			c = Integer.parseInt(request.getParameter("codeTri"));
		else
			c = 0;
		
//		request.setAttribute("codeTri", c);
		
		// Choix de l'ordre
		List<Computer> computerList = null;
		//computerList = ComputerService.getInstance().getListComputersWithRange(page, interval);
		switch(c){
		case 0:
			//computerList = ComputerService.getInstance().getListComputersByFilteringAndOrdering(0, true);
			computerList = ComputerService.getInstance().searchComputersByFilteringAndOrderingWithRange(sFiltre, page, interval, 0, true);
			break;
		case 1:
			//computerList = ComputerService.getInstance().getListComputersByFilteringAndOrdering(0, false);
			computerList = ComputerService.getInstance().searchComputersByFilteringAndOrderingWithRange(sFiltre, page, interval, 0, false);
			break;
		case 2:
			//computerList = ComputerService.getInstance().getListComputersByFilteringAndOrdering(1, true);
			computerList = ComputerService.getInstance().searchComputersByFilteringAndOrderingWithRange(sFiltre, page, interval, 1, true);
			break;
		case 3:
			//computerList = ComputerService.getInstance().getListComputersByFilteringAndOrdering(1, false);
			computerList = ComputerService.getInstance().searchComputersByFilteringAndOrderingWithRange(sFiltre, page, interval, 1, false);
			break;
		case 4:
			//computerList = ComputerService.getInstance().getListComputersByFilteringAndOrdering(2, true);
			computerList = ComputerService.getInstance().searchComputersByFilteringAndOrderingWithRange(sFiltre, page, interval, 2, true);
			break;
		case 5:
			//computerList = ComputerService.getInstance().getListComputersByFilteringAndOrdering(2, false);
			computerList = ComputerService.getInstance().searchComputersByFilteringAndOrderingWithRange(sFiltre, page, interval, 2, false);
			break;
		case 6:
			//computerList = ComputerService.getInstance().getListComputersByFilteringAndOrdering(3, true);
			computerList = ComputerService.getInstance().searchComputersByFilteringAndOrderingWithRange(sFiltre, page, interval, 3, true);
			break;
		case 7:
			//computerList = ComputerService.getInstance().getListComputersByFilteringAndOrdering(3, false);
			computerList = ComputerService.getInstance().searchComputersByFilteringAndOrderingWithRange(sFiltre, page, interval, 3, false);
			break;
		default:
			//computerList = ComputerService.getInstance().getListComputersByFilteringAndOrdering(0, true);
			System.out.println("Mauvaise initialisation du codeTri...");
		}

		// compte le nb de Computer dans la base
		int nbComputer = ComputerService.getInstance().getNbComputerFilter(sFiltre);
//		request.setAttribute("nbComputer", nbComputer);

		// liste les Computers
//		request.setAttribute("computerList", computerList);

		// tous les Computer pour la navigation
		List<Computer> allComputerList = ComputerService.getInstance().getListComputers();
//		request.setAttribute("allComputerList", allComputerList);
		
		// calcul du nombre de page
		int nbPage;
		if(sFiltre.length()>0)
			nbPage = (int) Math.ceil(ComputerService.getInstance().searchComputersByFilteringAndOrdering(sFiltre, 0, true).size()/interval); // retourne le nombre de Computer correspondant au critère de recherche
		else
			nbPage = (int) Math.ceil(allComputerList.size()/interval);
//		request.setAttribute("nbPage", nbPage);
		
		Page<Computer> laPage = new Page<>(nbComputer, page, interval, c, nbPage, sFiltre, computerList);
		request.setAttribute("pageComputer", laPage);

		this.getServletContext().getRequestDispatcher( "/WEB-INF/dashboard.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// compte le nb de Computer dans la base
		int nbComputer = ComputerService.getInstance().getNbComputer();
		request.setAttribute("nbComputer", nbComputer);

		// liste les Computers
		List<Computer> computerList = ComputerService.getInstance().getListComputers();
		request.setAttribute("computerList", computerList);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/dashboard.jsp" ).forward( request, response );
	}

}
