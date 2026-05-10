package org.example.vanopt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadingController {

    //This is the "Brain" we are going to use
    private final KnapsackService knapsackService;

    //spring wires knapsackService no need for "new"
    public LoadingController(KnapsackService knapsackService) {
        this.knapsackService = knapsackService;
    }

    //This method waits for someone to send a POST request to "/optimize"
    @PostMapping("/optimize")//post mapping tells the server to route any POST HTTP requests to this specific method
    //changed the return type to ResponseEntity<Object> so we can return EITHER an error string OR the good JSON
    public ResponseEntity<Object> handleOptimization(@RequestBody OptimizationRequest request) {

        //the van must have some space inside it
        if(request.getMaxVolume() <= 0){
            return ResponseEntity.badRequest().body("Error: maxVolume must be greater than zero");
        }

        //checking if the list exists at all, since if it doesn't, we will get a NullPointerException
        if(request.getAvailableShipments() == null){
            return ResponseEntity.badRequest().body("Error: avalable shipment list is missing");
        } else if(request.getAvailableShipments().size() == 0){
            //Using .size() ==0 to make sure it isn't just an empty bracket
            return ResponseEntity.badRequest().body("Error: you need at least one shipment!");
        }




        boolean isDataGood = true;//to keep track of wheather data is good or not
        String errorMessage = "";
        for(int i = 0; i < request.getAvailableShipments().size(); i++){
            Shipment currentShipment = request.getAvailableShipments().get(i);
            //if the volume is less than 0, we can't fix it
            if(currentShipment.getVolume() <= 0){
                isDataGood = false;
                errorMessage = "Error: SHipment " + currentShipment.getName() + " has invalid volume";
                break;//stopping the loop if we got bad data
            }

            if(currentShipment.getRevenue() < 0){
                isDataGood = false;
                errorMessage = "Error: no negative money is allowed for " + currentShipment.getName();
            }
        }
        if(!isDataGood){//if isDataGood == false
            System.out.println("Validation failed: " + errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }






        OptimizationResponse finalResult = knapsackService.optimize(request);

        //Sending the result back to the user with a 200 OK success signal
        return ResponseEntity.ok(finalResult);
    }
}
/*
    //OLD ATTEMPT - KEPT FOR NOTES


    @PostMapping("/test-load")
    public String oldHandleOptimization(@RequestBody Shipment[] request) {
        System.out.println("Testing the endpoint");
        //Shipment[] tuningFleet = new Shipment[3];
        //for(int i=0; i<request.length; i++){
        //    tuningFleet[i] = request[i];
        //}
        //System.out.println(tuningFleet[0].getName());
        return "this was a test";
    }
*/