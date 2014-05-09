package com.excilys.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.om.Company;
import com.excilys.service.CompanyService;

/**
 * Servlet implementation class RedirectAddComputerServlet
 */
@Controller
@RequestMapping("/RedirectAddComputerServlet")
public class RedirectAddComputerServlet {
	private static final long serialVersionUID = 1L;
		
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
    @RequestMapping(method=RequestMethod.GET)
	protected String doGet(ModelMap modelMap){
		// TODO Auto-generated method stub
    	
    	System.out.println("Get up!");
    	
		List<Company> companyList = companyService.getListCompany();
		
		System.out.println("Il y a " + companyList.size() + " company.");
		
		modelMap.addAttribute("companyList", companyList);
//		this.getServletContext().getRequestDispatcher( "/WEB-INF/addComputer.jsp" ).forward( request, response );
		return "addComputer";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @RequestMapping(method=RequestMethod.POST)
	protected String doPost(ModelMap modelMap){
		// TODO Auto-generated method stub
		List<Company> companyList = companyService.getListCompany();
		modelMap.addAttribute("companyList", companyList);
//		this.getServletContext().getRequestDispatcher( "/WEB-INF/addComputer.jsp" ).forward( request, response );
		return "addComputer";
	}

}
