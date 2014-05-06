package com.excilys.dao;

import java.sql.Connection;
import java.util.List;

import com.excilys.om.Company;

public interface CompanyDAO {

//  enlever pour le passage en SPRING
// 	public static CompanyDAO cdao = null;

	/**
	 * Liste toute les companies répertorié
	 * @return
	 */
	public List<Company> getListCompany(Connection connection);

	/**
	 * Insert une companie dans la base
	 * @param cp
	 */
	public Long insertCompany(Company cp, Connection connection);

	/**
	 * Recherche la company dans la base de donnée
	 * @param paramId l'id à rechercher
	 * @return L'objet Company
	 */
	public Company findCompanyById(Long paramId, Connection connection);

}
