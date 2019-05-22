package ua.dp.ollu.currex.currex_app.service;

import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;

import java.util.List;

public interface AdminService {
    void updateReferences();

    void removeOperation(long id);

    List<Reference> getReferences();

    List<Operation> getOperations();
}
