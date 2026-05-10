package org.example.vanopt;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//This tells the database: "Create a table for these items"
@Entity
public class Shipment {

    //The database needs a unique ID for every single row
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double volume;
    private double revenue;

    //setting up the shipment data
    public Shipment(String name, double volume, double revenue) {
        this.name = name;
        this.volume = volume;
        this.revenue = revenue;
    }

    //Default constructor so the database and Spring don't complain
    public Shipment() {
    }

    //Letting other classes "read" the data
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }

    public double getRevenue() {
        return revenue;
    }

    //Let the database "write" the data into the object
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
    @Override
    public String toString(){
        return "Shipment: [" + this.name + "] Vol: [" + this.volume + "] Revenue: [$" + this.revenue + "]";
    }
}