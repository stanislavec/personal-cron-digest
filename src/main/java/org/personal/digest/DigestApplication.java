package org.personal.digest;

import org.personal.digest.configuration.BotConfiguration;
import org.personal.digest.configuration.WeatherConfuguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({BotConfiguration.class, WeatherConfuguration.class})
public class DigestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigestApplication.class, args);
    }

}
