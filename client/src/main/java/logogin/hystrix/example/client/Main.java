package logogin.hystrix.example.client;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

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
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfig.class);
        applicationContext.refresh();
        applicationContext.start();

        ServletHolder servletHolder = new ServletHolder(new HystrixMetricsStreamServlet());
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(servletHolder, "/hystrix.stream");

        Server server = new Server(8070);
        server.setHandler(context);
        server.start();
        server.join();

    }

}
