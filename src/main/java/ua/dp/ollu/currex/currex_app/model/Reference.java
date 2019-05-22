package ua.dp.ollu.currex.currex_app.model;

import lombok.Data;

@Data
public class Reference {
    private Long id;
    private int numberCode; //код числовой
    private String stringCode;// код символьный
    private String name;// 	название
    private double rate;// курс НБУ
    private double saleRate;// курс продажи
    private double buyRate;// курс покупки
}
