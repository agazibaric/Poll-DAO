package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Listener that initializes web application when application is started.
 * 
 * @author Ante Gazibaric
 * @version 1.0
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {
	
	/**
	 * Database host.
	 */
	private String host;
	/*
	 * Database port.
	 */
	private String port;
	/**
	 * Database name.
	 */
	private String dbName;
	/**
	 * Database user name.
	 */
	private String user;
	/**
	 * Database user password.
	 */
	private String password;

	/**
	 * Method loads database data from given {@code Properties}.
	 * 
	 * @param properties properties that contains database informations
	 */
	private void loadProperties(Properties properties) {
		host = Objects.requireNonNull(properties.getProperty("host"), "Missing host in dbsettings.properties file");
		port = Objects.requireNonNull(properties.getProperty("port"), "Missing port in dbsettings.properties file");
		dbName = Objects.requireNonNull(properties.getProperty("name"), "Missing dbName in dbsettings.properties file");
		user = Objects.requireNonNull(properties.getProperty("user"), "Missing user in dbsettings.properties file");
		password = Objects.requireNonNull(properties.getProperty("password"), "Missing password in dbsettings.properties file");
	}
	
	/**
	 * Method returns connection string needed for connection to the database.
	 * 
	 * @return string needed for connection to the database
	 */
	private String getConnectionURL() {
		return "jdbc:derby://" + host + ":" + port + 
				"/" + dbName + ";" + 
				"user=" + user + ";" +
				"password=" + password;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	
		Path dbPropertiesPath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"));
		if (!Files.exists(dbPropertiesPath)) {
			throw new RuntimeException("File dbsettings.properties does not exist");
		}
		Properties properties = new Properties();
		try {
			properties.load(Files.newInputStream(dbPropertiesPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadProperties(properties);
		
		String connectionURL = getConnectionURL();
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException ex) {
			throw new RuntimeException("Error occurred during initialization of the pool.", ex);
		}
		cpds.setJdbcUrl(connectionURL);
		
		DAO dao = DAOProvider.getDao();
		try {
			dao.createPolls(cpds);
			dao.createPollOptions(cpds);
			if (dao.isPollEmpty(cpds)) {
				dao.fillPoll(sce, cpds, "/WEB-INF/poll-definitions.txt");
			}
		} catch (DAOException ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

}