package org.personal.digest.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageBuilderComponent {
    private final MeduzaComponent meduzaComponent;
    private final WeatherComponent weatherComponent;

    public StringBuilder setNewsMessage() {
        StringBuilder message = new StringBuilder();
        try {
            Map<String, MeduzaComponent.MeduzaDocumentsResponse> news = meduzaComponent.getMeduzaNews();


            if (!news.isEmpty()) {
                message.append("\uD83D\uDC40 10 новостей к началу дня: \n\n");
                for (MeduzaComponent.MeduzaDocumentsResponse value : news.values()) {
                    message.append(value.getTitle());
                    message.append("\n \uD83D\uDC49 [Источник]");
                    message.append("(https://meduza.io/").append(value.getUrl()).append(") \n\n");
                }
            } else {
                message.append("Не смог прогрузить новости.");
            }

            return message;
        } catch (Exception ex) {
            message.append("Не смог прогрузить новости.");
        }
        return message;
    }

    public StringBuilder setWeatherMessage() {
        StringBuilder message = new StringBuilder();
        String weather = weatherComponent.getWeather();

        if (!weather.isEmpty()) {
            message.append("\uD83E\uDE9F Погода за окном: \n\n").append(weather);
        } else {
            message.append("Не смог прогрузить погоду");
        }

        return message;
    }
}
