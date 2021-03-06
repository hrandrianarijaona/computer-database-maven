package com.excilys.om;

public class Company {

	private Long id;
	private String name;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Company() {
		// TODO Auto-generated constructor stub
	}

	private Company(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Company(CompanyBuilder cb){
		super();
		this.id = cb.id;
		this.name = cb.name;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public static class CompanyBuilder{
		private Long id;
		private String name;
		
		public CompanyBuilder id(Long id){
			this.id = id;
			return this;
		}
		
		public CompanyBuilder name(String name){
			this.name = name;
			return this;
		}
		
		public Company build(){
			return new Company(this);
		}
	}
	
	public static CompanyBuilder builder(){
		return new CompanyBuilder();
	}

}
