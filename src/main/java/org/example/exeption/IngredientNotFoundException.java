package org.example.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException() {
        super("Run not found");
    }
    public IngredientNotFoundException(String message) {
        super(message);
    }
}
