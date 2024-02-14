package integration.cb.client;

import integration.cb.config.CurrencyClientConfig;
import integration.cb.dto.generated.ValCurs;
import integration.cb.utils.DateFormatUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CbrClient {

    private final RestTemplate restTemplate;
    private final CurrencyClientConfig clientConfig;

    public ValCurs getCursToday() {
        HttpEntity<String> httpEntity = getHttpEntity();
        String uri = buildUriRequest(clientConfig.getCbUrl(), LocalDate.now());
        var responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, ValCurs.class);
        return responseEntity.getBody();
    }

    private String buildUriRequest(String baseUrl, LocalDate date) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam(clientConfig.getParamName(), DateFormatUtils.toFormat(date, clientConfig.getDatePattern()))
                .build().toUriString();
    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        return new HttpEntity<>(null, httpHeaders);
    }

}