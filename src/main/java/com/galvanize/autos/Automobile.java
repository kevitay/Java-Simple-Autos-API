package com.galvanize.autos;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Objects;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "automobiles")
public class Automobile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "model_year")
    private int year;
    private String make;
    private String model;
    private String color;
    @Column(name = "owner_name")
    private String owner;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date purchaseDate;
    private String vin;

    public Automobile() {
    }

    public Automobile(int year, String make, String model, String vin) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.vin = vin;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public String toString() {
        return "Automobile{" +
                "year=" + year +
                ", make=" + make + + '\'' +
                ", model=" + model + + '\'' +
                ", color=" + color + + '\'' +
                ", owner=" + owner + + '\'' +
                ", vin=" + vin + + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Automobile that = (Automobile) o;
        return year == that.year && Objects.equals(make, that.make) && Objects.equals(model, that.model) && Objects.equals(vin, that.vin);
    }
}
