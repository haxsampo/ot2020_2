
import Tietorakenteet.Haversine;
import static org.junit.Assert.*;
import org.junit.*;


public class HaversineTest {
    
    Haversine haver;
    
    @Before
    public void setUp() {
        this.haver = new Haversine();
    }
    
    @Test
    public void distanceTest1() {
        double lat1 = 60.1963576;
        double lon1 = 24.8861595;
        
        double lat2 = 60.1980952;
        double lon2 = 24.887184;
        
        double d = this.haver.distance(lat1, lon1, lat2, lon2);
        assertEquals(0.2013, d, 0.001);
    }
    
    @Test
    public void distanceTest2() {
        double lat1 = 60.1877423;
        double lon1 = 24.8313947;
        
        double lat2 = 60.2241938;
        double lon2 = 24.8978953;
        
        double d = this.haver.distance(lat1, lon1, lat2, lon2);
        assertEquals(5.471, d, 0.001);
    }
}
