package ua.dp.ollu.currex.currex_app.repository;

import org.springframework.stereotype.Service;
import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service("userDaoJDBC")
public
class UserDaoJDBCImpl implements UserDao {
    static {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Reference> getReferences() {

        List<Reference> references = new ArrayList<>();
        executeQuery("select * from APP.REFERENCE", resultSet -> {
            int row = 0;
            while (resultSet.next()) {
                Reference reference = CurrexMapper.getReferenceRowMapper().mapRow(resultSet, row++);
                references.add(reference);
            }
        });
        return references;
    }

    @Override
    public List<Operation> getOperations() {
        List<Operation> operations = new ArrayList<>();
        executeQuery("select * from APP.OPERATIONS WHERE STATUS <> 'DELETED'", resultSet -> {
            int row = 0;
            while (resultSet.next()) {
                Operation operation = CurrexMapper.getOperationRowMapper().mapRow(resultSet, row++);
                operations.add(operation);
            }
        });
        return operations;
    }

    @Override
    public void addExchange(Operation operation) {
        getConnection(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into APP.OPERATIONS(DATE, CURRENCY_BUY, CURRENCY_SALE, RATE, BUY_SUMM, SALE_SUMM) " +
                            "values (?,?,?,?,?,?)");
            statement.setTimestamp(1, new Timestamp(operation.getDate().getTime()));
            statement.setString(2, operation.getCurrencyBuy());
            statement.setString(3, operation.getCurrencySale());
            statement.setDouble(4, operation.getExchangeRate());
            statement.setDouble(5, operation.getBuySumm());
            statement.setDouble(6, operation.getSaleSumm());
            statement.execute();
        });
        DataBase.addOperation(operation);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:derby:/home/oleg/IdeaProjects/PrivatBank/PrivatBankCurrency/DB/Currex",
                "aaa",
                "aaa");
    }

    private void getConnection(OnConnection onConnection) {
        Connection connection = null;
        try {
            connection = getConnection();
            onConnection.use(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    private void closeConnection(Connection connection) {
        if (connection == null) return;
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeQuery(String sql, Execute execute) {
        getConnection(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            execute.execute(resultSet);
        });
    }

    @FunctionalInterface
    interface Execute {
        void execute(ResultSet resultSet) throws SQLException;
    }

    @FunctionalInterface
    interface OnConnection {
        void use(Connection connection) throws SQLException;
    }
}
