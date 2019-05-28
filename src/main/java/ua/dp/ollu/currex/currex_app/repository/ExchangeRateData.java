package ua.dp.ollu.currex.currex_app.repository;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * подтянуть данные с серверов нбу и приватбанка
 */
@Service
public class ExchangeRateData {
    private NbuExtractor nbuExtractor;
    private PbExtractor pbExtractor;

    public ExchangeRateData(NbuXmlExtractor nbuExtractor, PbJsonExtractor pbExtractor) {
        this.nbuExtractor = nbuExtractor;
        this.pbExtractor = pbExtractor;
    }

    public List<NBURate> getNBURates() {
        return nbuExtractor.extract();
    }

    public List<PBRate> getPBRates() {
        return pbExtractor.extract();
    }
}
