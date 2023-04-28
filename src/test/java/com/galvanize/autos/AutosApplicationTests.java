package com.galvanize.autos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AutosApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AutosRepository autosRepository;

    Random r = new Random();
    List<Automobile> testAutos;

    @BeforeEach
    void setUp() {
        this.testAutos = new ArrayList<>();
        String[] colors = {"RED", "BLUE", "GREEN", "PURPLE", "YELLOW", "BLACK", "CUB BLUE", "FOREST GREEN"};
        String[] makes = {"Honda", "Toyota", "Ford", "Chevy", "Dodge" };
        String[] owners = {
                "Jacob", "Bob", "Salih", "Chad", "Kevin",
                "John", "Phil", "Kyle", "Matthew", "Trenton",
                "Erick", "Greg", "Austin", "T", "Russhi",
                "Michelle", "Cody", "Keerthana"
        };
        HashMap<String, String[]> models = new HashMap<>();
        models.put(makes[0], new String[] {"Civic", "Accord", "NSX", "CR-V", "Odyssey"});
        models.put(makes[1], new String[] {"Camry", "Corolla", "Sienna", "Supra", "Tacoma"});
        models.put(makes[2], new String[] {"Mustang", "F-150", "Bronco", "GT", "Taurus"});
        models.put(makes[3], new String[] {"Suburban", "Tahoe", "Silverado", "Chevelle"});
        models.put(makes[4], new String[] {"Viper", "RAM", "Neon", "Charger", "Durango"});
        HashMap<String, String> vinPrefix = new HashMap<>();
        vinPrefix.put(makes[0], "HOND-01");
        vinPrefix.put(makes[1], "TOYO-02");
        vinPrefix.put(makes[2], "FORD-03");
        vinPrefix.put(makes[3], "CHVY-04");
        vinPrefix.put(makes[4], "DOGE-05");

        for (int i = 0; i < 50; i++) {
            int nextYear = r.nextInt(40) + 1970;
            int nextMakeIndex = r.nextInt(makes.length);
            String nextMake = makes[nextMakeIndex];
            String[] nextModels = models.get(nextMake);
            String nextModel = nextModels[r.nextInt(nextModels.length)];
            String nextVin = String.format("%s%5s", vinPrefix.get(nextMake), i).replaceAll(" ", "0");
            Automobile auto = new Automobile(nextYear, nextMake, nextModel, nextVin);
            auto.setColor(colors[r.nextInt(colors.length)]);
            auto.setOwner(owners[i % owners.length]);
            this.testAutos.add(auto);
        }
        autosRepository.saveAll(this.testAutos);
    }

    @AfterEach
    void tearDown() {
        autosRepository.deleteAll();
    }

    @Test
	    void contextLoads() {
	}

    @Test
    void getAutosExistsReturnsAutosList() {
        ResponseEntity<AutosList> response = restTemplate.getForEntity("/api/autos", AutosList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isEmpty()).isFalse();
        for (Automobile auto : response.getBody().getAutomobiles()) {
            System.out.println(auto);
        }
    }

    // void getAutosNotExistsReturnsAutosNotFound

    @Test
    void getAutosNotExistsReturnsAutosNoContent() {
        tearDown();
        ResponseEntity<AutosList> response = restTemplate.getForEntity("/api/autos", AutosList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
        System.out.println(autosRepository);
    }

    @Test
    void getAutosSearchReturnsAutosList() {
        int seq = r.nextInt(50);
        String color = testAutos.get(seq).getColor();
        String make = testAutos.get(seq).getMake();
        ResponseEntity<AutosList> response = restTemplate.getForEntity(
                String.format("/api/autos?color=%s&make=%s", color, make), AutosList.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().isEmpty()).isFalse();
        assertThat(response.getBody().getAutomobiles().size()).isGreaterThanOrEqualTo(1);
        for(Automobile auto: response.getBody().getAutomobiles()) {
            System.out.println(auto);
        }
    }

    // void getAutosByMakeReturnsAutosList


    // void getAutosByColorReturnsAutosList


    // void getAutoByVinReturnsAuto


    // void getAutoByVinInvalidReturnsAutoNotFound


    @Test
    void addAutoReturnsNewAutoDetails() {
        Automobile automobile = new Automobile();
        automobile.setVin("ABC123XX");
        automobile.setYear(1995);
        automobile.setMake("Ford");
        automobile.setModel("Windstar");
        automobile.setColor("Blue");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Automobile> request = new HttpEntity<>(automobile, headers);

        ResponseEntity<Automobile> response = restTemplate.postForEntity("/api/autos", request, Automobile.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getVin()).isEqualTo(automobile.getVin());
    }

    // void addAutoInvalidReturnsBadRequest


    // void updateAutoColorByVinReturnsAutomobile


    // void updateAutoOwnerByVinReturnsAutomobile


    // void deleteAutoByVinReturnsAccepted


    // void delteAutoByVinReturnsAutoNotFound

}
