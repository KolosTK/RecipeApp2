package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "INGREDIENT")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(unique = true, nullable = false)
    private Integer id;

    @NotEmpty
    @Column(nullable = false)
    private String name;
    
    @Positive
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;  // Back-reference to the Recipe

    // Default constructor for JPA
    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Ingredient(Integer id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

