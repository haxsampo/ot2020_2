package Main;

import Algoritmit.NodeHolder;
import Algoritmit.YksiSuuntainen;
import GatherProcess.CSVHandler;
import Kanta.Connect;
import Tietorakenteet.Node;
import UI.MapViewer;
import UI.Frame;
import UI.RoutePaint;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;


public class Main {
    public static void main(String[] args) {       
        CSVHandler handler = new CSVHandler();
        String path = "C:\\Users\\toni_\\Koulu\\example.csv";
        
        
        Connect conn = new Connect("C:\\Users\\toni_\\Koulu\\reitinetsin\\db.db");
        conn.clearAll();
        YksiSuuntainen ys = new YksiSuuntainen();
        
        try{
            handler.readCSV(path);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        NodeHolder nh = new NodeHolder();
        nh.setNodes(handler.csvToNodes());
        nh.laskeNaapurit();
        nh.indeksoiNodet();
        ArrayList<Node> nods = nh.getNodes();
        for(int j = 0; j<nods.size();j++) {
            System.out.println("j:"+j);
            Node noder = nods.get(j);
            System.out.println("noder Id: "+noder.getId());
            String naprId = "";
            for(int i = 0;i< noder.getNaapurit().size();i++) {
                naprId += noder.getNaapurit().get(i).getId()+" ";
            }
            System.out.println("Noder naapuri idt: "+naprId);
        }
        
        /*
        conn.insertAll(nods);
        ArrayList<Node> sqlNodes = conn.getAll();
        */
        List<GeoPosition> track = new ArrayList<GeoPosition>();
        track = ys.laskeReitti("p", nods.get(25));
        /*
        Node nod = nods.get(26);
        for(Node el : nods) {
            GeoPosition pos = el.getPos();
            Double lat = pos.getLatitude();
            Double lon = pos.getLongitude();
            if(lat == 60.2000017) {
                System.out.println("kakkiaisen naapurimäärä: "+el.getNaapurit().size());
            }
        }*/
        MapViewer mapper = new MapViewer(7,60.2055, 24.6559);
        
        //(60.2055, 24.6559);
        /*
        GeoPosition frankfurt = new GeoPosition(60.1963571, 24.8847357);
        GeoPosition wiesbaden = new GeoPosition(60.1963576, 24.8861595);
        GeoPosition mainz     = new GeoPosition(60.1963571, 24.8847357);
        GeoPosition darmstadt = new GeoPosition(60.1963571, 24.8847357);
        GeoPosition offenbach1 = new GeoPosition(60.1963571, 24.7641448);
        GeoPosition offenbach2 = new GeoPosition(60.1963575, 24.7696837);
        GeoPosition offenbach3 = new GeoPosition(60.1963573, 24.9719712);
        
        List<GeoPosition> track = Arrays.asList( frankfurt, wiesbaden, mainz,
                                                darmstadt, offenbach, offenbach1, offenbach2, offenbach3);
        */
        RoutePaint painter = new RoutePaint(track);
        
        MouseInputListener mia = new PanMouseInputListener(mapper.getViewer());
        mapper.getViewer().addMouseListener(mia);
        mapper.getViewer().addMouseMotionListener(mia);

        mapper.getViewer().addMouseListener(new CenterMapListener(mapper.getViewer()));

        mapper.getViewer().addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapper.getViewer()));

        mapper.getViewer().addKeyListener(new PanKeyListener(mapper.getViewer()));
        //mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
        // -> zoomi on ilmeisesti jo implementoitu
        Frame frame = new Frame(mapper.getViewer());
        
        mapper.getViewer().setOverlayPainter(painter);
        
        
    }
    
}
