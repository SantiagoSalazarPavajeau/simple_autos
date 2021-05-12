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
class AutomobileServiceTest {

    private AutosService autoService;

    @Mock
    AutosRepository autosRepository;

    @BeforeEach
    void setUp() {
        autoService = new AutosService(autosRepository);
    }

    @Test
    void getAutos() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang","ABC1");
        when(autosRepository.findAll()).thenReturn(Arrays.asList(automobile));
        AutosList autoList = autoService.getAutos();
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