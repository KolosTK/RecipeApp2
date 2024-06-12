package org.example.controller;

import jakarta.transaction.Transactional;
import org.example.model.Ingredient;
import org.example.repository.IIngredientRepository;
import org.example.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/ingredients")
public class IngredientController {
    @Autowired
    IngredientService ingredientService;
    @Autowired
    IIngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IIngredientRepository ingredientRepository, IngredientService ingredientService) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable("id") Integer id) {
        try {
            Ingredient ingredient = ingredientService.findById(id);
            if (ingredient != null) {
                return ResponseEntity.ok(ingredient);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
    @PostMapping
    public ResponseEntity<?> storeIngredient(@RequestBody Ingredient ingredient) {
        try {
            Ingredient savedIngredient = ingredientService.addIngredient(ingredient);
            return ResponseEntity.ok(savedIngredient);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // New endpoint for multiple ingredients
    @PostMapping("/bulk")
    public ResponseEntity<?> storeIngredients(@RequestBody List<Ingredient> ingredients) {
        try {
            List<Ingredient> savedIngredients = ingredientService.addAllIngredients(ingredients);
            return ResponseEntity.ok(savedIngredients);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @Transactional
    @PutMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable Integer id, @RequestBody Ingredient ingredientDetails) {
        try {
            return ingredientRepository.findById(id).map(ingredient -> {
                ingredient.setName(ingredientDetails.getName());
                ingredient.setPrice(ingredientDetails.getPrice());
                return ingredientRepository.save(ingredient);
            }).orElse(null);
        } catch (Exception e) {
            // Log the exception details here to diagnose the issue
            System.out.println("Update failed: " + e.getMessage());
            return null;
        }
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Integer id) {
        try {
            boolean isDeleted = ingredientService.deleteIngredient(id);
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
    public ResponseEntity<?> deleteIngredientByName(@PathVariable String name) {
        try {
            boolean isDeleted = ingredientService.deleteIngredientByName(name);
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
