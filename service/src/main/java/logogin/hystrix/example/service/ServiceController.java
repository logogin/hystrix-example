package logogin.hystrix.example.service;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ServiceController.java
 *
 * @author Pavel Danchenko
 * @date Jul 8, 2013
 *
 */
@Controller
@RequestMapping("service")
public class ServiceController {

    private Random rnd = new Random(System.currentTimeMillis());

    @RequestMapping("fail-fast")
    public void failFast() {
        throw new RuntimeException("failure");
    }

    @RequestMapping("fail-slow/{delay}")
    public void failSlow(@PathVariable long delay) throws Exception {
        if ( delay > 0 ) {
            Thread.sleep(delay);
        }
        throw new RuntimeException("failure");
    }

    @RequestMapping("fail-randomly/{threshold:.+}")
    @ResponseBody
    public String failRandomly(@PathVariable double threshold) throws Exception {
        double chance = rnd.nextDouble();
        if ( chance > threshold ) {
            throw new RuntimeException("failure");
        }
        return "success";
    }

}
