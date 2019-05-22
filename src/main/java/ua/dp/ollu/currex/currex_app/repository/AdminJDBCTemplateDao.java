package ua.dp.ollu.currex.currex_app.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;

import java.util.List;

import static ua.dp.ollu.currex.currex_app.model.Operation.STATUS_DELETED;

@Service("adminJDBCTemplateDao")
public class AdminJDBCTemplateDao implements AdminDao {
    private final JdbcTemplate jdbcTemplate;

    public AdminJDBCTemplateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reference> getReferences() {
        return jdbcTemplate.query("select * from APP.REFERENCE", CurrexMapper.getReferenceRowMapper());
    }

    @Override
    public List<Operation> getOperations() {
        return jdbcTemplate.query("select * from APP.OPERATIONS WHERE STATUS <> ?", CurrexMapper.getOperationRowMapper(), STATUS_DELETED);
    }

    @Override
    public void removeOperation(long id) {
        jdbcTemplate.update("UPDATE APP.OPERATIONS SET STATUS = ? WHERE ID = ? ", STATUS_DELETED, id);
    }

    @Override
    public void updateReferences(List<Reference> references) {
        //очистить  базу
        jdbcTemplate.execute("TRUNCATE TABLE APP.REFERENCE");
        //сохранить в базу
        for (Reference ref : references) {
            jdbcTemplate.update("INSERT INTO APP.REFERENCE (num_code, str_code, name, rate, sale_rate, buy_rate)" +
                    "VALUES (?,?,?,?,?,?)", ref.getNumberCode(), ref.getStringCode(), ref.getName(), ref.getRate(), ref.getSaleRate(), ref.getBuyRate());
        }
    }
}
