package org.personal.digest.components;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.personal.digest.configuration.BotConfiguration;
import org.personal.digest.enums.InlineQueries;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TelegramComponent {
    private final TelegramBot telegramBot;
    private final BotConfiguration botConfiguration;
    private final MessageBuilderComponent messageBuilderComponent;

    public InlineKeyboardMarkup getInlineKeyboard() {

        return new InlineKeyboardMarkup(
                new InlineKeyboardButton("\uD83D\uDDDE Новости").callbackData("news"),
                new InlineKeyboardButton("⛅ Погода").callbackData("weather"),
                new InlineKeyboardButton("\uD83D\uDEA8 Пинг!").callbackData("ping"));
    }

    @PostConstruct
    private void initializeTelegramComponent() {
        telegramBot.setUpdatesListener(updates -> {
            updates.forEach(update -> {
                try {
                    String query = "";

                    if (update.callbackQuery() != null) {
                        if (update.callbackQuery().data() != null) {
                            query = update.callbackQuery().data();
                        }
                    }

                    if (update.message() != null) {
                        if (update.message().text() != null && update.message().text().contains("Проверка связи")) {
                            SendMessage request = new SendMessage(botConfiguration.getChatId(), "Выберите доступную опцию:").parseMode(ParseMode.Markdown).replyMarkup(getInlineKeyboard());
                            telegramBot.execute(request);
                        }
                    }

                    if (query.isEmpty()) return;

                    StringBuilder message = new StringBuilder();

                    if (Objects.equals(query, InlineQueries.NEWS.getQuery())) {
                        message = messageBuilderComponent.setNewsMessage();
                    }

                    if (Objects.equals(query, InlineQueries.PING.getQuery())) {
                        message = new StringBuilder("Да, я в строю!");
                    }

                    if (Objects.equals(query, InlineQueries.WEATHER.getQuery())) {
                        message = messageBuilderComponent.setWeatherMessage();
                    }

                    InlineKeyboardMarkup inlineKeyboard = getInlineKeyboard();

                    SendMessage request = new SendMessage(botConfiguration.getChatId(), message.toString()).parseMode(ParseMode.Markdown).replyMarkup(inlineKeyboard);
                    telegramBot.execute(request);
                } catch (Exception ex) {
                    System.out.println(ex);
                }

            });

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
