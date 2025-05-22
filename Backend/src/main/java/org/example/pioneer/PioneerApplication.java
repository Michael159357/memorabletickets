package org.example.pioneer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@EnableAsync
@SpringBootApplication(scanBasePackages = "org.example.pioneer")
public class PioneerApplication {

    public static void main(String[] args) {

        SpringApplication.run(PioneerApplication.class, args);
    }

}
