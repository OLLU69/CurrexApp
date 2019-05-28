package ua.dp.ollu.currex.currex_app.repository;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service("nbuExtractor")
class NbuXmlExtractor implements NbuExtractor {
    private static final String NBU_XML_REQUEST = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange";
    private RestTemplate template;

    NbuXmlExtractor(RestTemplate template) {
        this.template = template;
    }

    @Override
    public List<NBURate> extract() {
        // с сервера НБУ на день раньше
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
        String date = format.format(calendar.getTime());
        String request = NBU_XML_REQUEST + date;
        ResponseEntity<exchange> nbuResponse = template.exchange(request, HttpMethod.GET, null, exchange.class);
        if (nbuResponse.getStatusCode() != HttpStatus.OK || !nbuResponse.hasBody())
            throw new Error("От сервера нбу нет данных");
        exchange body = nbuResponse.getBody();
        if (body == null) return null;
        return body.getCurrency();
    }

    @Data
    static class exchange {
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<NBURate> currency;
    }
}
