That’s an interface extending ApplicationContext with a contract for accessing the ServletContext.

: the root web application context is simply a centralized place to define shared beans.

The root web application context is managed by a listener of class org.springframework.web.context.ContextLoaderListener, which is part of the spring-web module.By default, the listener will load an XML application context from /WEB-INF/applicationContext.xml.  However, those defaults can be changed

We can specify an alternate location of the XML context configuration with the contextConfigLocation parameter:

<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/rootApplicationContext.xml</param-value>
</context-param>
Or more than one location, separated by commas:

<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/context1.xml, /WEB-INF/context2.xml</param-value>
</context-param>
We can even use patterns:


<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/*-context.xml</param-value>
</context-param>

So 1 root context with all xmls defined using the above pattern

Let’s see, for example, how to use Java annotations configuration instead.

We use the contextClass parameter to tell the listener which type of context to instantiate:

<context-param>
    <param-name>contextClass</param-name>
    <param-value>
        org.springframework.web.context.support.AnnotationConfigWebApplicationContext
    </param-value>
</context-param>
Every type of context may have a default configuration location. In our case, the AnnotationConfigWebApplicationContext does not have one, so we have to provide it.

We can thus list one or more annotated classes:

<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>
        com.baeldung.contexts.config.RootApplicationConfig,
        com.baeldung.contexts.config.NormalWebAppConfig
    </param-value>
</context-param>
Or we can tell the context to scan one or more packages:

<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>org.baeldung.bean.config</param-value>
</context-param>
And, of course, we can mix and match the two options.


Using webapplicatioNInitializer

XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
Then, in the second line, we tell the context where to load its bean definitions from. Again, setConfigLocations is the programmatic analogous of the contextConfigLocation parameter in web.xml:

rootContext.setConfigLocations("/WEB-INF/rootApplicationContext.xml");

Finally, we create a ContextLoaderListener with the root context and register it with the servlet container. As we can see, ContextLoaderListener has an appropriate constructor that takes a WebApplicationContext and makes it available to the application:

The WebApplicationInitializer class that we’ve seen earlier is a general-purpose interface. It turns out that Spring provides a few more specific implementations, including an abstract class called AbstractContextLoaderInitializer.

Its job, as the name implies, is to create a ContextLoaderListener and register it with the servlet container.

We only have to tell it how to build the root context:


public class AnnotationsBasedApplicationInitializer 
  extends AbstractContextLoaderInitializer {
  
    @Override
    protected WebApplicationContext createRootApplicationContext() {
        AnnotationConfigWebApplicationContext rootContext
          = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootApplicationConfig.class);
        return rootContext;
    }
}
Here we can see that we no longer need to register the ContextLoaderListener, which saves us from a little bit of boilerplate code.

Spring MVC applications have at least one Dispatcher Servlet configured (but possibly more than one, we’ll talk about that case later). This is the servlet that receives incoming requests, dispatches them to the appropriate controller method, and returns the view.

Each DispatcherServlet has an associated application context. Beans defined in such contexts configure the servlet and define MVC objects like controllers and view resolvers.

the root context is the parent of every dispatcher servlet context. Thus, beans defined in the root web application context are visible to each dispatcher servlet context, but not vice versa.

https://www.baeldung.com/spring-web-contexts


servletContext.addListener(new ContextLoaderListener(rootContext));