package ua.dp.ollu.currex.currex_app.repository;

import org.springframework.jdbc.core.RowMapper;
import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;

class CurrexMapper {
    private static RowMapper<Operation> operationRowMapper = (resultSet, row) -> {
        Operation operation = new Operation();
        operation.setId(resultSet.getLong("id"));
        operation.setDate(resultSet.getTimestamp("date"));
        operation.setCurrencyBuy(resultSet.getString("currency_buy"));
        operation.setCurrencySale(resultSet.getString("currency_sale"));
        operation.setExchangeRate(resultSet.getDouble("rate"));
        operation.setBuySumm(resultSet.getDouble("buy_summ"));
        operation.setSaleSumm(resultSet.getDouble("sale_summ"));
        operation.setStatus(resultSet.getString("status"));
        return operation;
    };
    private static RowMapper<Reference> referenceRowMapper = (resultSet, row) -> {
        Reference reference = new Reference();
        reference.setId(resultSet.getLong("id"));
        reference.setNumberCode(resultSet.getInt("num_code"));
        reference.setStringCode(resultSet.getString("str_code"));
        reference.setName(resultSet.getString("name"));
        reference.setRate(resultSet.getDouble("rate"));
        reference.setSaleRate(resultSet.getDouble("sale_rate"));
        reference.setBuyRate(resultSet.getDouble("buy_rate"));
        return reference;
    };

    static RowMapper<Reference> getReferenceRowMapper() {
        return referenceRowMapper;
    }

    static RowMapper<Operation> getOperationRowMapper() {
        return operationRowMapper;
    }
}
