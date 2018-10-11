package com.java.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import com.java.exception.DatabaseException;
import com.java.repository.StudentRepository;
import com.java.repository.StudentRepositoryImplOne;
import com.java.util.ConnectionUtil;
import com.java.util.DatabaseUtil;

/*Root configuration file. Spring config can see beans in this but not vice-versa. 
 * Move connectionUtil to other file, error*/
public class RootConfig {
	@Bean("repository")
	public StudentRepository getRepository() throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<StudentRepositoryImplOne> obj = StudentRepositoryImplOne.class;
		Constructor<StudentRepositoryImplOne> cons = obj.getDeclaredConstructor();
		cons.setAccessible(true);
		return cons.newInstance();
		// return new StudentRepositoryImplOne(getUtil());
	}

	@Bean("util")
	public DatabaseUtil getUtil() {
		DatabaseUtil util = new DatabaseUtil();
		return util;
	}

	@Bean
	@Scope("prototype")
	public ConnectionUtil getConnection() throws DatabaseException {
		ConnectionUtil util = new ConnectionUtil();
		return util;
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer getConfigurer() {
		PropertySourcesPlaceholderConfigurer cfg = new PropertySourcesPlaceholderConfigurer();
		cfg.setLocation(new ClassPathResource("database.properties"));
		return cfg;
	}

}
