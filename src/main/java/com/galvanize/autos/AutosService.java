package com.galvanize.autos;

import org.springframework.stereotype.Service;

@Service
public class AutosService {

    AutosRepository autosRepository;

    public AutosService(AutosRepository autosRepository) {
        this.autosRepository = autosRepository;
    }

    public AutosService() {
        // default constructor
    }

    public AutosList getAutos() {
        // Query: select * from autos;
        // Put that in a list
        // Return a new AutosList with the list
        return new AutosList(autosRepository.findAll());
    }

    public AutosList getAutos(String color, String make) {
        return null;
    }

    public AutosList getAutos(String searchQuery) {
        return null;
    }

    public Automobile addAuto(Automobile auto) {
        return null;
    }

    public Automobile getAuto(String vin) {
        return null;
    }

    public Automobile updateAuto(String vin, String color, String owner) {
        return null;
    }

    public Automobile updateAuto(String searchQuery) {
        return null;
    }

    public void deleteAuto(String vin) {

    }
}
