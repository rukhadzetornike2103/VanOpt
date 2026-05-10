package org.example.vanopt;

import java.util.List;

public class OptimizationRequest {
    //These are the two things the user sends us
    private double maxVolume;
    private List<Shipment> availableShipments;

    //
    public OptimizationRequest(double maxVolume, List<Shipment> availableShipments) {
        this.maxVolume = maxVolume;
        this.availableShipments = availableShipments;
    }

    //Default constructor so Spring Boot can create the object from JSON
    public OptimizationRequest() {
    }

    //These let the KnapsackService "read" the input
    public double getMaxVolume() {
        return maxVolume;
    }

    public List<Shipment> getAvailableShipments() {
        return availableShipments;
    }

    //These let Spring Boot "write" the JSON data into this object
    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
    }

    public void setAvailableShipments(List<Shipment> availableShipments) {
        this.availableShipments = availableShipments;
    }
}