package org.personal.digest.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class TelegramComponent {
    @Bean
    public TelegramBot telegramBot(BotConfiguration botConfiguration) {
        return new TelegramBot(botConfiguration.getBotId());
    }
}
