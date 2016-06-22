package logogin.hystrix.example.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Main.java
 *
 * @author Pavel Danchenko
 * @date Jul 8, 2013
 *
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(WebConfig.class);

        ServletHolder servletHolder = new ServletHolder(new DispatcherServlet(applicationContext));
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(servletHolder, "/*");

        Server server = new Server(8080);

        server.setHandler(context);

        server.start();
        server.join();

    }

}
