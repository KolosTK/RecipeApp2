
import org.example.model.Difficulty;
import org.example.model.Rating;
import org.example.model.Recipe;
import org.example.controller.RecipeController;
import org.example.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }


    @Test
    public void testDeleteRecipeById() throws Exception {
        when(recipeService.deleteRecipe(anyInt())).thenReturn(true);

        mockMvc.perform(delete("/api/recipes/deleteById/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRecipeByName() throws Exception {
        when(recipeService.deleteRecipeByName(anyString())).thenReturn(true);

        mockMvc.perform(delete("/api/recipes/deleteByName/Test Recipe"))
                .andExpect(status().isOk());
    }
}
