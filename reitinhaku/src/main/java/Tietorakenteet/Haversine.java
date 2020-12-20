package Tietorakenteet;

public class Haversine {
    
    private int R = 6371;
    
    public Haversine() {
        
    }
    
    public double nodeDist(Node eka, Node toka) {
        Double ret = distance(eka.getPos().getLatitude(), eka.getPos().getLongitude(),
                toka.getPos().getLatitude(), toka.getPos().getLongitude());
        return ret;
    }
    
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        Double latDist = toRadian(lat2-lat1);
        Double lonDist = toRadian(lon2-lon1);
        Double a = Math.sin(latDist / 2) * Math.sin(latDist / 2)+
                Math.cos(toRadian(lat1)) * Math.cos(toRadian(lat2)) * 
                Math.sin(lonDist / 2) * Math.sin(lonDist / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R*c;
        return distance;
    }
    
    private static Double toRadian(Double value) {
        return value * Math.PI / 180;
    }
}
