package ratings.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ratings.services.AuthoritiesService;
import ratings.services.AuthoritiesServiceImpl;

@Configuration
public class AuthoritiesConfig {
    @Value(value = "${headers.api_version.key}")
    private String apiVersionKey;
    @Value(value = "${headers.api_version.value}")
    private String apiVersionValue;
    @Value(value = "${uri.scheme}")
    private String scheme;
    @Value(value = "${uri.host}")
    private String host;
    @Value(value = "${authorities.uri.path}")
    private String path;
    @Value(value = "${authorities.uri.pageNumber}")
    private int pageNumber;
    @Value(value = "${authorities.uri.pageSize}")
    private int pageSize;

    @Bean
    AuthoritiesService authoritiesService(RestTemplate restTemplate) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme(scheme)
                .host(host)
                .path(path);

        HttpHeaders headers = new HttpHeaders();
        headers.set(apiVersionKey, apiVersionValue);

        return new AuthoritiesServiceImpl(headers, builder, restTemplate, pageNumber, pageSize);
    }
}
