package com.excilys.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.dao.ComputerDAOImpl;
import com.excilys.om.Computer;
import com.excilys.service.ComputerService;
@Controller
@RequestMapping("/DeleteComputerServlet")
public class DeleteComputerServlet{
	
	@Autowired
	private ComputerService computerService;
	
	public DeleteComputerServlet() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(method=RequestMethod.POST)
	protected String doPost(ModelMap modelMap, @RequestParam(value="id", required=true) String paramId){

		StringBuilder sb = new StringBuilder();

		Long id = Long.parseLong(paramId);
		if((id==null)||(id==0)){
			sb.append("Accès à la page de manière illégal...");
			// On envoie le message d'erreur
			modelMap.addAttribute("msg", sb.toString());

			// compte le nb de Computer dans la base
			int nbComputer = computerService.getNbComputer();
			modelMap.addAttribute("nbComputer", nbComputer);

			// liste les Computers
			List<Computer> computerList = computerService.getListComputers();
			modelMap.addAttribute("computerList", computerList);

//			this.getServletContext().getRequestDispatcher( "/WEB-INF/dashboard.jsp" ).forward( request, response );
			
			return "dashboard";
		}
		else{
			// On supprime le Computer de la base
			computerService.deleteComputer(id);

			// compte le nb de Computer dans la base
			int nbComputer = computerService.getNbComputer();
			modelMap.addAttribute("nbComputer", nbComputer);

			// liste les Computers
			List<Computer> computerList = computerService.getListComputers();
			modelMap.addAttribute("computerList", computerList);

//			this.getServletContext().getRequestDispatcher( "/index.jsp" ).forward( request, response );
			
			return "../../index";
			
		}

	}

}
