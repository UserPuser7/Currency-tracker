package ru.Grom.controller;


import ru.Grom.model.Currency;
import ru.Grom.model.CurrencyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private List<Currency> currencies = new ArrayList<>();

    // GET /currencies
    @GetMapping
    public ResponseEntity<List<Currency>> getCurrencies() {
        return ResponseEntity.ok(currencies); // 200 OK
    }

    // POST /currencies
    @PostMapping
    public ResponseEntity<Void> addCurrency(@RequestBody CurrencyRequest request) {
        Currency currency = new Currency();
        currency.setId(UUID.randomUUID().toString()); // Генерация уникального ID
        currency.setName(request.getName());
        currency.setBaseCurrency(request.getBaseCurrency());
        currency.setPriceChangeRange(request.getPriceChangeRange());
        currency.setDescription(request.getDescription());

        currencies.add(currency);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
    }

    // GET /currencies/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Currency> getCurrency(@PathVariable String id) {
        Optional<Currency> currency = currencies.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        return currency.map(ResponseEntity::ok) // 200 OK
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // 404 Not Found
    }

    // PUT /currencies/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCurrency(@PathVariable String id, @RequestBody CurrencyRequest request) {
        Optional<Currency> currencyOptional = currencies.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (currencyOptional.isPresent()) {
            Currency currency = currencyOptional.get();
            currency.setName(request.getName());
            currency.setBaseCurrency(request.getBaseCurrency());
            currency.setPriceChangeRange(request.getPriceChangeRange());
            currency.setDescription(request.getDescription());

            return ResponseEntity.ok().build(); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }

    // DELETE /currencies/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable String id) {
        boolean removed = currencies.removeIf(c -> c.getId().equals(id));

        if (removed) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }
}