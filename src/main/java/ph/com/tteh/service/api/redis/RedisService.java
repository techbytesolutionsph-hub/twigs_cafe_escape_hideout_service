package ph.com.tteh.service.api.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;
import ph.com.tteh.configuration.properties.RedisPropertiesConfig;
import ph.com.tteh.exception.ServiceException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Service layer responsible for interacting with Upstash Redis using REST API.
 *
 * <p>This service provides basic Redis operations such as:
 * SET, GET, and DELETE using WebClient.</p>
 *
 * <p>All keys and values are URL-encoded to ensure safe transmission
 * over HTTP.</p>
 */
@RequiredArgsConstructor
@Service
public class RedisService {

    private final WebClient webClient;
    private final RedisPropertiesConfig redisConfig;

    /**
     * Stores a key-value pair in Redis.
     *
     * @param key Redis key (e.g., cart:101)
     * @param value JSON string or plain value to store
     * @return Redis response as String
     */
    public String set(String key, String value) {

        return webClient.post()
                .uri(redisConfig.getUrl() + "/set/" + encode(key) + "/" + encode(value))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new ServiceException("Redis SET failed: " + body)
                                ))
                )
                .bodyToMono(String.class)
                .block();
    }

    /**
     * Retrieves a value from Redis by key.
     *
     * @param key Redis key (e.g., cart:101)
     * @return Stored value as String (usually JSON)
     */
    public String get(String key) {

        return webClient.get()
                .uri(redisConfig.getUrl() + "/get/" + encode(key))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new ServiceException("Redis GET failed: " + body)
                                ))
                )
                .bodyToMono(String.class)
                .block();
    }

    /**
     * Deletes a key from Redis.
     *
     * @param key Redis key to delete
     * @return Redis response as String
     */
    public String delete(String key) {

        return webClient.post()
                .uri(redisConfig.getUrl() + "/del/" + encode(key))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new ServiceException("Redis DELETE failed: " + body)
                                ))
                )
                .bodyToMono(String.class)
                .block();
    }

    /**
     * Encodes values to ensure safe URL transmission.
     *
     * @param value raw key or value
     * @return URL-encoded string
     */
    private String encode(String value) {
        return UriUtils.encode(value, StandardCharsets.UTF_8);
    }
}