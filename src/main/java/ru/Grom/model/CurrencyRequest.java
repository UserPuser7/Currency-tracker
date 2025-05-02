package ru.Grom.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CurrencyRequest {
    private String name;
    private String baseCurrency = "RUB";
    private String priceChangeRange;
    private String description;
}