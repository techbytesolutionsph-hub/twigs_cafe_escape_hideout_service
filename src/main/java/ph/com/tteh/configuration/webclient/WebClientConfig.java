package ph.com.tteh.configuration.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import ph.com.tteh.configuration.properties.RedisPropertiesConfig;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final RedisPropertiesConfig redisConfig;

    @Bean
    public WebClient webClient() {

        return WebClient.builder()
                .baseUrl(redisConfig.getUrl())
                .defaultHeaders(headers -> headers.setBearerAuth(redisConfig.getToken()))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
