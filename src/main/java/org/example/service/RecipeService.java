package org.example.service;

import org.example.model.Ingredient;
import org.example.model.Recipe;
import org.example.repository.IRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    @Autowired
    IRecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe findById(Integer id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public boolean deleteRecipe(Integer id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public boolean deleteRecipeByName(String name) {
        List<Recipe> recipes = recipeRepository.findByName(name);
        if (!recipes.isEmpty()) {
            for (Recipe recipe : recipes) {
                recipeRepository.delete(recipe);
            }
            return true;
        }
        return false;
    }
}
