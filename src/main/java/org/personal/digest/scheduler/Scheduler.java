package org.personal.digest.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.personal.digest.components.MessageBuilderComponent;
import org.personal.digest.components.TelegramComponent;
import org.personal.digest.configuration.BotConfiguration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
@RequiredArgsConstructor
@Slf4j
public class Scheduler {

    private final TelegramBot telegramBot;
    private final BotConfiguration botConfiguration;
    private final TelegramComponent telegramComponent;

    private final MessageBuilderComponent messageBuilderComponent;

    @Scheduled(cron = "0 30 8,22 * * *", zone = "Europe/Moscow")
    public void SchedulerCallback() throws JSONException {
        try {
            StringBuilder message;

            message = messageBuilderComponent.setNewsMessage();

            message.append("\n\n");

            message.append(messageBuilderComponent.setWeatherMessage());

            InlineKeyboardMarkup inlineKeyboard = telegramComponent.getInlineKeyboard();

            SendMessage request = new SendMessage(botConfiguration.getChatId(), message.toString()).parseMode(ParseMode.Markdown).replyMarkup(inlineKeyboard);
            telegramBot.execute(request);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
