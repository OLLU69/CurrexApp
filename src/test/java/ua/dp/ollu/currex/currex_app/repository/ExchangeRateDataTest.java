package ua.dp.ollu.currex.currex_app.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ExchangeRateDataTest {

    @Test
    public void xmlMapTest() throws IOException {
        ObjectMapper mapper = new XmlMapper();
        NbuXmlExtractor.exchange rates = new NbuXmlExtractor.exchange();
        rates.setCurrency(new ArrayList<>());
        NBURate rate = new NBURate();
        rate.setR030(30);
        rate.setTxt("Австралійський долар");
        rate.setRate(18.233238);
        rate.setCc("AUD");
        rate.setExchangedate("28.05.2019");
        rates.getCurrency().add(rate);
        rates.getCurrency().add(rate);
        String xml = mapper.writeValueAsString(rates);
        System.out.println(xml);
        assertNotNull(xml);
        assertFalse(xml.isEmpty());
        String expectedXml = "<exchange>" +
                "<currency><r030>30</r030><txt>Австралійський долар</txt><rate>18.233238</rate><cc>AUD</cc><exchangedate>28.05.2019</exchangedate></currency>" +
                "<currency><r030>30</r030><txt>Австралійський долар</txt><rate>18.233238</rate><cc>AUD</cc><exchangedate>28.05.2019</exchangedate></currency>" +
                "</exchange>";
        assertEquals(expectedXml, xml);

        NbuXmlExtractor.exchange exchange = mapper.readValue(xml, NbuXmlExtractor.exchange.class);
        assertEquals(rates, exchange);

    }
}
