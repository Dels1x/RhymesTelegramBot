package ua.delsix;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j
public class DispatcherApplication {
    public static void main(String[] args) {
        System.out.println("hello world 1");
        log.debug("helo world 2");
        SpringApplication.run(DispatcherApplication.class);
    }
}
