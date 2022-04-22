// Miguel Sanchez 07-Feb-2021

import java.io.FileNotFoundException;

public class MyClass {
    public static void main(String[] args) throws FileNotFoundException {
        // LOCATIONS ARE CHANGED!! ---------------------------
        String posLat = "/Users/miguelsanchez/Positive latitude";
        String posLong = "/Users/miguelsanchez/Positive longitude";
        String unvLat = "/Users/miguelsanchez/Unverified latitude";
        String unvLong = "/Users/miguelsanchez/Unverified longitude";

        Coordinate[] unverified = Coordinate.putCoordinatesInArray(unvLat, unvLong, true);
        Coordinate[] positive = Coordinate.putCoordinatesInArray(posLat, posLong, false);

        Coordinate.rankCoordinates(unverified, positive);

        //System.out.println(unverified.length);

        for (Coordinate coordinate : unverified){
            //System.out.println(coordinate);
            //coordinate.printInformation();
            coordinate.printCloseCoordinates();
        }
    }
}
