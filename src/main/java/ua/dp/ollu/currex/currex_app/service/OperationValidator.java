package ua.dp.ollu.currex.currex_app.service;

import org.springframework.stereotype.Component;
import ua.dp.ollu.currex.currex_app.model.Operation;

@Component
class OperationValidator {
    Operation validate(Operation operation) {
        if (operation.getBuySumm() == 0.0 || operation.getSaleSumm() == 0.0) throw new Error("Пустое поле суммы");
        if (operation.getExchangeRate() == 0.0) throw new Error("Пустое поле курса");
        return operation;
    }
}
