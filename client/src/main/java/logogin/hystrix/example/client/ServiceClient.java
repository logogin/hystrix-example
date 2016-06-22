package logogin.hystrix.example.client;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.common.util.concurrent.Futures;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * ServiceClient.java
 *
 * @author Pavel Danchenko
 * @date Jul 8, 2013
 *
 */
@Service
public class ServiceClient extends AbstractScheduledService {

    private final static Logger log = LoggerFactory.getLogger(ServiceClient.class);

    private class FailFastCommand extends HystrixCommand<Object> {

        public FailFastCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("ServiceClient"));
        }

        @Override
        protected Object run() throws Exception {
            return restTemplate.getForObject("http://localhost:8080/service/fail-fast", Object.class);
        }

    }

    private class FailSlowCommand extends HystrixCommand<Object> {

        public FailSlowCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("ServiceClient"));
        }

        @Override
        protected Object run() throws Exception {
            return restTemplate.getForObject("http://localhost:8080/service/fail-slow/5000", Object.class);
        }

    }

    private class FailRandomlyCommand extends HystrixCommand<Object> {

        public FailRandomlyCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("ServiceClient"));
        }

        @Override
        protected Object run() throws Exception {
            return restTemplate.getForObject("http://localhost:8080/service/fail-randomly/0.5", String.class);
        }

    }

    private RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        startAndWait();
    }

    @PreDestroy
    public void destroy() {
        stopAndWait();
    }

    @Override
    protected void runOneIteration() throws Exception {
        try {
            FailFastCommand cmd1 = new FailFastCommand();
            Future<Object> call1 = cmd1.queue();
            Futures.getUnchecked(call1);
        } catch (Exception ex) {
            log.error("Exception caught: ", ex);
            //continue
        }

        try {
            FailSlowCommand cmd2 = new FailSlowCommand();
            Future<Object> call2 = cmd2.queue();
            Futures.getUnchecked(call2);
        } catch (Exception ex) {
            log.error("Exception caught: ", ex);
            //continue
        }

        try {
            FailRandomlyCommand cmd3 = new FailRandomlyCommand();
            Future<Object> call3 = cmd3.queue();
            Futures.getUnchecked(call3);
        } catch (Exception ex) {
            log.error("Exception caught: ", ex);
            //continue
        }
    }

    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedRateSchedule(5, 5, TimeUnit.SECONDS);
    }
}
