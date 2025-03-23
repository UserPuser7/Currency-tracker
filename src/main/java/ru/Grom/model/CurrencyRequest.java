package ru.Grom.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data

public class CurrencyRequest {

    private String name;
    private String baseCurrency;
    private String priceChangeRange;
    private String description;

}