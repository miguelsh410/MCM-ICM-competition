
public class DistanceFormula {
    // Take longitude and latitude points and return distance between those points in kilometers
    public static double distanceFormula(double x_1, double y_1, double x_2, double y_2){
        double rad = 6371;

        // Difference between the x and y coordinates
        double distX = x_2 - x_1;
        double distY = y_2 - y_1;

        // Use distance formula
        double distanceLongLat = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
        double distanceRadians = Math.toRadians(distanceLongLat);

        // Return distance in kilometers
        return rad * distanceRadians;
    }

    public static void main(String[] args) {
        double distance = distanceFormula(-122.6885, 48.981, -123.1052, 47.84);

        System.out.println(distance + " Km");
    }
}
