package com.galvanize.autos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AutosServiceTest {

    private AutosService autosService;

    @BeforeEach
    void setUp() {
        autosService = new AutosService();
    }

    @Test
    void getAutos() {
        AutosList autoList = autosService.getAutos();
        assertThat(autoList).isNotNull();
    }

    @Test
    void testGetAutos() {
    }

    @Test
    void testGetAutos1() {
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
    void testUpdateAuto() {
    }

    @Test
    void deleteAuto() {
    }
}