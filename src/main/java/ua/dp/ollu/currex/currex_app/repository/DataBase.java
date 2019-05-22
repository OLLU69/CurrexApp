package ua.dp.ollu.currex.currex_app.repository;

import ua.dp.ollu.currex.currex_app.model.Operation;
import ua.dp.ollu.currex.currex_app.model.Reference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

class DataBase {
    static List<Operation> operations = new ArrayList<>();
    static List<Reference> references = new ArrayList<>();
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
    private static long id = 1;

    static {
        DataBase.references.addAll(Arrays.asList(
                DataBase.newReference(getNewId(), 0, "RUB", "Рубль", 0.50, 0.45, 0.55),
                DataBase.newReference(getNewId(), 1, "USD", "Доллар", 30.50, 30.25, 30.75),
                DataBase.newReference(getNewId(), 2, "EUR", "Евро", 40.50, 40.45, 40.55)
//                DataBase.newReference(getNewId(), 2, "UAH", "Гривна", 1, 1, 1)
        ));
        DataBase.operations.addAll(Arrays.asList(
                newOperation(1L, "22.04.19", "18:55", "RUB", "UAH", 0.45, 120, 120 * 0.45),
                newOperation(2L, "22.04.21", "14:55", "UAH", "RUB", 1.0 / 0.55, 150, 150 / 0.55),
                newOperation(3L, "22.04.24", "18:00", "USD", "UAH", 30.25, 120, 120 * 20.25)
        ));
    }

    private static Operation newOperation(long id, String date, String time, String currencyBuy, String currencySale, double exchangeRate, int buySumm, double saleSumm) {
        Operation operation = new Operation();
        operation.setId(id);
        operation.setDate(newDate(date, time));
        operation.setCurrencyBuy(currencyBuy);
        operation.setCurrencySale(currencySale);
        operation.setExchangeRate(exchangeRate);
        operation.setBuySumm(buySumm);
        operation.setSaleSumm(saleSumm);
        return operation;
    }

    private static Date newDate(String date, String time) {
        try {
            return formatter.parse(date + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Reference newReference(long id, int numberCode, String str, String name, double rate, double buyRate, double saleRate) {
        Reference r = new Reference();
        r.setId(id);
        r.setNumberCode(numberCode);
        r.setStringCode(str);
        r.setName(name);
        r.setRate(rate);
        r.setBuyRate(buyRate);
        r.setSaleRate(saleRate);
        return r;
    }

    static void removeOperation(long id) {
        Operation operation = operations.stream().filter(op -> op.getId().equals(id)).findFirst().orElse(null);
        operations.remove(operation);
    }

    static void addOperation(Operation operation) {
        operation.setId(getNewId());
        operation.setDate(new Date());
        operations.add(operation);
    }

    private static Long getNewId() {
        return id++;
    }
}
