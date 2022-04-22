public class HaversineFormula {
    // Code from:
    // https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
    static double haversine(double lat1, double lon1,
                            double lat2, double lon2)
    {
        // distance between latitudes and longitudes
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }
    // Driver Code
    public static void main(String[] args)
    {
        // TEST
        double lat1 = 48.98099;
        double lon1 = -122.6885;
        double lat2 = 48.97195;
        double lon2 = -122.7009;
        System.out.println(haversine(lat1, lon1, lat2, lon2) + " K.M.");
    }
}
