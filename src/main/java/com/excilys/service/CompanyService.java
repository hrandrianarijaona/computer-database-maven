package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.excilys.connection.ConnectionFactory;
import com.excilys.dao.CompanyDAOImpl;
import com.excilys.om.Company;
import com.excilys.om.Computer;
import com.excilys.service.LogService.TypeLog;

/**
 * Classe singleton de service pour Company
 * @author hrandr
 *
 */
@Service
public class CompanyService {
	
	private Logger log = null;
	
	@Autowired
	private LogServiceImpl logService;
	@Autowired
	private ConnectionFactory connectionFactory;
	@Autowired
	private CompanyDAOImpl companyDAO;
	
	@Autowired
	public CompanyService(ConnectionFactory connectionFactory, CompanyDAOImpl companyDAO, LogServiceImpl logService) {
		// TODO Auto-generated constructor stub
		log = LoggerFactory.getLogger(this.getClass());
		this.connectionFactory = connectionFactory;
		this.companyDAO = companyDAO;
		this.logService = logService;
	}
	
	/**
	 * Sert à obtenir l'unique instance de CompanyService
	 * @return
	 */
	public CompanyService getInstance(){
		return this;
	}
	
	/**
	 * Liste toute les Company dans la base de donnée
	 * @return
	 */
	public List<Company> getListCompany() {
		Connection connection = null;
		List<Company> lc = null;
		try {
			connection = connectionFactory.getConnection();
			log.info("getListCompany... " + connection);
			lc = companyDAO.getListCompany(connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Probleme dans getListCompany...");
		}
		connectionFactory.disconnect();
		
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
			connection = connectionFactory.getConnection();
			connection.setAutoCommit(false);
			id = companyDAO.insertCompany(cp, connection);
			log.info("insertCompany(" + id + ")" + connection);
			logService.addLog("insertCompany(" + id + ")", TypeLog.INFOS, connection);
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
		connectionFactory.disconnect();
		
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
			connection = connectionFactory.getConnection();
			log.info("findCompanyById(" + paramId + ") " + connection);
			cpy = companyDAO.findCompanyById(paramId, connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Probleme dans findCompanyById(" + paramId + ") ");
		}
		connectionFactory.disconnect();
		
		return cpy;
	}
	
	
}
