package ua.dp.ollu.currex.currex_app.repository;

import lombok.Data;

@Data
public class PBRate {
    private String ccy;//буквенный код валюты обмена
    private String base_ccy; //"UAH",
    private Double buy;//курс покупки
    private Double sale;//курс продажи
}
