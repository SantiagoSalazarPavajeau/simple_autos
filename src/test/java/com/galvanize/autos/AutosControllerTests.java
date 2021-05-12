package com.galvanize.autos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AutosController.class)
public class AutosControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AutosService autosService;

    ObjectMapper mapper = new ObjectMapper();
    // GET: /api/autos
    // GET:  /api/autos   returns a lit of all autos
    @Test
    void getAuto_noParms_exists_returnsAutosLists() throws Exception {
        // Arrange
        List<Automobile> automobiles = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967+i, "Ford", "Mustang", "AABB"+i));
        }
        when(autosService.getAutos()).thenReturn(new AutosList(automobiles));
        // Act
        mockMvc.perform(get("/api/autos"))
                .andDo(print())
                // Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }

    @Test
    void getAutos_noParms_non_returnsNoContent() throws Exception {
        when(autosService.getAutos()).thenReturn(new AutosList());
        mockMvc.perform(get("/api/autos"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void getAutos_searchParms_exists_ReturnsAutosList() throws Exception {
        List<Automobile> automobiles = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967+i, "Ford", "Mustang", "AABB"+i));
        }
        when(autosService.getAutos(anyString(), anyString())).thenReturn(new AutosList(automobiles));
        mockMvc.perform(get("/api/autos?color=RED&make=Form"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }

    @Test
    void addAuto_valid_returnsAuto() throws Exception {
        Automobile auto = new Automobile(1967, "ford", "mustang", "ABC123");
      //  String json = "{\"year\":1967,\"make\":\"Ford\",\"model\":\"Mustang\",\"color\":null,\"owner\":null,\"vin\":\"AABBCC\"}";
        when(autosService.addAuto(any(Automobile.class))).thenReturn(auto);

        mockMvc.perform(post("/api/autos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(auto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("make").value("ford"));
    }

    @Test
    void addAuto_badRequest_returns400() throws Exception {
        String json = "{\"year\":1967,\"make\":\"Ford\",\"model\":\"Mustang\",\"color\":null,\"owner\":null,\"vin\":\"AABBCC\"}";
        when(autosService.addAuto(any(Automobile.class))).thenThrow(InvalidAutoException.class);

        mockMvc.perform(post("/api/autos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAuto_withVin_returnsAuto() throws Exception {
        Automobile auto = new Automobile(1967, "ford", "mustang", "AABB7");
        when(autosService.getAuto(anyString())).thenReturn(auto);
        mockMvc.perform(get("/api/autos/"+auto.getVin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("vin").value(auto.getVin()));
    }

    @Test
    void updateAuto_withObject_returnsAuto() throws Exception {
        Automobile auto = new Automobile(1967, "Ford", "mustang", "AABB5");
        when(autosService.updateAuto(anyString(), anyString(), anyString())).thenReturn(auto);
        mockMvc.perform(patch("/api/autos/"+auto.getVin())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"color\":\"RED\",\"owner\":\"Carina\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("color").value("RED"))
                .andExpect(jsonPath("owner").value("Carina"));
    }

    @Test
    void deleteAuto_withVin_exists_returns202() throws Exception {

        mockMvc.perform(delete("/api/autos/AABBCC"))
                .andExpect(status().isAccepted());
        verify(autosService).deleteAuto(anyString());
    }

    @Test
    void deleteAuto_withVin_notExists_returnsNoContent() throws Exception {
        doThrow(new AutoNotFoundException()).when(autosService).deleteAuto(anyString());
        mockMvc.perform(delete("/api/autos/AABBCC"))
                .andExpect(status().isNoContent());
    }


}

