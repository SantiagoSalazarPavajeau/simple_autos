package com.galvanize.autos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AutosController.class) // add controller name
public class AutosControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AutoService autoService;

    List<Auto> autoList;


    @BeforeEach
    void setUp() {
        autoList = new ArrayList<>();
        autoList.add(new Auto("Toyota", "Red"));
        autoList.add(new Auto("Chevy", "Black"));
    }

    @Test
    @DisplayName("Should return all cars when sending GET request")
    void test_getAll() throws Exception {
        when(autoService.getAllCars()).thenReturn(autoList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/autos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}
