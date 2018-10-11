package com.java.config;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.java.controller.StudentController;
import com.java.repository.StudentRepository;
import com.java.service.StudentService;
import com.java.service.StudentServiceImpl;
@Configuration
@EnableWebMvc
public class SpringConfig {

	
	@Autowired StudentRepository rep;
	@Bean
	@Scope("singleton")
	public BeanNameUrlHandlerMapping getHandlerMapping() {
		return new BeanNameUrlHandlerMapping();
	}
	
	@Bean(name= {"/addStudent","/updateStudent"})
	public StudentController getStudentController() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return new StudentController(getService());
	}

	@Bean(value="service")
	public StudentService getService() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return new StudentServiceImpl(rep);
	}

	
	@Bean
	public ViewResolver getResolver() {
		return new InternalResourceViewResolver("/WEB-INF/views/",".jsp");
	}
	
	
}
