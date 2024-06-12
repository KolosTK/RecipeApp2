package org.example.repository;

import org.example.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IIngredientRepository extends JpaRepository<Ingredient, Integer> {
    List<Ingredient> findByName(String name);
}
