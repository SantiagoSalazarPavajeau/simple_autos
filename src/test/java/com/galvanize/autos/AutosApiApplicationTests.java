package com.galvanize.autos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AutosApiApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AutosRepository autosRepository;

    Random r = new Random();
    List<Automobile> testAutos;

    @BeforeEach
    void setUp() {
        this.testAutos = new ArrayList<>();
        Automobile auto;
        String[] colors = {"ROOT BEER", "MAGENTA", "AMBER", "ORANGE", "YELLOW"};

        for(int i = 0; i < 50; i++){
            if(i%3 == 0){
                auto = new Automobile(1997, "BMW", "M6", "AABBCC"+(i*13));
                auto.setColor(colors[r.nextInt(5)]);
            }else if( i % 2 == 0 ){
                auto = new Automobile(1997, "Audi", "TT", "VVBBXX"+(i*12));
                auto.setColor(colors[r.nextInt(5)]);
            }else{
                auto = new Automobile(2020, "Bentley", "Continental", "QQZZAA"+(i*12));
            }
            this.testAutos.add(auto);
        }
        autosRepository.saveAll(this.testAutos);
    }

    @AfterEach
    void tearDown() {
        autosRepository.deleteAll();
    }

    @Test
    void getAutos_exists_returnsAutosList(){
        //make call to restTemplate through random port
        ResponseEntity<AutosList> response = restTemplate.getForEntity("/api/autos", AutosList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isEmpty()).isFalse();
        for(Automobile auto: response.getBody().getAutomobiles()){
            System.out.println(auto);
        }
    }

    @Test
	void contextLoads() {
	}

}
