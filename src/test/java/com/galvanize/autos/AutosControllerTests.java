package com.galvanize.autos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(AutosController.class) // add controller name
public class AutosControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AutoService autoService;

    List<Auto> autoList;

    ObjectMapper mapper = new ObjectMapper();

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

    @Test
    @DisplayName("Creates a new car")
    void test_createANewCar_andReturnsCar() throws Exception {
        Auto auto = new Auto("Chevy", "White");
        when(autoService.addAuto(any(Auto.class))).thenReturn(auto);
        mockMvc.perform(post("/api/autos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(auto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("make").value("Chevy"));
    }

    @Test
    @DisplayName("Returns 400 with bad request")
    void test_Returns400OnBadRequest() throws Exception {

        when(autoService.addAuto(any(Auto.class))).thenThrow(InvalidAutoException.class);
        String json = "{\"make\":\"Chevy\",\"color\":\"White\",\"vin\":0}";
        mockMvc.perform(post("/api/autos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return car with vin")
    void test_ReturnsCarWithVin() throws Exception {
        Auto auto = new Auto("BMW", "Silver");
        when(autoService.getAuto(anyInt())).thenReturn(auto);
        mockMvc.perform(get("/api/autos/" + auto.getVin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("vin").value(auto.getVin()));
    }

    @Test
    void test_UpdateCarRequest() throws Exception {
        Auto auto = new Auto("BMW", "Yellow");
        when(autoService.updateAuto(anyInt(), anyString(), anyString())).thenReturn(auto);
        mockMvc.perform(patch("/api/autos/" + auto.getVin())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"make\":\"BMW\", \"color\" : \"Blue\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("color").value("Blue"));

    }

    @Test
    void test_DeleteCarWithVin() throws Exception {
        mockMvc.perform(delete("/api/autos/1"))
                .andExpect(status().isAccepted());

        verify(autoService).deleteAuto(anyInt()); // void method called
    }

    @Test
    void test_Delete_VinNotFound_returnsNoContent() throws Exception {
        doThrow(new AutoNotFoundException()).when(autoService).deleteAuto(anyInt());

        mockMvc.perform(delete("/api/autos/1"))
                .andExpect(status().isNoContent());
    }


}
