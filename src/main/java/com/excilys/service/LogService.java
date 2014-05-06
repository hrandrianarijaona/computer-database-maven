package com.excilys.service;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.LogDAO;

public enum LogService implements ILogService {
	INSTANCE;
	
	private Logger log = null;
	
	private LogService() {
		// TODO Auto-generated constructor stub
		log = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * Ajoute le message de log dans la base de donnée
	 * @param msg le message
	 * @param type le type de log
	 */
	public void addLog(String msg, TypeLog type, Connection connection) {
		
		String sType = null;
		switch(type){
		case INFOS:
			sType = "Infos"; break;
		case ERROR:
			sType = "Erreur"; break;
		case DEBUG:
			sType = "Debug"; break;
		case TRACE:
			sType = "Trace"; break;
		case WARN:
			sType = "Warn"; break;
		default:
			sType = "Default";
		}
		log.info("addLog... " + connection);
		LogDAO.INSTANCE.addLog(msg, sType, connection);
	}

}
