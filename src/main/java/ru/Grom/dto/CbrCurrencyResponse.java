package ru.Grom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

@Data
public class CbrCurrencyResponse {
    @JsonProperty("Valute")
    private Map<String, CbrCurrency> valute;

    @Data
    public static class CbrCurrency {
        private String ID;
        private String NumCode;
        private String CharCode;
        private Integer Nominal;
        private String Name;
        private Double Value;
        private Double Previous;
    }
}