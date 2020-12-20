import Algoritmit.NodeHolder;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.*;

public class NodeHolderTest {

    NodeHolder nh;
    
    @Before
    public void setUp() {
        this.nh = new NodeHolder();
    }
    
    @Test
    public void getSmallestTest() {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        lista.add(50);
        lista.add(3);
        lista.add(20);
        lista.remove(1);
        lista.add(1);
        Integer pienin = nh.getSmallest(lista);
        assertEquals(1,pienin);
    }
    
}
