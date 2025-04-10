package ru.Grom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.Grom.entity.Currency;
import ru.Grom.model.CurrencyRequest;
import ru.Grom.service.CurrencyService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    private final CurrencyService service;

    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Currency>> getCurrencies() {
        return ResponseEntity.ok(service.getAllCurrencies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Currency> getCurrency(@PathVariable UUID id) {
        Currency currency = service.getCurrencyById(id);
        return currency != null ? ResponseEntity.ok(currency) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Currency> addCurrency(@RequestBody CurrencyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCurrency(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable UUID id, @RequestBody CurrencyRequest request) {
        return ResponseEntity.ok(service.updateCurrency(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable UUID id) {
        service.deleteCurrency(id);
        return ResponseEntity.noContent().build();
    }
}