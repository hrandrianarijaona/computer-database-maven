package com.excilys.dto;


public class ComputerDTO {

	private String id;
	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private String idCompany;
	
	public ComputerDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(String introducedDate) {
		this.introducedDate = introducedDate;
	}

	public String getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(String discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	public String getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(String company) {
		this.idCompany = company;
	}

	public ComputerDTO(String id, String name, String introducedDate,
			String discontinuedDate, String idCompany) {
		super();
		this.id = id;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.idCompany = idCompany;
	}
	
	

}
