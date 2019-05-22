package ua.dp.ollu.currex.currex_app.service;

import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;

import java.util.List;

public interface UserService {
    List<Reference> getReferences();

    List<Operation> getOperations();

    void addExchange(Operation operation);
}
