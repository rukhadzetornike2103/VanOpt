
VanOpt a Cargo loading Optimizer App
this is a program built to solve the knapSack problem for delivery services.
The program takes a lsit of shipments, then it figures out the combination on how to sort them in the most efficient way,
prioritizing revenue without compromising the van's volume or weight. afterwards it logs the decision in PostgreSQL database.
The program is built using Java combined with Spring Boot


The core of this API (Application Programing Interface) is not to just sort a list.
The algorithm mentioned in the PDF (mentioned in Zero Junior Assigment.pdf) is more of a dynamic programming concept.
0/1 Knapsack Problem. The idea is pretty simple, either load the shipment or not, DO NOT SPLIT BOXES.
However getting this to work requires/required building a matrix to calculate every single combination possible in respect of
weight and revenue before making a final decision. 
To figure out the best loadout the algorithm builds a grid using a 2D Array (double[][]) and the rows represent shipments available
the columns represent every volume size possible starting from size 0 up to the van's capacity (maxVolume)
then the indexes must be cast as int-s since a decimal as an index for an array will cause the program to break.
The core loop system runs a nested "for" loop which iterates through every box and asks one question -> Should I take this box or leave it?
if taking the box makes more money than leaving it then it overwrites that slot inside the grid with higher revenue.

one issue is that the matrix holds the highest possible revenue, so in simpler terms, it holds money, but not really the names of the shipments.
A reverse loop was necessary to find out what the matrix saved, it starts at the bottom of the matrix and basically walks backwards through the matrix.
If the revenue number is different from the row on top of it, it means that the algorithm decided to load that very specific box,
but if the number stays the same, that means that that one got skipped.

I also tried to follow the process of the program working by adding some System.out.println statements to see what's going on.
while making it more comfortable for me to write since I am not yet very familiar with Java.


!!!How To Use!!!
To run the program a Java Development kit is required, in my case JDK 17 but higher will work fine.
The Docker program, Docker Desktop, this is required for the database
Either a Maven or Gradle wrapper for the program
To run the program navigate to the src folder, for more detail -> "VanOpt\src\main\java\org\example\vanopt\VanOptApplication.java"
