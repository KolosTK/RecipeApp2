package org.example.controller;

import org.example.model.Ingredient;
import org.example.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/ingredients")
public class IngredientController {
    @Autowired
    IngredientService ingredientService;

    @PostMapping("/store")
    public ResponseEntity<Ingredient> storeIngredient(@RequestBody Ingredient ingredient) {
        try {
            Ingredient savedIngredient = ingredientService.addIngredient(ingredient);
            return ResponseEntity.ok(savedIngredient);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Or handle the exception more gracefully
        }
    }
}
