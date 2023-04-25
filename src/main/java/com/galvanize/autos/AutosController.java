package com.galvanize.autos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutosController {
    @GetMapping("/api/autos")
    public AutosList getAutos(){
        // AutosList = Service.getAutos();
        return null;
    }
}
