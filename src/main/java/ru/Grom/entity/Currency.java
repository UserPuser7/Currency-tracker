package ru.Grom.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Setter
@Getter // Эти типы чтобы разница в ветках была
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "base_currency", nullable = false)
    private String baseCurrency = "RUB";

    @Column(name = "price_change_range", nullable = false)
    private String priceChangeRange;

    private String description;
}