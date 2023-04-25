package com.galvanize.autos;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(AutosController.class)
public class AutosControllerTests {
    @Autowired
    MockMvc autos;

    @MockBean
    AutosService autosService;

    ObjectMapper mapper = new ObjectMapper();

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
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.automobiles", hasSize(5)));
    }


    // GET: /api/autos no autos in DB returns 204 no content
    @Test
    public void getAutosNoParamsNoneReturnsNoContent() throws Exception {
        // Arrange
        when(autosService.getAutos()).thenReturn(new AutosList());
        // Act
        autos.perform(get("/api/autos"))
                .andDo(print())
                .andExpect(status().isNoContent());
        // Assert
    }

    // GET /api/autos?color={color}&make={make} returns list of autos matching color and make
        // GET /api/autos?make={make}  returns list of autos matching make
    @Test
    public void getAutosSearchParamsExistsReturnsAutosList() throws Exception {
        List<Automobile> automobiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967 + i, "Ford", "Mustang", "AABB" + i));
        }
        when(autosService.getAutos(anyString(), anyString())).thenReturn(new AutosList(automobiles));
        autos.perform(get("/api/autos?color=red&make=ford"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)));
    }

    // GET /api/autos?color={color} returns list of autos matching color
    @Test
    public void getAutosSearchColorExistsReturnsAutosList() throws Exception {
        List<Automobile> automobiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967 + i, "Ford", "Mustang", "AABB" + i));
            automobiles.get(i).color = "red";
        }
        when(autosService.getAutos(anyString())).thenReturn(new AutosList(automobiles));
        autos.perform(get("/api/autos?color=red"))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)))
                .andExpect(jsonPath("$.automobiles[0].color").value("red"))
                .andExpect(jsonPath("$.automobiles[1].color").value("red"))
                .andExpect(jsonPath("$.automobiles[2].color").value("red"))
                .andExpect(jsonPath("$.automobiles[3].color").value("red"))
                .andExpect(jsonPath("$.automobiles[4].color").value("red"));
    }

    // GET /api/autos?make={make}  returns list of autos matching make
    @Test
    public void getAutosSearchMakeExistsReturnsAutosList() throws Exception {
        List<Automobile> automobiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            automobiles.add(new Automobile(1967 + i, "Ford", "Mustang", "AABB" + i));
            automobiles.get(i).make = "Ford";
        }
        when(autosService.getAutos(anyString())).thenReturn(new AutosList(automobiles));
        autos.perform(get("/api/autos?make=Ford"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.automobiles", hasSize(5)))
                .andExpect(jsonPath("$.automobiles[0].make").value("Ford"))
                .andExpect(jsonPath("$.automobiles[1].make").value("Ford"))
                .andExpect(jsonPath("$.automobiles[2].make").value("Ford"))
                .andExpect(jsonPath("$.automobiles[3].make").value("Ford"))
                .andExpect(jsonPath("$.automobiles[4].make").value("Ford"));
    }

    // POST /api/autos adds auto that has information in request body returns 200 for successful
        //    {
        //        "year": 1967,
        //            "make": "Ford",
        //            "model": "Mustang",
        //            "color": "RED",
        //            "owner": "John Doe",
        //            "vin": "7F03Z01025"
        //    }


    @Test
    public void addAutoValidReturnsAuto() throws Exception {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosService.addAuto(any(Automobile.class))).thenReturn(automobile);
        //String json = "{\"year\": 1967, \"make\": \"Ford\", \"model\": \"Mustang\", \"color\": null, \"vin\": \"AABBCC\"}";
        autos.perform(post("/api/autos").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(automobile)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("make").value("Ford"));
    }

    // POST /api/autos adds auto that has information in request body returns 400 for bad request
    @Test
    public void addAutoBadRequestReturns400() throws Exception {
        when(autosService.addAuto(any(Automobile.class))).thenThrow(InvalidAutoException.class);
        String json = "{\"year\": 1967, \"make\": \"Ford\", \"model\": \"Mustang\", \"color\": null, \"vin\": \"AABBCC\"}";
        autos.perform(post("/api/autos").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    // GET /api/autos/{vin} returns auto with VIN matching VIN passed in request path; returns 200 OK or 204 vehicle not found
    @Test
    public void getAutoByVinReturnsAuto() throws Exception {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosService.getAuto(anyString())).thenReturn(automobile);
        autos.perform(get("/api/autos" + automobile.getVin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("vin").value(automobile.getVin()));
    }

    // PATCH /api/autos/{vin} (can update owner or color of vehicle matching VIN in Request Path variable) returns 200 auto updated successfully, 204 vehicle not found or 400 bad request
    @Test
    public void updateAutoWithObjectReturnsAuto() throws Exception {
        Automobile automobile = new Automobile(1967, "Ford", "Mustang", "AABBCC");
        when(autosService.updateAuto(anyString(), anyString(), anyString())).thenReturn(automobile);
        autos.perform(patch("/api/autos/" + automobile.getVin())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"color\":\"red\", \"owner\":\"Bob\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("color").value("red"))
            .andExpect(jsonPath("owner").value("Bob"));
    }

    // DELETE //api/autos/{vin} delete auto by VIN number in Request Path variable returns 200 auto delete request accepted or 204 vehicle not found
    @Test
    public void deleteAutoWithVinExistsReturns202() throws Exception {
        autos.perform(delete("/api/autos/AABBCC"))
                .andExpect(status().isAccepted());
        verify(autosService).deleteAuto(anyString());
    }

    @Test
    public void deleteAutoWithVinNotExistsReturnNoContent() throws Exception {
        doThrow(new AutoNotFoundException()).when(autosService).deleteAuto(anyString());
        autos.perform(delete("/api/autos/AABBCC"))

    }

}
