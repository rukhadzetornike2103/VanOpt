package org.example.vanopt;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class KnapsackService {

    //Add the "Librarian" (Repository) here so we can save to the database
    private final OptimizationRepository repository;

    //constructor for the repository, Spring autowires this interface so SQL queries can be run without writing SQL queries
    public KnapsackService(OptimizationRepository repository) {
        this.repository = repository;
    }

    //This is the main method the controller calls
    public OptimizationResponse optimize(OptimizationRequest request) {

        //Get the data out of the request
        double maxVol = request.getMaxVolume();
        List<Shipment> available = request.getAvailableShipments();
        int totalItems = available.size();



        //Loop through items and volumes (The core logic)
        //rows are the items columns are the volume
        //int must be used because array indexes cannot be doubles
        int intMaxVol = (int) maxVol;
        double[][] table = new double[totalItems + 1][intMaxVol + 1];
        //this is the core logic
        for(int i = 1; i <= totalItems; i++){
            Shipment currentShipment = available.get(i - 1);
            int itemVol = (int) currentShipment.getVolume();
            double itemRev = currentShipment.getRevenue();

            for(int j = 0; j <= intMaxVol; j++){
                if(itemVol <= j){
                    //this is to decide is it better to take it or leave it
                    //if take-it, current item revenue + the best revenue for the remaining space above
                    int leftoverSpace = j - itemVol;

                    double takeIt = itemRev + table[i-1][leftoverSpace];
                    double leaveIt = table[i-1][j]; //taking the value from the row above


                    if(takeIt > leaveIt){
                        table[i][j] = takeIt;
                    } else {
                        table[i][j] = leaveIt;
                    }
                } else{
                    //this is too big for the current capacity therefore skipping it.
                    table[i][j] = table[i-1][j];
                }
            }
        }

        //Backtrack: Look at the table and see which ones we actually picked
        List<Shipment> chosenOnes = new ArrayList<>();
        double remainingRev = table[totalItems][(int) maxVol];
        int remainingVol = (int) maxVol;

        System.out.println("Starting Algorithm Decision Logic");
        for(int i = totalItems; i > 0; i--){
            if(remainingRev <= 0){
                System.out.println("REvenue has hit 0, breaking loop early");
                break;
            }

            //if the value has changed from the row above then we pick the item, because if it didn't change
            //then the leaveIt got inherited
            if(remainingRev != table[i-1][remainingVol]){
                Shipment picked = available.get(i-1);
                chosenOnes.add(picked);
                System.out.println("Algorithm selected: " + picked.getName() + " for " + picked.getRevenue() + "$");

                //subtracting to find the next item's state
                remainingRev -= picked.getRevenue();
                remainingVol -= (int) picked.getVolume();
            }
        }
        System.out.println("Decision Complete");

        //Creating the response object and fill it up manually
        OptimizationResponse response = new OptimizationResponse();

        double finalVol = 0;
        double finalRev = 0;
        System.out.println("VAN LOADOUT COMPLETE");
        //this is to call an array and output whatever is in the "i"'s appropriate value index
        for(int i = 0; i < chosenOnes.size(); i++){


            Shipment s = chosenOnes.get(i);
            //adding the totals
            finalVol += s.getVolume();
            finalRev += s.getRevenue();
            //this outputs the computer's choice to the console so we can actually see it
            //and uses the toString method
            System.out.println("Loaded: " + s.toString());
        }
        System.out.println("Total van Vol: " + finalVol + " Total Revenue: " + finalRev + "$");

        String generatedId = UUID.randomUUID().toString();//setting up a random ID
        LocalDateTime rightnow = LocalDateTime.now();

        response.setRequestId(generatedId);//assigning the ID
        response.setSelectedShipments(chosenOnes);
        response.setTotalVolume(finalVol);
        response.setTotalRevenue(finalRev);
        response.setCreatedAt(rightnow);

        //Saving the result to PostgreSQL database
        //wrapping it in a try/catch block so that if the DB fails we catch it
        try{
            System.out.println("Connecting to Database...");
            repository.save(response);
            System.out.println("Saved Successfully!");
        } catch(Exception e){
            System.out.println("CRITICAL DB FAILURE !");
            System.out.println("Error Details -> " + e.getMessage());
        }

        return response;
    }
}