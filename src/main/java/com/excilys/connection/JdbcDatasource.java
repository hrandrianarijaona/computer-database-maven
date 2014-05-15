package com.excilys.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcDatasource {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcDatasource() {
	}
	
	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	public Connection getConnection() throws SQLException{
		return jdbcTemplate.getDataSource().getConnection();
	}
	
	/**
	 * Methode de fermeture des objets utilisé par les DAOs
	 * @param obj
	 */
	public void closeObject(Object... obj){
		Logger log = LoggerFactory.getLogger(this.getClass());
		for(Object o : obj){
			if(o instanceof Connection){
				if(o!=null){
					try {
						((Connection) o).close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("Probleme de fermeture de connection");
					}
				}
			}
			else if(o instanceof ResultSet){
				if(o!=null){
					try {
						((ResultSet) o).close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("Probleme de fermeture du ResultSet");
					}
				}
			}
			else if(o instanceof PreparedStatement){
				if(o!=null){
					try {
						((PreparedStatement) o).close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("Probleme de fermeture du prepardedStatement");
					}
				}
			}
			else if(o instanceof Statement){
				if(o!=null){
					try {
						((Statement) o).close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("Probleme de fermeture du Statement");
					}
				}
			}
				
		}
	}

}
