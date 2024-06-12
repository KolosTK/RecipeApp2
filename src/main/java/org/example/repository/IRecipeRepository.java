package org.example.repository;

import org.example.model.Ingredient;
import org.example.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByName(String name);
}
