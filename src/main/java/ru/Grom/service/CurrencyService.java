package ru.Grom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.Grom.entity.Currency;
import ru.Grom.model.CurrencyRequest;
import ru.Grom.repository.CurrencyRepository;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository repository;

    public List<Currency> getAllCurrencies() {
        return repository.findAll();
    }

    public Currency getCurrencyById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Currency addCurrency(CurrencyRequest request) {
        Currency currency = new Currency();
        currency.setName(request.getName());
        currency.setBaseCurrency(request.getBaseCurrency());
        currency.setPriceChangeRange(request.getPriceChangeRange());
        currency.setDescription(request.getDescription());
        return repository.save(currency);
    }

    public Currency updateCurrency(UUID id, CurrencyRequest request) {
        Currency currency = repository.findById(id).orElseThrow();
        currency.setName(request.getName());
        currency.setBaseCurrency(request.getBaseCurrency());
        currency.setPriceChangeRange(request.getPriceChangeRange());
        currency.setDescription(request.getDescription());
        return repository.save(currency);
    }

    public void deleteCurrency(UUID id) {
        repository.deleteById(id);
    }
}