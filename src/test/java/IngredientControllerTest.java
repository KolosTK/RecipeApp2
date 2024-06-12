import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.example.controller.IngredientController;
import org.example.model.Ingredient;
import org.example.service.IngredientService;
import org.example.repository.IIngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IngredientControllerTest {
    private MockMvc mockMvc;
    private IngredientService ingredientService;
    private IIngredientRepository ingredientRepository;

    @BeforeEach
    public void setup() {
        ingredientService = mock(IngredientService.class);
        ingredientRepository = mock(IIngredientRepository.class);
        IngredientController controller = new IngredientController(ingredientRepository, ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllIngredients() throws Exception {
        List<Ingredient> ingredients = Arrays.asList(new Ingredient(1, "Salt", new BigDecimal("3.00")),
                new Ingredient(2, "Sugar", new BigDecimal("1.50")));
        when(ingredientService.getAllIngredients()).thenReturn(ingredients);

        mockMvc.perform(get("/api/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Salt")))
                .andExpect(jsonPath("$[1].name", is("Sugar")));
    }
    @Test
    public void testGetIngredientById() throws Exception {
        Ingredient ingredient = new Ingredient(1, "Salt", new BigDecimal("3.00"));
        when(ingredientService.findById(1)).thenReturn(ingredient);

        mockMvc.perform(get("/api/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Salt")));
    }
    @Test
    public void testStoreIngredient() throws Exception {
        Ingredient ingredient = new Ingredient(1, "Pepper", new BigDecimal("2.00"));
        when(ingredientService.addIngredient(any(Ingredient.class))).thenReturn(ingredient);

        mockMvc.perform(post("/api/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Pepper\",\"price\":\"2.00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Pepper")));
    }
    @Test
    public void testUpdateIngredient() throws Exception {
        Ingredient updatedIngredient = new Ingredient(1, "Salt", new BigDecimal("5.00"));
        when(ingredientRepository.findById(anyInt())).thenReturn(Optional.of(updatedIngredient));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(updatedIngredient);

        mockMvc.perform(put("/api/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Salt\",\"price\":\"5.00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(closeTo(5.0, 0.001))));
    }
    @Test
    public void testDeleteIngredient() throws Exception {
        when(ingredientService.deleteIngredient(1)).thenReturn(true);

        mockMvc.perform(delete("/api/ingredients/deleteById/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteIngredientByName() throws Exception {
        when(ingredientService.deleteIngredientByName("Salt")).thenReturn(true);

        mockMvc.perform(delete("/api/ingredients/deleteByName/Salt"))
                .andExpect(status().isOk());
    }
}
