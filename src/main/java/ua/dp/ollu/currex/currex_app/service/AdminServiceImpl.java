package ua.dp.ollu.currex.currex_app.service;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;
import ua.dp.ollu.currex.currex_app.repository.AdminDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("appService")
public class AdminServiceImpl implements AdminService {
    private static final String NBU_JSON_REQUEST = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private static final String PB_JSON_REQUEST = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private AdminDao adminDao;
    private RestTemplate template;

    @Autowired
    public AdminServiceImpl(@Qualifier("adminJDBCTemplateDao") AdminDao adminDao, RestTemplate template) {
        this.adminDao = adminDao;
        this.template = template;
    }

    @Override
    public void updateReferences() {
        logger.info("updateReferences");
        //подтянуть данные с серверов нбу и приватбанка
        // с сервера НБУ на день раньше
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
        String date = format.format(calendar.getTime());
        String request = NBU_JSON_REQUEST + date;
        ResponseEntity<List<NBURate>> nbuResponse = template.exchange(request, HttpMethod.GET, null, new ParameterizedTypeReference<List<NBURate>>() {
        });
        if (nbuResponse.getStatusCode() != HttpStatus.OK || !nbuResponse.hasBody())
            throw new Error("От сервера нбу нет данных");
        List<NBURate> nbuRates = nbuResponse.getBody();
        if (nbuRates == null) return;
        ResponseEntity<List<PBRate>> pbResponse = template.exchange(PB_JSON_REQUEST, HttpMethod.GET, null, new ParameterizedTypeReference<List<PBRate>>() {
        });
        if (pbResponse.getStatusCode() != HttpStatus.OK || !pbResponse.hasBody())
            throw new Error("От сервера приватбанка нет данных");
        List<PBRate> pbRates = pbResponse.getBody();
        if (pbRates == null) return;
        //собрать список
        pbRates.stream().filter(pbRate -> pbRate.ccy.equals("RUR")).findFirst().ifPresent(pbRate -> pbRate.ccy = "RUB");
        List<Reference> references = pbRates.stream()
                .map(pbRate ->
                        nbuRates.stream()
                                .filter(nbuRate -> nbuRate.cc.equals(pbRate.ccy))
                                .findFirst()
                                .map(nbuRate -> {
                                    Reference ref = new Reference();
                                    ref.setNumberCode(nbuRate.r030);
                                    ref.setStringCode(nbuRate.cc);
                                    ref.setName(nbuRate.txt);
                                    ref.setRate(nbuRate.rate);
                                    ref.setSaleRate(pbRate.sale);
                                    ref.setBuyRate(pbRate.buy);
                                    return ref;
                                })
                                .orElse(null)
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        adminDao.updateReferences(references);
    }

    @Override
    public void removeOperation(long id) {
        logger.info("removeOperation(" + id + ")");
        adminDao.removeOperation(id);
    }

    @Override
    public List<Reference> getReferences() {
        return adminDao.getReferences();
    }

    @Override
    public List<Operation> getOperations() {
        return adminDao.getOperations();
    }

    @Data
    private static class PBRate {
        private String ccy;//буквенный код валюты обмена
        private String base_ccy; //"UAH",
        private Double buy;//курс покупки
        private Double sale;//курс продажи
    }

    @Data
    private static class NBURate {
        private Integer r030; //числовой код валюты обмена
        private String txt; // описание
        private Double rate; // курс
        private String cc; //буквенный код валюты обмена
        private String exchangedate; // дата установки

    }
}
