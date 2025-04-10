package ru.Grom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Grom.entity.Currency;
import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
}