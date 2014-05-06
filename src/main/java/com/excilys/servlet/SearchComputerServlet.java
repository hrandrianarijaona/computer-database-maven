package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.om.Computer;
import com.excilys.service.ComputerService;

/**
 * Servlet implementation class SearchComputerServlet
 */
@Component
@WebServlet("/SearchComputerServlet")
public class SearchComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
	}
	
	@Autowired
	private ComputerService computerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchComputerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// mot clé de recherche
		String search = "";
		search = request.getParameter("search");
		request.setAttribute("search", search);
		
		// verif sur le parametre page
		String sPage = request.getParameter("page");
		int page = 0;
		if(sPage!=null)
			page = Integer.parseInt(request.getParameter("page")) - 1; // -1 pour ne pas fausser le (page*interval)

		// verif sur le parametre interval
		String sInterval = request.getParameter("interval");
		int interval = 20;
		if(sInterval!=null)
			interval = Integer.parseInt(request.getParameter("interval"));
		
		System.out.println("coucou: " + request.getParameter("search") + "/" + page + "/" + interval);

		// liste les Computers dans l'intervalle voulut
		List<Computer> computerList = computerService.searchComputersWithRange(search, page*interval, interval);
		request.setAttribute("computerList", computerList);

		// liste les Computers dans l'intervalle voulut
		List<Computer> allResultComputerList = computerService.searchComputers(search);
		request.setAttribute("allResultComputerList", allResultComputerList);
		
		// nombre de page
		int nbPage = allResultComputerList.size()/interval;
		if(allResultComputerList.size()%interval>0) // si il y a un reste, alors on ajoute une page supplémentaire
			nbPage++;
		request.setAttribute("nbPage", nbPage);
		

		// compte le nb de Computer dans la base correspondant au critère de recherche
		int nbComputer = allResultComputerList.size();
		request.setAttribute("nbComputer", nbComputer);

		this.getServletContext().getRequestDispatcher( "/WEB-INF/results.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
