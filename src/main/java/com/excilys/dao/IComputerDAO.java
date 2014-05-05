package com.excilys.dao;

import java.sql.Connection;
import java.util.List;

import com.excilys.om.Computer;

public interface IComputerDAO {

	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base
	 * @return
	 */
	public List<Computer> getListComputers(Connection connection);
	
	/**
	 * retourne le nombre de computer/ordinateur dans la base
	 * @return
	 */
	public int getNbComputer(Connection connection);
	
	/**
	 * retourne le nombre de computer/ordinateur dans la base contenant le motif filter
	 * @param filter le motif
	 * @param connection la connection
	 * @return
	 */
	public int getNbComputerFilter(String filter, Connection connection);

	/**
	 * Insert un ordinateur/computer dans la base
	 */
	public Long insertComputer(Computer cp, Connection connection);

	/**
	 * Supprime l'ordinateur identifié en paramètre de la base de donnée
	 * @param id
	 */
	public void deleteComputer(Long id, Connection connection);
	
	/**
	 * Fonction de recherche par filtre
	 * @param word le mot ou schema à rechercher
	 * @param filter le mode de tri (0 => name, 1 => introducedDate, 2 => discontinuedDate, 3 => company)
	 * @param isAsc true => ascendant / false => descendant
	 * @return
	 */
	public List<Computer> searchComputersByFilteringAndOrdering(String word, int filter, boolean isAsc, Connection connection);
	
	
	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base correspondant au motif avec intervalle de resultat et les critères de triage et d'ordre
	 * @param word le motif à chercher
	 * @param rang la page
	 * @param interval le nombre d'element à afficher
	 * @param filter le mode de tri (0 => name, 1 => introducedDate, 2 => discontinuedDate, 3 => company)
	 * @param isAsc true => ascendant / false => descendant
	 * @return
	 */
	public List<Computer> searchComputersByFilteringAndOrderingWithRange(String word, int rang, int interval, int filter, boolean isAsc, Connection connection);
	
	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base avec les critères de filtrage et d'ordre
	 * @param filter le mode de tri (0 => name, 1 => introducedDate, 2 => discontinuedDate, 3 => company)
	 * @param isAsc true => ascendant / false => descendant
	 * @return
	 */
	public List<Computer> getListComputersByFilteringAndOrdering(int filter, boolean isAsc, Connection connection) ;
	
	/**
	 * Liste tous les ordinateurs/computers repertorié dans la base avec les critères de filtrage et d'ordre
	 * @param rang la page
	 * @param interval le nombre d'element à afficher
	 * @param filter le mode de tri (0 => name, 1 => introducedDate, 2 => discontinuedDate, 3 => company)
	 * @param isAsc true => ascendant / false => descendant
	 * @return
	 */
	public List<Computer> getListComputersByFilteringAndOrderingWithRange(int rang, int interval, int filter, boolean isAsc, Connection connection);
	
}
