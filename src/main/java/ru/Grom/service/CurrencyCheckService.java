package ru.Grom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.Grom.dto.CbrCurrencyResponse;
import ru.Grom.entity.Currency;
import ru.Grom.repository.CurrencyRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyCheckService {
    private static final String CBR_API_URL = "https://www.cbr-xml-daily.ru/daily_json.js";

    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void checkCurrencyChanges() {
        log.info("Starting currency rates check...");

        try {
            CbrCurrencyResponse response = restTemplate.getForObject(CBR_API_URL, CbrCurrencyResponse.class);

            if (response == null || response.getValute() == null) {
                log.warn("Empty response from CBR API");
                return;
            }

            List<Currency> trackedCurrencies = currencyRepository.findAll();

            if (trackedCurrencies.isEmpty()) {
                log.info("No currencies to track in database");
                return;
            }

            trackedCurrencies.forEach(currency -> {
                CbrCurrencyResponse.CbrCurrency cbrCurrency = response.getValute().get(currency.getBaseCurrency());

                if (cbrCurrency != null) {
                    checkCurrencyChange(currency, cbrCurrency);
                } else {
                    log.warn("Currency {} not found in CBR response", currency.getBaseCurrency());
                }
            });

            log.info("Currency rates check completed successfully");
        } catch (Exception e) {
            log.error("Error during currency rates check: {}", e.getMessage());
        }
    }

    private void checkCurrencyChange(Currency trackedCurrency, CbrCurrencyResponse.CbrCurrency cbrCurrency) {
        double changePercentage = calculatePercentageChange(cbrCurrency.getPrevious(), cbrCurrency.getValue());
        double threshold = parseThreshold(trackedCurrency.getPriceChangeRange());

        if (Math.abs(changePercentage) >= Math.abs(threshold)) {
            String direction = changePercentage > 0 ? "вырос" : "упал";
            String message = String.format("%s %s на %.2f%%",
                    trackedCurrency.getName(),
                    direction,
                    Math.abs(changePercentage));

            log.info(message);

            if (trackedCurrency.getDescription() != null && !trackedCurrency.getDescription().isEmpty()) {
                log.info("Description: {}", trackedCurrency.getDescription());
            }
        }
    }

    private double calculatePercentageChange(double previous, double current) {
        return ((current - previous) / previous) * 100;
    }

    private double parseThreshold(String priceChangeRange) {
        try {
            String numericPart = priceChangeRange.replaceAll("[^0-9.-+]", "");
            if (numericPart.startsWith("+")) {
                numericPart = numericPart.substring(1);
            }
            return Double.parseDouble(numericPart);
        } catch (NumberFormatException e) {
            log.warn("Invalid priceChangeRange format: {}", priceChangeRange);
            return 0.0;
        }
    }
}