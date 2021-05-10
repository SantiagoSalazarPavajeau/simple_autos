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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        autoList.add(new Auto("Honda", "Red"));
        autoList.add(new Auto("Chevy", "White"));
    }

    @Test
    @DisplayName("Should return all cars when sending GET request")
    void test_getAll() throws Exception {
        when(autoService.getAllCars()).thenReturn(autoList);
        mockMvc.perform(get("/api/autos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    @DisplayName("Should return all cars by color")
    void test_getAllByColor() throws Exception {
        List<Auto> listByColor = new ArrayList<>();
        listByColor.add(new Auto("Honda", "Red"));
        listByColor.add(new Auto("Toyota", "Red"));

        when(autoService.getAllByColor("Red")).thenReturn(listByColor);

        mockMvc.perform(get("/api/autos?color=Red"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Should return all cars by make")
    void test_getAllByMake() throws Exception {
        List<Auto> listByMake = new ArrayList<>();
        listByMake.add(new Auto("Chevy", "Black"));
        listByMake.add(new Auto("Chevy", "White"));

        when(autoService.getAllByMake("Chevy")).thenReturn(listByMake);

        mockMvc.perform(get("/api/autos?make=Chevy"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Should return all cars by make and color")
    void test_getAllByMakeAndColor() throws Exception {
        List<Auto> listByMakeAndColor = new ArrayList<>();
        listByMakeAndColor.add(new Auto("Chevy", "White"));

        when(autoService.getAllByMakeAndColor("White","Chevy")).thenReturn(listByMakeAndColor);

        mockMvc.perform(get("/api/autos?color=White&make=Chevy"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName( "Should return 204 no content if no cars found")
    void test_SearchForCarsReturn204OnNoneFound() throws Exception {
        List<Auto> emptyList = new ArrayList<>();

        when(autoService.getAllCars()).thenReturn(emptyList);

        mockMvc.perform(get("/api/autos"))
                .andExpect(status().isNoContent());
    }




}
