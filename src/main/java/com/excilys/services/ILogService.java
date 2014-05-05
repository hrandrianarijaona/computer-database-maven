package com.excilys.services;

import java.sql.Connection;


public interface ILogService {

	public enum TypeLog{
		INFOS, ERROR, TRACE, DEBUG, WARN;
	}
	
	/**
	 * Ajoute le message de log dans la base de donnée
	 * @param msg le message
	 * @param type le type de log
	 */
	public void addLog(String msg, TypeLog type, Connection connection);
}
