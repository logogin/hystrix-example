package logogin.hystrix.example.client;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig.java
 *
 * @author Pavel Danchenko
 * @date Jul 8, 2013
 *
 */
@Configuration
@ComponentScan(basePackageClasses = { ServiceClient.class } )
public class AppConfig {

}
