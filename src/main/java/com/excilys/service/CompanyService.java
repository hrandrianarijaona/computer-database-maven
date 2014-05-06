package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.connection.ConnectionFactory;
import com.excilys.dao.CompanyDAO;
import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.service.ILogService.TypeLog;

/**
 * Classe singleton de service pour Company
 * @author hrandr
 *
 */
public enum CompanyService {
	INSTANCE;
	
	private Logger log = null;
	
	private CompanyService() {
		// TODO Auto-generated constructor stub
		log = LoggerFactory.getLogger(this.getClass());
	}
	
	/**
	 * Sert à obtenir l'unique instance de CompanyService
	 * @return
	 */
	public static CompanyService getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Liste toute les Company dans la base de donnée
	 * @return
	 */
	public List<Company> getListCompany() {
		Connection connection = null;
		List<Company> lc = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("getListCompany... " + connection);
			lc = CompanyDAO.INSTANCE.getListCompany(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Probleme dans getListCompany...");
		}
		ConnectionFactory.INSTANCE.disconnect();
		
		return lc;
	}
	
	/**
	 * Insert une companie dans la base
	 * @param cp
	 */
	public void insertCompany(Company cp) {
		Connection connection = null;
		Long id = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			connection.setAutoCommit(false);
			id = CompanyDAO.INSTANCE.insertCompany(cp, connection);
			log.info("insertCompany(" + id + ")" + connection);
			LogService.INSTANCE.addLog("insertCompany(" + id + ")", TypeLog.INFOS, connection);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Probleme lors de insertCompany... on rollback");
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				log.error("Probleme dans le rollback du insertCompany...");
			}
			
		}
		ConnectionFactory.INSTANCE.disconnect();
		
	}
	
	/**
	 * Recherche la company dans la base de donnée
	 * @param paramId l'id à rechercher
	 * @return L'objet Company
	 */
	public Company findCompanyById(Long paramId){
		Connection connection = null;
		Company cpy = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("findCompanyById(" + paramId + ") " + connection);
			cpy = CompanyDAO.INSTANCE.findCompanyById(paramId, connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Probleme dans findCompanyById(" + paramId + ") ");
		}
		ConnectionFactory.INSTANCE.disconnect();
		
		return cpy;
	}
	
	
}
