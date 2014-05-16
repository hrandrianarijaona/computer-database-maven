package com.excilys.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.connection.ConnectionFactory;
import com.excilys.connection.JdbcDatasource;
import com.excilys.dao.ComputerDAOImpl;
import com.excilys.dao.LogDAOImpl;
import com.excilys.om.Computer;
import com.excilys.service.LogService.TypeLog;


/**
 * Classe Service de Computer
 * @author hrandr
 *
 */
@Service
@Transactional
public class ComputerService {

	private Logger log = null;
	@Autowired
	private ComputerDAOImpl computerDAO;
	@Autowired
	private LogServiceImpl logService;

	@Autowired
	public ComputerService(ComputerDAOImpl computerDAO, LogServiceImpl logService) {
		// TODO Auto-generated constructor stub
		log = LoggerFactory.getLogger(this.getClass());
		this.computerDAO = computerDAO;
		this.logService = logService;
	}



	/**
	 * retourne l'unique instance de ComputerService
	 * @return
	 */
	public ComputerService getInstance(){
		return this;
	}

	/**
	 * Recherche le Computer dans la base de donnée
	 * @param paramId l'id du Computer rechercher
	 * @return l'instance de la Computer
	 */
	@Transactional(readOnly=true)
	public Computer findComputerById(Long paramId){

		log.info("findComputerById... ");
		Computer computer = computerDAO.findComputerById(paramId);

		return computer;
	}

	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Computer> getListComputers() {
		List<Computer> lc = null;
		log.info("Listing des Computers... ");
		lc = computerDAO.getListComputers();
		//			logService.addLog("Listing des Computers effectué...", TypeLog.INFOS, connection);

		return lc;
	}

	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base avec les critères de filtrage et d'ordre
	 * @param filter le mode de tri (0 => name, 1 => introducedDate, 2 => discontinuedDate, 3 => company)
	 * @param isAsc true => ascendant / false => descendant
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Computer> getListComputersByFilteringAndOrdering(int filter, boolean isAsc) {
		log.info("getListComputersByFilteringAndOrdering... ");
		List<Computer> lc = null;

		lc = computerDAO.getListComputersByFilteringAndOrdering(filter, isAsc);


		return lc;
	}



	/**
	 * retourne le nombre de computer/ordinateur dans la base
	 * @return
	 */
	@Transactional(readOnly=true)
	public int getNbComputer(){
		log.info("getNbComputer... ");
		int nbComputer = computerDAO.getNbComputer();

		return nbComputer;
	}

	/**
	 * Insert un ordinateur/computer dans la base
	 */
	@Transactional(readOnly=false, rollbackFor=RuntimeException.class)
	public void insertComputer(Computer cp) {
		Long id = null;

		id = computerDAO.insertComputer(cp);
//		throw new RuntimeException("Allez les bleus!!!");
		
	}

	/**
	 * Supprime l'ordinateur identifié en paramètre de la base de donnée
	 * @param id
	 */
	@Transactional(readOnly=false, rollbackFor={RuntimeException.class, SQLException.class})
	public void deleteComputer(Long id){
		log.info("deleteComputer...");

		computerDAO.deleteComputer(id);
		//			logService.addLog("Delete du computer id(" + id + ")", TypeLog.INFOS, connection);

	}

	/**
	 * Fonction de recherche par filtre
	 * @param word le mot ou schema à rechercher
	 * @param filter le mode de tri (0 => name, 1 => introducedDate, 2 => discontinuedDate, 3 => company)
	 * @param isAsc true => ascendant / false => descendant
	 * @return
	 */
	public List<Computer> searchComputersByFilteringAndOrdering(String word, int filter, boolean isAsc) {
		List<Computer> lc = null;
		log.info("searchComputersByFilteringAndOrdering... ");
		lc = computerDAO.searchComputersByFilteringAndOrdering(word, filter, isAsc);

		return lc;
	}


	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base correspondant au motif avec intervalle de resultat et les critères de triage et d'ordre
	 * @param word le motif à chercher
	 * @param rang la page
	 * @param interval le nombre d'element à afficher
	 * @param filter le mode de tri (0 => name, 1 => introducedDate, 2 => discontinuedDate, 3 => company)
	 * @param isAsc true => ascendant / false => descendant
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Computer> searchComputersByFilteringAndOrderingWithRange(String word, int rang, int interval, int filter, boolean isAsc) {

		List<Computer> lc = null;
		log.info("searchComputersByFilteringAndOrderingWithRange... ");
		lc = computerDAO.searchComputersByFilteringAndOrderingWithRange(word, rang, interval, filter, isAsc);

		return lc;
	}


	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base avec les critères de filtrage et d'ordre
	 * @param rang la page
	 * @param interval le nombre d'element à afficher
	 * @param filter le mode de tri (0 => name, 1 => introducedDate, 2 => discontinuedDate, 3 => company)
	 * @param isAsc true => ascendant / false => descendant
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Computer> getListComputersByFilteringAndOrderingWithRange(int rang, int interval, int filter, boolean isAsc){

		List<Computer> lc = null;
		log.info("getListComputersByFilteringAndOrderingWithRange... ");
		lc = computerDAO.getListComputersByFilteringAndOrderingWithRange(rang, interval, filter, isAsc);

		return lc;
	}

	/**
	 * Met à jour un Computer de la base
	 * @param comp le Computer à mettre à jour
	 */
	@Transactional(readOnly=false, rollbackFor={RuntimeException.class, SQLException.class})
	public void updateComputer(Computer comp){
		log.info("updateComputer("+ comp.getId() +")... ");
		computerDAO.updateComputer(comp);
		//			logService.addLog("updateComputer("+ comp.getId() +")... ", TypeLog.INFOS, connection);

	}

	/**
	 * retourne le nombre de computer/ordinateur dans la base contenant le motif filter
	 * @param filter le motif
	 * @return
	 */
	@Transactional(readOnly=true)
	public int getNbComputerFilter(String filter) {
		log.info("getNbComputerFilter(" + filter + ")... ");
		int nbComputer = computerDAO.getNbComputerFilter(filter);

		return nbComputer;
	}

}
