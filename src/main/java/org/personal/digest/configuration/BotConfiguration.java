package org.personal.digest.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Accessors(chain = true)
@ConfigurationProperties("telegram")
public
class BotConfiguration {
    private String botId;
    private String chatId;
}
