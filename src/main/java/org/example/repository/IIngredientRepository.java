package org.example.repository;

import org.example.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIngredientRepository extends JpaRepository<Ingredient, Integer> {
}
