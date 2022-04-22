// Miguel Sanchez 07-Feb-2021

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Coordinate implements Comparable<Coordinate> {
    private final double longitude;
    private final double latitude;
    private int positiveCounter = 0;
    private double closestPositiveDistance = 999999.0; // (Needed a very big number (in km) to initialize it.)
    private Coordinate closestPositive;
    private final boolean isUnverified;
    private final ArrayList<Coordinate> closeCoordinates = new ArrayList<>();

    public Coordinate(double latitude, double longitude, boolean isUnverified) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.isUnverified = isUnverified;
    }

    // The distance is calculated using the Haversine Formula. The value returned from this function is in kilometers
    // The code for this Haversine Formula was taken from:
    // https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
    public double distance(Coordinate c2){
        return HaversineFormula.haversine(this.latitude, this.longitude, c2.latitude, c2.longitude);
    }

    // This will check if the coordinate is in a 30km radius of a positive and increase the positive counter if it is
    // close. It will also update which is the closest positive coordinate and its corresponding closest distance.
    public void closeTo(Coordinate c2){
        double distanceFromC2 = this.distance(c2);

        if(distanceFromC2 < 30) {
            this.positiveCounter++;
            this.closeCoordinates.add(c2);

            if (distanceFromC2 < this.closestPositiveDistance) {
                this.closestPositiveDistance = distanceFromC2;
                this.closestPositive = c2;
            }
        }
    }

    // Rank unverified coordinates based on how many positive cases are close and which is the closest positive case.
    public static void rankCoordinates( Coordinate[] unverified, Coordinate[] positive){
        for (Coordinate unvCoordinate : unverified){
            for (Coordinate posCoordinate : positive){
                unvCoordinate.closeTo(posCoordinate);
            }
        }

        Arrays.sort(unverified);
    }

    // Takes csv files with coordinates and puts the coordinates in an array
    public static Coordinate[] putCoordinatesInArray(String latPath, String longPath, boolean isUnverified)
            throws FileNotFoundException {
        Scanner latScanner = new Scanner(new File(latPath));
        Scanner longScanner = new Scanner(new File(longPath));

        // Even though the files are csv, the delimiter might be something else.
        latScanner.useDelimiter("\n");
        longScanner.useDelimiter("\n");

        ArrayList<Coordinate> coordinatesList = new ArrayList<>();

        while (latScanner.hasNext() && longScanner.hasNext()){
            coordinatesList.add(new Coordinate(Double.parseDouble(latScanner.next()),
                    Double.parseDouble(longScanner.next()), isUnverified));
        }

        latScanner.close();
        longScanner.close();

        Object[] coordinatesOArray = coordinatesList.toArray();
        Coordinate[] coordinatesArray = new Coordinate[coordinatesOArray.length];

        for (int i = 0; i < coordinatesArray.length; i++) {
            coordinatesArray[i] = (Coordinate) coordinatesOArray[i];
        }

        return coordinatesArray;
    }

    // Prints how many positive reports near the unverified case, which is the closest positive report, and what is the
    // distance between the positive report and the unverified report
    public void printInformation(){
        String toReturn = "(" + this.latitude + ", " + this.longitude + ")";

        if (isUnverified) {
            toReturn = toReturn + " - Positive reports near: " + this.positiveCounter + ".";
        }

        if (positiveCounter > 0) {
            toReturn = toReturn + " Closest: " + this.closestPositive + ", at " + this.closestPositiveDistance + " km.";
        }

        System.out.println(toReturn);
    }

    // Print all the coordinates that are close to this point
    public void printCloseCoordinates(){
        if (positiveCounter > 0){
            int i = 0;

            System.out.println(this.toString() + " is close to:");

            // This while loop could be a for loop
            while(i < this.closeCoordinates.size()){
                System.out.println("\t" + this.closeCoordinates.get(i) + " -> Distance: " +
                        this.distance(this.closeCoordinates.get(i)) + " km.");

                i++;
            }

            System.out.println("");
        }
        else{
            System.out.println("There are no positive reports close to " + this.toString());
        }
    }

    public String toString(){
        return  "(" + this.latitude + ", " + this.longitude + ")";
    }

    @Override
    public int compareTo(Coordinate c2) {
        int difference = this.positiveCounter - c2.positiveCounter;

        if (difference == 0){
            double distance = this.closestPositiveDistance - c2.closestPositiveDistance;

            if (distance > 0) difference = -1;
            else difference = 1;
        }
        return difference;
    }
}
