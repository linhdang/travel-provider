package ucd.travelprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ucd")
public class TravelProvider {

    public static void main(String[] args) {
        SpringApplication.run(TravelProvider.class, args);
    }

}
