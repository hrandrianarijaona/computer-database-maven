package com.excilys.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.connection.ConnectionFactory;
import com.excilys.dao.ComputerDAO;
import com.excilys.dao.LogDAO;
import com.excilys.om.Computer;
import com.excilys.services.ILogService.TypeLog;


/**
 * Classe Service de Computer
 * @author hrandr
 *
 */
public enum ComputerService {
	INSTANCE;
	
	private Logger log = null;
	
	private ComputerService() {
		// TODO Auto-generated constructor stub
		log = LoggerFactory.getLogger(this.getClass());
	}
	
	
	
	/**
	 * retourne l'unique instance de ComputerService
	 * @return
	 */
	public static ComputerService getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Recherche le Computer dans la base de donnée
	 * @param paramId l'id du Computer rechercher
	 * @return l'instance de la Computer
	 */
	public Computer findComputerById(Long paramId){
		
		Connection connection = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("findComputerById... " + connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("Probleme de connection niveau Service");
			e.printStackTrace();
		}
		Computer computer = ComputerDAO.INSTANCE.findComputerById(paramId, connection);
		ConnectionFactory.INSTANCE.disconnect();
		
		return computer;
	}
	
	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base
	 * @return
	 */
	public List<Computer> getListComputers() {
		List<Computer> lc = null;
		Connection connection = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("Listing des Computers... " + connection);
			connection.setAutoCommit(false);
			lc = ComputerDAO.INSTANCE.getListComputers(connection);
			LogService.INSTANCE.addLog("Listing des Computers effectué...", TypeLog.INFOS, connection);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				log.error("Problème dans le listing des computers ... on rollback");
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				log.error("Problème dans le rollback du listing des computers");
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		ConnectionFactory.INSTANCE.disconnect();
		
		return lc;
	}
	
	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base avec les critères de filtrage et d'ordre
	 * @param filter le mode de tri (0 => name, 1 => introducedDate, 2 => discontinuedDate, 3 => company)
	 * @param isAsc true => ascendant / false => descendant
	 * @return
	 */
	public List<Computer> getListComputersByFilteringAndOrdering(int filter, boolean isAsc) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("getListComputersByFilteringAndOrdering... " + connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("Probleme de connection niveau Service");
			e.printStackTrace();
		}
		List<Computer> lc = null;
		
		lc = ComputerDAO.INSTANCE.getListComputersByFilteringAndOrdering(filter, isAsc, connection);
		ConnectionFactory.INSTANCE.disconnect();
		
		
		return lc;
	}
	
	
	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base
	 * @param rang le rang
	 * @return
	 */
	public List<Computer> getListComputersWithRange(int rang, int interval) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("getListComputersWithRange... " + connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("Probleme de connection niveau Service");
			e.printStackTrace();
		}
		List<Computer> lc = null;
		
		lc = ComputerDAO.INSTANCE.getListComputersWithRange(rang, interval, connection);
		ConnectionFactory.INSTANCE.disconnect();
		
		return lc;
	}

	
	/**
	 * retourne le nombre de computer/ordinateur dans la base
	 * @return
	 */
	public int getNbComputer(){
		Connection connection = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("getNbComputer... " + connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("Probleme de connection niveau Service");
			e.printStackTrace();
		}
		int nbComputer = ComputerDAO.INSTANCE.getNbComputer(connection);
		ConnectionFactory.INSTANCE.disconnect();
		
		return nbComputer;
	}
	
	/**
	 * Insert un ordinateur/computer dans la base
	 */
	public void insertComputer(Computer cp) {
		Connection connection = null;
		Long id = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			connection.setAutoCommit(false);
			id = ComputerDAO.getInstance().insertComputer(cp, connection);
			log.info("Insertion du Computer (" + id + ")..." + connection);
			LogService.INSTANCE.addLog("Insertion du computer (" + id + ")", TypeLog.INFOS, connection);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				log.error("Problème dans l'insertion du Computer... on rollback");
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				log.error("Problème dans le rollback de l'insertion du Computer");
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		ConnectionFactory.INSTANCE.disconnect();
		
	}
	
	/**
	 * Supprime l'ordinateur identifié en paramètre de la base de donnée
	 * @param id
	 */
	public void deleteComputer(Long id){
		Connection connection = null;
		try {
			log.info("deleteComputer..." + connection);
			connection = ConnectionFactory.INSTANCE.getConnection();
			
			connection.setAutoCommit(false);
			ComputerDAO.getInstance().deleteComputer(id, connection);
			LogService.INSTANCE.addLog("Delete du computer id(" + id + ")", TypeLog.INFOS, connection);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Probleme dans le delete du computer id(" + id + "), on rollback... au niveau service");
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				log.error("Probleme de rollback du deleteComputer...");
			}
		}
		ConnectionFactory.INSTANCE.disconnect();
		
	}
	
	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base correspondant au motif
	 * @return
	 */
	public List<Computer> searchComputers(String word) {
		Connection connection = null;
		List<Computer> lc = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("searchComputers... " + connection);
			lc = ComputerDAO.INSTANCE.searchComputers(word, connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Probleme dans searchComputers... " + connection);
		}
		ConnectionFactory.INSTANCE.disconnect();
		
		return lc;
	}
	
	/**
	 * Fonction de recherche par filtre
	 * @param word le mot ou schema à rechercher
	 * @param filter le mode de tri (0 => name, 1 => introducedDate, 2 => discontinuedDate, 3 => company)
	 * @param isAsc true => ascendant / false => descendant
	 * @return
	 */
	public List<Computer> searchComputersByFilteringAndOrdering(String word, int filter, boolean isAsc) {
		Connection connection = null;
		List<Computer> lc = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("searchComputersByFilteringAndOrdering... " + connection);
			lc = ComputerDAO.INSTANCE.searchComputersByFilteringAndOrdering(word, filter, isAsc, connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Probleme dans searchComputersByFilteringAndOrdering...");
		}
		ConnectionFactory.INSTANCE.disconnect();
		
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
	public List<Computer> searchComputersByFilteringAndOrderingWithRange(String word, int rang, int interval, int filter, boolean isAsc) {
		Connection connection = null;
		List<Computer> lc = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("searchComputersByFilteringAndOrderingWithRange... " + connection);
			lc = ComputerDAO.INSTANCE.searchComputersByFilteringAndOrderingWithRange(word, rang, interval, filter, isAsc, connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Probleme dans searchComputersByFilteringAndOrderingWithRange...");
		}
		ConnectionFactory.INSTANCE.disconnect();
		
		return lc;
	}
	
	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base correspondant au motif avec intervalle de resultat
	 * @return
	 */
	public List<Computer> searchComputersWithRange(String word, int rang, int interval) {
		Connection connection = null;
		List<Computer> lc = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("searchComputersWithRange... " + connection);
			lc = ComputerDAO.INSTANCE.searchComputersWithRange(word, rang, interval, connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Probleme dans searchComputersWithRange");
		}
		ConnectionFactory.INSTANCE.disconnect();
		
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
	public List<Computer> getListComputersByFilteringAndOrderingWithRange(int rang, int interval, int filter, boolean isAsc){
		Connection connection = null;
		List<Computer> lc = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("getListComputersByFilteringAndOrderingWithRange... " + connection);
			lc = ComputerDAO.INSTANCE.getListComputersByFilteringAndOrderingWithRange(rang, interval, filter, isAsc, connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ConnectionFactory.INSTANCE.disconnect();
		
		return lc;
	}
	
	/**
	 * Met à jour un Computer de la base
	 * @param comp le Computer à mettre à jour
	 */
	public void updateComputer(Computer comp){
		Connection connection = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("updateComputer("+ comp.getId() +")... " + connection);
			connection.setAutoCommit(false);
			ComputerDAO.getInstance().updateComputer(comp, connection);
			LogService.INSTANCE.addLog("updateComputer("+ comp.getId() +")... ", TypeLog.INFOS, connection);
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Probleme dans updateComputer("+ comp.getId() +"), on rollback... ");
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				log.error("Probleme de rollback dans updateComputer...");
			}
			
		}
		ConnectionFactory.INSTANCE.disconnect();
		
	}
	
	/**
	 * retourne le nombre de computer/ordinateur dans la base contenant le motif filter
	 * @param filter le motif
	 * @return
	 */
	public int getNbComputerFilter(String filter) {
		Connection connection = null;
		try {
			connection = ConnectionFactory.INSTANCE.getConnection();
			log.info("getNbComputerFilter(" + filter + ")... " + connection);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("Probleme de connection niveau Service");
			e.printStackTrace();
		}
		int nbComputer = ComputerDAO.INSTANCE.getNbComputerFilter(filter, connection);
		ConnectionFactory.INSTANCE.disconnect();
		
		return nbComputer;
	}

}
