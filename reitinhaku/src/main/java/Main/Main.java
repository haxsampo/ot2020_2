package Main;

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
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;


public class Main {
    public static void main(String[] args) {       
        MapViewer mapper = new MapViewer(7,60.2055, 24.6559);
        
        //(60.2055, 24.6559);
        GeoPosition frankfurt = new GeoPosition(60.2055, 24.6559);
        GeoPosition wiesbaden = new GeoPosition(60.2065, 24.6500);
        GeoPosition mainz     = new GeoPosition(60.205, 24.68);
        GeoPosition darmstadt = new GeoPosition(60.21, 24.66);
        GeoPosition offenbach = new GeoPosition(60.22, 24.67);
        List<GeoPosition> track = Arrays.asList( frankfurt, wiesbaden, mainz,
                                                darmstadt, offenbach);
        RoutePaint painter = new RoutePaint(track);
        //mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
        // -> zoomi on ilmeisesti jo implementoitu
        Frame frame = new Frame(mapper.getViewer());
        
        mapper.getViewer().setOverlayPainter(painter);
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //int width = (int)screenSize.getWidth();
        //int height = (int)screenSize.getHeight();

        /** Display the viewer in a JFrame
        JFrame frame = new JFrame("Reitinhaku_juttu");
        frame.getContentPane().add(mapper.getViewer());
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);   
        **/
    }
    
}
