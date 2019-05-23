package ua.dp.ollu.currex.currex_app.repository;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * подтянуть данные с серверов нбу и приватбанка
 */
@Service
public class ExchangeRateData {
    private static final String PB_JSON_REQUEST = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private static final String NBU_JSON_REQUEST = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private RestTemplate template;

    public ExchangeRateData(RestTemplate template) {
        this.template = template;
    }

    public List<NBURate> getNBURates() {
        // с сервера НБУ на день раньше
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
        String date = format.format(calendar.getTime());
        String request = ExchangeRateData.NBU_JSON_REQUEST + date;
        ResponseEntity<List<NBURate>> nbuResponse = template.exchange(request, HttpMethod.GET, null, new ParameterizedTypeReference<List<NBURate>>() {
        });
        if (nbuResponse.getStatusCode() != HttpStatus.OK || !nbuResponse.hasBody())
            throw new Error("От сервера нбу нет данных");
        return nbuResponse.getBody();
    }

    public List<PBRate> getPBRates() {
        ResponseEntity<List<PBRate>> pbResponse = template.exchange(ExchangeRateData.PB_JSON_REQUEST, HttpMethod.GET, null, new ParameterizedTypeReference<List<PBRate>>() {
        });
        if (pbResponse.getStatusCode() != HttpStatus.OK || !pbResponse.hasBody()) {
            throw new Error("От сервера приватбанка нет данных");
        }
        return pbResponse.getBody();
    }
}
