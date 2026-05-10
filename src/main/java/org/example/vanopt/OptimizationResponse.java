package org.example.vanopt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.time.LocalDateTime;
import java.util.List;

//This @Entity tag tells Java: "Hey, make a table for this in the database"
@Entity
public class OptimizationResponse {

    //This @Id tag marks the unique name for each row (The Primary Key)
    @Id
    private String requestId;

    //This tells the DB that one response can have many shipments
    //CascadeType.ALL means if we save the response, save the shipments too
    @OneToMany(cascade = CascadeType.ALL)
    private List<Shipment> selectedShipments;

    private double totalVolume;
    private double totalRevenue;
    private LocalDateTime createdAt;

    //Standard Constructor - setting everything up at once
    public OptimizationResponse(String requestId, List<Shipment> selectedShipments,
                                double totalVolume, double totalRevenue,
                                LocalDateTime createdAt) {
        this.requestId = requestId;
        this.selectedShipments = selectedShipments;
        this.totalVolume = totalVolume;
        this.totalRevenue = totalRevenue;
        this.createdAt = createdAt;
    }

    //Spring Boot needs this empty constructor to do its thing behind the scenes
    public OptimizationResponse() {
    }

    //Getters
    public String getRequestId() {
        return requestId;
    }

    public List<Shipment> getSelectedShipments() {
        return selectedShipments;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    //Setters
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setSelectedShipments(List<Shipment> selectedShipments) {
        this.selectedShipments = selectedShipments;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}