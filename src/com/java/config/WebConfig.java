package com.java.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//Don't use @Configuration
//Equivalent of web.xml

public class WebConfig implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//root application context
		AnnotationConfigWebApplicationContext ctx= new AnnotationConfigWebApplicationContext();
		ctx.register(new Class[] {RootConfig.class});
		ctx.setServletContext(servletContext);
		servletContext.addListener(new ContextLoaderListener(ctx));
		//each dispatcher servlet having own child context
		Dynamic servletOne=servletContext.addServlet("myServlet", new DispatcherServlet());
		servletOne.addMapping("/");
		servletOne.setAsyncSupported(true);
		servletOne.setInitParameter("contextConfigLocation", "com.java.config.SpringConfig");
		servletOne.setInitParameter("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
		//1st request comes in : object of servlet : init() method
		//As soon as ur application is deployed: create the objects, call the init()
		servletOne.setLoadOnStartup(1);
		
	}

}
