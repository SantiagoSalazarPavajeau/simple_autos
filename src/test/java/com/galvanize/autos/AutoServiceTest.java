package com.galvanize.autos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AutoServiceTest {

    private AutoService autoService;

    @BeforeEach
    void setUp() {
        autoService = new AutoService();
    }

    @Test
    void getAllCars() {
        AutoList autoList = autoService.getAllCars();
        assertThat(autoList).isNotNull();
    }

    @Test
    void getAllByColor() {
    }

    @Test
    void getAllByMake() {
    }

    @Test
    void getAllByMakeAndColor() {
    }

    @Test
    void addAuto() {
    }

    @Test
    void getAuto() {
    }

    @Test
    void updateAuto() {
    }

    @Test
    void deleteAuto() {
    }
}