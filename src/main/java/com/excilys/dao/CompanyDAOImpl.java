package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.connection.ConnectionFactory;
import com.excilys.connection.JdbcDatasource;
import com.excilys.connection.ProjetConnection;
import com.excilys.om.Company;

/**
 * Classe de DAO pour Company
 * @author hrandr
 *
 */
@Repository
public class CompanyDAOImpl implements CompanyDAO{
	
//	@Autowired
//	ConnectionFactory connectionFactory;
	
	@Autowired
	private JdbcDatasource jdbcDatasource;
	
	private CompanyDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Sert à obtenir l'unique instance de CompanyDAO
	 * @return
	 */
	public CompanyDAOImpl getInstance(){
		return this;
	}

	/**
	 * Liste toute les companies répertorié
	 * @return
	 */
	public List<Company> getListCompany() {
		
		Logger log = LoggerFactory.getLogger(this.getClass());
		Connection connection = null;
		try {
//			connection = connectionFactory.getConnection();
			connection = jdbcDatasource.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log.error("Erreur lors de la demande de connection.");
		}
		
		ArrayList<Company> al = new ArrayList<Company>();

		// requete de recuperation des companies répertorié dans la base
		String query = "SELECT * FROM company;";
		ResultSet results = null;
		Statement stmt = null;

		if(connection != null){

			try {
				stmt = connection.createStatement();
				results = stmt.executeQuery(query);

				while(results.next()){
					// Recuperation des donnéees de la ligne
					Long id = results.getLong("id");
					String name = results.getString("name");

					al.add(Company.builder().id(id).name(name).build()); // Company créer avec le pattern Builder
					// al.add(new Company(id, name));

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Problème dans la requete de listing...");
			} finally{
				jdbcDatasource.closeObject(results, stmt, connection);
			}
		}
		else{
			System.out.println("La connection est null...");
		}

		return al;
	}

	/**
	 * Insert une companie dans la base
	 * @param cp
	 */
	public Long insertCompany(Company cp) {

		Logger log = LoggerFactory.getLogger(this.getClass());
		Connection connection = null;
		try {
			connection = jdbcDatasource.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log.error("Erreur lors de la demande de connection.");
		}
		
		Long id = null;
		// ajoutez ici le code d'insertion d'un produit
		String query = "INSERT INTO company(name) VALUES(?);";
		int results = 0;
		PreparedStatement pstmt = null;
		

		try {
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, cp.getName());
			System.out.println("La requete: " + pstmt.toString());

			results = pstmt.executeUpdate();

			System.out.println("Insertion bien effectué...");
			
			try {
				// On recupère l'id généré
				ResultSet rsId = pstmt.getGeneratedKeys();
				while(rsId.next()){
					id = rsId.getLong(1);
				}
				
				// fermeture de rsId
//				ConnectionFactory.closeObject(rsId);
				jdbcDatasource.closeObject(rsId);
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Probleme dans la génération des id Company...");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Probleme dans la requete d'insertion...");
		}finally{
			jdbcDatasource.closeObject(results, pstmt, connection);
		}

		return id;
	}

	/**
	 * Recherche la company dans la base de donnée
	 * @param paramId l'id à rechercher
	 * @return L'objet Company
	 */
	public Company findCompanyById(Long paramId){
		
		Logger log = LoggerFactory.getLogger(this.getClass());
		Connection connection = null;
		try {
			connection = jdbcDatasource.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			log.error("Erreur lors de la demande de connection.");
		}
		
		// Company company = new Company();
		Company company = Company.builder().build(); // créée par le pattern Builder

		// requete de recuperation des companies répertorié dans la base
		String query = "SELECT * FROM company WHERE id=?;";
		ResultSet results = null;
		PreparedStatement pstmt = null;

		if(connection != null){

			try {
				pstmt = connection.prepareStatement(query);
				pstmt.setLong(1, paramId);
				results = pstmt.executeQuery();

				while(results.next()){
					// Recuperation des donnéees de la ligne
					Long id = results.getLong("id");
					String name = results.getString("name");
					company.setId(id);
					company.setName(name);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Problème dans la requete de recherche de company...");
			} finally{
				jdbcDatasource.closeObject(results, pstmt, connection);
			}
		}
		else{
			System.out.println("La connection est null...");
		}

		return company;
	}

}
