package org.example.model;

import ch.qos.logback.core.joran.sanity.Pair;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "RECIPE")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(unique = true)
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column
    @NotNull
    private BigDecimal price;

    @Column
    @Enumerated(EnumType.STRING)    
    private Rating rating;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ingredient> ingredients= new ArrayList<>();
    

    public Recipe() {
    }
    public Recipe(String name) {
        this.name = name;
    }

    public Recipe(String name, Difficulty difficulty, BigDecimal price, Rating rating, List<Ingredient> ingredients) {
        this.name = name;
        this.difficulty = difficulty;
        this.price = price;
        this.rating = rating;
        this.ingredients = ingredients;
        if(!ingredients.isEmpty()) calculatePrice();
    }

    private void calculatePrice() {
//        price = BigDecimal.ZERO;
//       for (RecipeIngredient item : ingredients) {
//       price.add(item.getIngredient().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
//       }
    }


}

