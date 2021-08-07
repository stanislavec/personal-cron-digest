package org.personal.digest.configuration;

import lombok.Data;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Component
public class MeduzaComponent {

    public Map<String, MeduzaDocumentsResponse> getMeduzaNews() throws JSONException {

        String uri = "https://meduza.io/api/w5/search?chrono=news&locale=ru&page=0&per_page=10";

        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());

        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        return Objects.requireNonNull(restTemplate.exchange(uri, HttpMethod.GET, null, MeduzaResponse.class).getBody()).getDocuments();

    }

    @Data
    static class MeduzaResponse {
        Boolean has_next;
        Map<String, MeduzaDocumentsResponse> documents;
    }

    @Data
    public static class MeduzaDocumentsResponse {
        String title;
        String url;
    }

//    @Data
//    static class MeduzaResponse {
//        Boolean has_next;
//        MeduzaDocumentsResponse documents;
//    }
//
//    @Data
//    static class  MeduzaDocumentsResponse {
//        String title;
//        String url;
//    }
}
