package org.example.vanopt;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class KnapsackServiceUnitTests {

    // We create the service manually for the test
    private final KnapsackService knapsackService = new KnapsackService(null);

    @Test
    void shouldPickBestParcelsForMaximumRevenue() {
        // SETUP: Exact values from the homework requirements [cite: 25, 26, 48]
        Shipment a = new Shipment("Parcel A", 5.0, 120.0);
        Shipment b = new Shipment("Parcel B", 10.0, 200.0);
        Shipment c = new Shipment("Parcel C", 3.0, 80.0);
        Shipment d = new Shipment("Parcel D", 8.0, 160.0);

        List<Shipment> available = Arrays.asList(a, b, c, d);
        OptimizationRequest request = new OptimizationRequest(15.0, available);

        // EXECUTE
        OptimizationResponse response = knapsackService.optimize(request);

        // ASSERT: Total Revenue should be 320 (A + B) [cite: 67, 155]
        assertEquals(320.0, response.getTotalRevenue(), "Revenue should be 320.0");
        assertEquals(15.0, response.getTotalVolume(), "Volume should be 15.0");
        assertEquals(2, response.getSelectedShipments().size());
    }

    @Test
    void shouldReturnEmptyIfNothingFits() {
        // Requirement 1: If nothing fits, return empty list and 0 revenue [cite: 70, 158]
        Shipment hugeBox = new Shipment("Huge Box", 100.0, 500.0);
        OptimizationRequest request = new OptimizationRequest(10.0, Arrays.asList(hugeBox));

        OptimizationResponse response = knapsackService.optimize(request);

        assertEquals(0.0, response.getTotalRevenue());
        assertTrue(response.getSelectedShipments().isEmpty());
    }
}