package ua.dp.ollu.currex.currex_app.repository;

import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;

import java.util.List;

public interface UserDao {
    List<Reference> getReferences();

    List<Operation> getOperations();

    void addExchange(Operation operation);
}
