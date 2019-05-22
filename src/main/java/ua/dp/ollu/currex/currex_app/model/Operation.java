package ua.dp.ollu.currex.currex_app.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "operations")
@Data
public class Operation {
    public static final String STATUS_LIVE = "LIVE";
    public static final String STATUS_DELETED = "DELETED";
    private Long id;
    private Date date = new Date();
    private String currencyBuy;
    private String currencySale;
    private double exchangeRate;
    private double buySumm;
    private double saleSumm;
    private String status = STATUS_LIVE; //live/deleted
}
