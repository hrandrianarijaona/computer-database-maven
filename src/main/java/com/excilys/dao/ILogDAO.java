package com.excilys.dao;

import java.sql.Connection;

public interface ILogDAO {

	/**
	 * Ajoute le message de log dans la base de donnée
	 * @param msg le message
	 * @param c la connection
	 */
	public void addLog(String msg, String type, Connection c);
	
}
