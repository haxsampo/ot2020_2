import Tietorakenteet.Node;
import Tietorakenteet.PrioJono;
import Tietorakenteet.PrioNode;
import static org.junit.Assert.*;
import org.junit.*;
import org.jxmapviewer.viewer.GeoPosition;
public class PrioJonoTest {
    
    PrioJono pr;
    
    
    @Before
    public void setUp() {
        this.pr = new PrioJono();
        GeoPosition gp = new GeoPosition(1,2);
        GeoPosition gp3 = new GeoPosition(4,2);
        Node nod = new Node(gp);
        
        Node nod2 = new Node(gp3);
        PrioNode kolme = new PrioNode(nod,3.0);
        
        PrioNode kaksi = new PrioNode(nod2,2.0);
        pr.lisaaJonoon(kolme);
        
        pr.lisaaJonoon(kaksi);
    }
    
    @Test
    public void addJonoonTest() {
        GeoPosition gp2 = new GeoPosition(3,2);
        Node nod1 = new Node(gp2);
        PrioNode yksi = new PrioNode(nod1,1.0);
        pr.lisaaJonoon(yksi);
        Double ensimmainenPrioArvo = pr.getJono().get(0).getPrio();
        assertEquals(1.0, ensimmainenPrioArvo, 0.001);
        assertEquals(3,pr.getJono().size());
    }
    
    @Test
    public void popTest() {
        PrioNode popped = pr.pop();
        assertEquals(2.0, popped.getPrio(), 0.001);
        PrioNode toinenPopped = pr.pop();
        assertEquals(3.0, toinenPopped.getPrio(), 0.001);
        assertEquals(0,pr.getJono().size());
    }
}
