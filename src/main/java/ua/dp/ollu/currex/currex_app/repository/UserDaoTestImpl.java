package ua.dp.ollu.currex.currex_app.repository;

import org.springframework.stereotype.Service;
import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;

import java.util.List;

@Service("userDaoTest")
public
class UserDaoTestImpl implements UserDao {

    @Override
    public List<Reference> getReferences() {
        return DataBase.references;
    }

    @Override
    public List<Operation> getOperations() {
        return DataBase.operations;
    }

    @Override
    public void addExchange(Operation operation) {
        DataBase.addOperation(operation);
    }
}
