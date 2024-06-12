package org.example.controller;

import jakarta.transaction.Transactional;
import org.example.model.Recipe;
import org.example.repository.IRecipeRepository;
import org.example.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/recipes")
public class RecipeController {
    @Autowired
    RecipeService recipeService;
    @Autowired
    IRecipeRepository recipeRepository;

    @Autowired
    public RecipeController(IRecipeRepository recipeRepository, RecipeService recipeService) {
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
       
            List<Recipe> recipes = recipeService.getAllRecipes();
            return ResponseEntity.ok(recipes);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") Integer id) {
        try {
            Recipe recipe = recipeService.findById(id);
            if (recipe != null) {
                return ResponseEntity.ok(recipe);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
    @PostMapping
    public ResponseEntity<Recipe> storeRecipe(@RequestBody Recipe recipe) {
        try {
            Recipe savedRecipe = recipeService.addRecipe(recipe);
            return ResponseEntity.ok(savedRecipe);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); 
        }
    }
    @Transactional
    @PutMapping("/{id}")
    public Recipe updateRecipe(@PathVariable Integer id, @RequestBody Recipe recipeDetails) {
        try {
            return recipeRepository.findById(id).map(recipe -> {
                recipe.setName(recipeDetails.getName());
                recipe.setPrice(recipeDetails.getPrice());
                return recipeRepository.save(recipe);
            }).orElse(null);
        } catch (Exception e) {
            // Log the exception details here to diagnose the issue
            System.out.println("Update failed: " + e.getMessage());
            return null;
        }
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Integer id) {
        try {
            boolean isDeleted = recipeService.deleteRecipe(id);
            if (isDeleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping("/deleteByName/{name}")
    public ResponseEntity<?> deleteRecipeByName(@PathVariable String name) {
        try {
            boolean isDeleted = recipeService.deleteRecipeByName(name);
            if (isDeleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
