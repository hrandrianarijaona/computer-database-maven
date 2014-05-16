package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.connection.ConnectionFactory;
import com.excilys.connection.JdbcDatasource;
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
@Transactional
public class CompanyService {
	
	private Logger log = null;
	
	@Autowired
	private LogServiceImpl logService;
	@Autowired
	private CompanyDAOImpl companyDAO;
	
	@Autowired
	public CompanyService(JdbcDatasource jdbcDatasource, CompanyDAOImpl companyDAO, LogServiceImpl logService) {
		// TODO Auto-generated constructor stub
		log = LoggerFactory.getLogger(this.getClass());
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
	@Transactional(readOnly=true)
	public List<Company> getListCompany() {
		List<Company> lc = null;
		log.info("getListCompany... ");
		lc = companyDAO.getListCompany();
		
		return lc;
	}
	
	/**
	 * Insert une companie dans la base
	 * @param cp
	 */
	@Transactional(readOnly=false, rollbackFor={RuntimeException.class, SQLException.class})
	public void insertCompany(Company cp) {
		Long id = null;
		//			connection.setAutoCommit(false);
					id = companyDAO.insertCompany(cp);
					log.info("insertCompany(" + id + ")");
		//			logService.addLog("insertCompany(" + id + ")", TypeLog.INFOS, connection);
		
	}
	
	/**
	 * Recherche la company dans la base de donnée
	 * @param paramId l'id à rechercher
	 * @return L'objet Company
	 */
	@Transactional(readOnly=true)
	public Company findCompanyById(Long paramId){
		Company cpy = null;
		log.info("findCompanyById(" + paramId + ") ");
		cpy = companyDAO.findCompanyById(paramId);
		
		return cpy;
	}
	
	
}
