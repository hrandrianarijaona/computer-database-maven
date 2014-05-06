package com.excilys.mapper;

import org.joda.time.DateTime;

import com.excilys.dto.ComputerDTO;
import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.service.CompanyService;

public class ComputerMapper {

	private ComputerMapper() {
		// TODO Auto-generated constructor stub
	}
	
	public static Computer mapComputer(ComputerDTO cdto){
		Long id = Long.parseLong(cdto.getId());
		String name = cdto.getName();
		DateTime introducedDate = null;
		DateTime discontinuedDate = null;
		Long idCompany = Long.parseLong(cdto.getIdCompany());
		Company company = CompanyService.getInstance().findCompanyById(idCompany);
		
		if(cdto.getIntroducedDate()!=null)
			introducedDate = new DateTime(cdto.getIntroducedDate());
		
		if(cdto.getDiscontinuedDate()!=null)
			discontinuedDate = new DateTime(cdto.getDiscontinuedDate());
		
		Computer computer = Computer.builder().id(id).name(name).introducedDate(introducedDate).discontinuedDate(discontinuedDate).company(company).build();
		
		return computer;
		
	}

}
