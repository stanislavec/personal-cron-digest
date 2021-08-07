package org.personal.digest.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Accessors(chain = true)
@Component
@ConfigurationProperties("weather")
public
class WeatherConfuguration {
    private String apiKey;
}
