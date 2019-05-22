package ua.dp.ollu.currex.currex_app.repository;

import org.springframework.stereotype.Service;
import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;

import java.util.List;

@Service("adminTestDao")
public
class AdminTestDaoImpl implements AdminDao {

    @Override
    public List<Reference> getReferences() {
        return DataBase.references;
    }

    @Override
    public List<Operation> getOperations() {
        return DataBase.operations;
    }

    @Override
    public void removeOperation(long id) {
        DataBase.removeOperation(id);
    }

    @Override
    public void updateReferences(List<Reference> references) {
        DataBase.references.clear();
        DataBase.references.addAll(references);
    }
}
