package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.om.Company;
import com.excilys.service.CompanyService;

/**
 * Servlet implementation class RedirectAddComputerServlet
 */
@Component
@WebServlet("/RedirectAddComputerServlet")
public class RedirectAddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
	}
	
	@Autowired
	private CompanyService companyService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedirectAddComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Company> companyList = companyService.getListCompany();
		
		System.out.println("Il y a " + companyList.size() + " company.");
		
		request.setAttribute("companyList", companyList);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/addComputer.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Company> companyList = companyService.getListCompany();
		request.setAttribute("companyList", companyList);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/addComputer.jsp" ).forward( request, response );
	}

}
