package logogin.hystrix.example.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebConfig.java
 *
 * @author Pavel Danchenko
 * @date Jul 8, 2013
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { ServiceController.class })
public class WebConfig extends WebMvcConfigurerAdapter {

}
