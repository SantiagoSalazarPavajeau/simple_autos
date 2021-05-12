package com.galvanize.autos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutoServiceTest {

    private AutoService autoService;

    @Mock
    AutosRepository autosRepository;

    @BeforeEach
    void setUp() {
        autoService = new AutoService(autosRepository);
    }

    @Test
    void getAutos() {
        Auto auto = new Auto(1967, "Ford", "Mustang", "ABC");
        when(autosRepository.findAll()).thenReturn(Arrays.asList(auto));
        AutoList autoList = autoService.getAutos();
        assertThat(autoList).isNotNull();
        assertThat(autoList.isEmpty()).isFalse();
    }

    @Test
    void testGetAutos() {

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