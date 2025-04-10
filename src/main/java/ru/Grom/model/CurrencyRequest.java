package ru.Grom.model;

import lombok.Data;

@Data
public class CurrencyRequest {
    private String name;
    private String baseCurrency = "RUB";
    private String priceChangeRange;
    private String description;
}