package ua.dp.ollu.currex.currex_app.repository;

import lombok.Data;

@Data
public class NBURate {
    private Integer r030; //числовой код валюты обмена
    private String txt; // описание
    private Double rate; // курс
    private String cc; //буквенный код валюты обмена
    private String exchangedate; // дата установки

}
