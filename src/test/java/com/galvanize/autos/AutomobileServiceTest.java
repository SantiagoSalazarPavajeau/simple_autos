package com.galvanize.autos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    void getAutos_search_returnsList() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang","ABC1");
        automobile.setColor("RED");
        when(autosRepository.findByColorContainsAndMakeContains(anyString(), anyString()))
                .thenReturn(Arrays.asList(automobile));
        AutosList autoList = autoService.getAutos("RED", "Ford");
        assertThat(autoList).isNotNull();
        assertThat(autoList.isEmpty()).isFalse();
    }

    @Test
    void addAuto_valid_returnsAuto() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang","ABC1");
        automobile.setColor("RED");
        when(autosRepository.save(any(Automobile.class)))
                .thenReturn(automobile);
        Automobile auto = autoService.addAuto(automobile);
        assertThat(auto).isNotNull();
        assertThat(auto.getMake()).isEqualTo("Ford");
    }

    @Test
    void getAuto() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang","ABC1");
        automobile.setColor("RED");
        when(autosRepository.findByVin(anyString()))
                .thenReturn(java.util.Optional.of(automobile));
        Automobile auto = autoService.getAuto(automobile.getVin());
        assertThat(auto).isNotNull();
        assertThat(auto.getVin()).isEqualTo(automobile.getVin());
    }

    @Test
    void updateAuto() {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang","ABC1");
        automobile.setColor("RED");
        when(autosRepository.findByVin(anyString()))
                .thenReturn(java.util.Optional.of(automobile));
        when(autosRepository.save(any(Automobile.class)))
                .thenReturn(automobile);
        Automobile auto = autoService.updateAuto(automobile.getVin(), "PURPLE", "ANYONE");
        assertThat(auto).isNotNull();
        assertThat(auto.getVin()).isEqualTo(automobile.getVin());
    }

    @Test
    void deleteAuto() {
    }
}