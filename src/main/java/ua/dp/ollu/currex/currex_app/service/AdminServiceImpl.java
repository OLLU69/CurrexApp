package ua.dp.ollu.currex.currex_app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;
import ua.dp.ollu.currex.currex_app.repository.AdminDao;
import ua.dp.ollu.currex.currex_app.repository.ExchangeRateData;
import ua.dp.ollu.currex.currex_app.repository.NBURate;
import ua.dp.ollu.currex.currex_app.repository.PBRate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("appService")
public class AdminServiceImpl implements AdminService {
    private final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private AdminDao adminDao;
    private ExchangeRateData exchangeRateData;

    @Autowired
    public AdminServiceImpl(@Qualifier("adminJDBCTemplateDao") AdminDao adminDao, ExchangeRateData exchangeRateData) {
        this.adminDao = adminDao;
        this.exchangeRateData = exchangeRateData;
    }

    @Override
    public void updateReferences() {
        logger.info("updateReferences");
        List<NBURate> nbuRates = exchangeRateData.getNBURates();
        if (nbuRates == null) return;
        List<PBRate> pbRates = exchangeRateData.getPBRates();
        if (pbRates == null) return;
        //заменить RUR на RUB
        pbRates.stream().filter(pbRate -> pbRate.getCcy().equals("RUR")).findFirst().ifPresent(pbRate -> pbRate.setCcy("RUB"));
        //собрать список
        List<Reference> references = pbRates.stream()
                .map(pbRate ->
                        nbuRates.stream()
                                .filter(nbuRate -> nbuRate.getCc().equals(pbRate.getCcy()))
                                .findFirst()
                                .map(nbuRate -> newReference(pbRate, nbuRate))
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

    private Reference newReference(PBRate pbRate, NBURate nbuRate) {
        Reference ref = new Reference();
        ref.setNumberCode(nbuRate.getR030());
        ref.setStringCode(nbuRate.getCc());
        ref.setName(nbuRate.getTxt());
        ref.setRate(nbuRate.getRate());
        ref.setSaleRate(pbRate.getSale());
        ref.setBuyRate(pbRate.getBuy());
        return ref;
    }
}
