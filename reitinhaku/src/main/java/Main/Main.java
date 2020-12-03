package Main;

import GatherProcess.CSVHandler;
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
        String path = "C:\\Users\\toni_\\Koulu\\kordiAll.csv";
        
        try{
            handler.readCSV(path);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        List<List<String>> l = handler.getCsv();
        List<GeoPosition> track = new ArrayList<GeoPosition>();
        List<Node> nodet = new ArrayList<Node>();
        for(int i=0;i<l.size();i++) {
            if(!(l.get(i).get(11).equals("yAtZero"))) {
                double x1 = Double.parseDouble(l.get(i).get(11));
                double y1 = Double.parseDouble(l.get(i).get(10));
                GeoPosition uusGeo = new GeoPosition(y1, x1);
                Node kasiteltava = new Node(uusGeo);
                for(int j=0;j<l.size();j++) {
                    if(!(l.get(i).get(11).equals("yAtZero"))) {
                        
                    }
                }
            }
            
            
        }
        
        for(int i=0;i<l.size();i++) {
            if(!(l.get(i).get(11).equals("yAtZero"))) {
                try{
                    double x = Double.parseDouble(l.get(i).get(11));
                    double y = Double.parseDouble(l.get(i).get(10));
                    GeoPosition uus = new GeoPosition(x,y);
                    track.add(uus);
                } catch(Exception e) {
                    System.out.println(e);
                }
                 
            }
        }
        MapViewer mapper = new MapViewer(7,60.2055, 24.6559);
        
        //(60.2055, 24.6559);
        /*
        GeoPosition frankfurt = new GeoPosition(60.2055, 24.6559);
        GeoPosition wiesbaden = new GeoPosition(60.2065, 24.6500);
        GeoPosition mainz     = new GeoPosition(60.205, 24.68);
        GeoPosition darmstadt = new GeoPosition(60.21, 24.66);
        GeoPosition offenbach = new GeoPosition(60.22, 24.67);
        List<GeoPosition> track = Arrays.asList( frankfurt, wiesbaden, mainz,
                                                darmstadt, offenbach);
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
