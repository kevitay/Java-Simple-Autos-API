package com.galvanize.autos;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(AutosController.class)
public class AutosControllerTests {
    @Autowired
    MockMvc autos;

    @MockBean
    AutosService autosService;

    // GET: /api/autos returns list of all autos in DB
    @Test
    public void getAutosNoParamsExistsReturnsAutosList() throws Exception {
        // Arrange
        List<Automobile> automobiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967 + i, "Ford", "Mustang", "AABB" + i));
        }
        when(autosService.getAutos()).thenReturn(new AutosList(automobiles));
        // Act
        autos.perform(get("/api/autos"))
                .andDo(print())
                // Assert
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.automobiles", hasSize(5)));
    }


    // GET: /api/autos no autos in DB returns 204 no content

    // GET /api/autos?color={color}&make={make} returns list of autos matching color and make
    // GET /api/autos?color={color} returns list of autos matching color
    // GET /api/autos?make={make}  returns list of autos matching make

    // POST /api/autos adds auto that has information in request body returns 200 for successful
        //    {
        //        "year": 1967,
        //            "make": "Ford",
        //            "model": "Mustang",
        //            "color": "RED",
        //            "owner": "John Doe",
        //            "vin": "7F03Z01025"
        //    }

    // POST /api/autos adds auto that has information in request body returns 400 for bad request

    // GET /api/autos/{vin} returns auto with VIN matching VIN passed in request path; returns 200 OK or 204 vehicle not found

    // PATCH /api/autos/{vin} (can update owner or color of vehicle matching VIN in Request Path variable) returns 200 auto updated successfully, 204 vehicle not found or 400 bad request

    // DELETE //api/autos/{vin} delete auto by VIN number in Request Path variable returns 200 auto delete request accepted or 204 vehicle not found

}
