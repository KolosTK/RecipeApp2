package org.example.service;

import org.example.model.Ingredient;
import org.example.repository.IIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    IIngredientRepository ingredientRepository;
    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredientRepository.existsByName(ingredient.getName())) {
            throw new IllegalStateException("Ingredient with name " + ingredient.getName() + " already exists.");
        }
        return ingredientRepository.save(ingredient);
    }
    
    public Ingredient findById(Integer id) {
        return ingredientRepository.findById(id).orElse(null);
    }
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public boolean deleteIngredient(Integer id) {
        if (ingredientRepository.existsById(id)) {
            ingredientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteIngredientByName(String name) {
        List<Ingredient> ingredients = ingredientRepository.findByName(name);
        if (!ingredients.isEmpty()) {
            for (Ingredient ingredient : ingredients) {
                ingredientRepository.delete(ingredient);
            }
            return true;
        }
        return false;
    }

    public Ingredient updateIngredient(Integer id, Ingredient ingredientDetails) {
        return ingredientRepository.findById(id).map(ingredient -> {
            ingredient.setName(ingredientDetails.getName());
            ingredient.setPrice(ingredientDetails.getPrice());
            return ingredientRepository.save(ingredient);
        }).orElse(null);
    }

    public List<Ingredient> addAllIngredients(List<Ingredient> ingredients) {
        return ingredientRepository.saveAll(ingredients);
    }
}
