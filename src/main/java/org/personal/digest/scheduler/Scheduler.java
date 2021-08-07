package org.personal.digest.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.personal.digest.configuration.BotConfiguration;
import org.personal.digest.configuration.MeduzaComponent;
import org.personal.digest.configuration.WeatherComponent;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Lazy(false)
@RequiredArgsConstructor
@Slf4j
public class Scheduler {

    private final TelegramBot telegramBot;
    private final BotConfiguration botConfiguration;
    private final MeduzaComponent meduzaComponent;
    private final WeatherComponent weatherComponent;

    @Scheduled(cron = "0 30 9 * * ?", zone = "Europe/Moscow")
//    @Scheduled(fixedDelay = 5000L)
    public void SchedulerCallback() throws JSONException {
        try {
            Map<String, MeduzaComponent.MeduzaDocumentsResponse> news = meduzaComponent.getMeduzaNews();

            StringBuilder message = new StringBuilder("\uD83D\uDC40 10 новостей к началу дня: \n\n");

            if (!news.isEmpty()) {
                for (MeduzaComponent.MeduzaDocumentsResponse value : news.values()) {
                    message.append(value.getTitle());
                    message.append("\n \uD83D\uDC49 [Источник]");
                    message.append("(https://meduza.io/").append(value.getUrl()).append(") \n\n");
                }
            } else {
                message.append("Не смогу прогрузить новости.");
            }

            String weather = weatherComponent.getWeather();

            if (!weather.isEmpty()) {
                message.append("\n\n").append("\uD83E\uDE9F Погода за окном: \n\n").append(weather);
            }

            SendMessage request = new SendMessage(botConfiguration.getChatId(), message.toString()).parseMode(ParseMode.Markdown);
            telegramBot.execute(request);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
