package ua.dp.ollu.currex.currex_app.repository;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service("pbExtractor")
class PbJsonExtractor implements PbExtractor {
    private static final String PB_JSON_REQUEST = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private RestTemplate template;

    PbJsonExtractor(RestTemplate template) {
        this.template = template;
    }

    @Override
    public List<PBRate> extract() {
        ResponseEntity<List<PBRate>> pbResponse = template.exchange(PB_JSON_REQUEST, HttpMethod.GET, null, new ParameterizedTypeReference<List<PBRate>>() {
        });
        if (pbResponse.getStatusCode() != HttpStatus.OK || !pbResponse.hasBody()) {
            throw new Error("От сервера приватбанка нет данных");
        }
        return pbResponse.getBody();
    }
}
