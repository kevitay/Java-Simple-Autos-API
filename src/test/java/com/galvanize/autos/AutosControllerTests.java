package com.galvanize.autos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class AutosControllerTests {
    @Autowired
    MockMvc autos;

    // GET: /api/autos returns list of all autos in DB
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
