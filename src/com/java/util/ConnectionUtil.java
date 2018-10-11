package com.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.java.exception.DatabaseException;

public class ConnectionUtil implements ApplicationContextAware{
	
	
	public  Connection getConnection() throws DatabaseException {
		DatabaseUtil util= applicationContext.getBean(DatabaseUtil.class);
		Connection connection= null;
		try {
			Class.forName(util.getDriverName());
		
		 connection=	DriverManager.getConnection(util.getUrl(), util.getUsername(), util.getPassword());
		} catch (ClassNotFoundException e) {
			throw new DatabaseException("Could not load the driver class for the database!"+ e.getMessage());
		} catch (SQLException e) {
			throw new DatabaseException("Could not connect to the database. Please check the credentials"+ e.getMessage());
		}
		return connection;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext= applicationContext;
	}
	private  ApplicationContext applicationContext;
}
