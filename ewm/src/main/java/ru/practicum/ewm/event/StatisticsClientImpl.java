package ru.practicum.ewm.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class StatisticsClientImpl implements StatisticsClient {
    @Value("ewn-main-service")
    private final String app; //Название сервиса
    private final String url;//URI сервиса
    private final ObjectMapper objectMapper;

    public StatisticsClientImpl(@Value("ewn-main-service") String app,
                                @Value("${stats.url}")
                                String url, ObjectMapper objectMapper) {
        this.app = app;
        this.url = url;
        this.objectMapper = objectMapper;
    }


    @Override
    @SneakyThrows
    public void saveEndpointHit(HttpServletRequest request) {
        Hit hit = new Hit(
                app,
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now()
        );
        String requestBody = objectMapper.writeValueAsString(hit);

        HttpRequest requestNew = HttpRequest.newBuilder()
                .uri(URI.create(url + "/hit"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpClient.newHttpClient()
                .sendAsync(requestNew, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode);
    }

    @AllArgsConstructor
    @Getter
    private static class Hit {
        private String app; //Название сервиса
        private String uri;//URI сервиса
        private String ip;//IP-адрес пользователя, осуществившего запрос
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime timestamp;
    }
}
