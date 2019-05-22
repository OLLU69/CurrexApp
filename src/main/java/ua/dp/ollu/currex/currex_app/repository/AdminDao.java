package ua.dp.ollu.currex.currex_app.repository;

import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;

import java.util.List;


public interface AdminDao {
    List<Reference> getReferences();

    List<Operation> getOperations();

    void removeOperation(long id);

    void updateReferences(List<Reference> references);
}
