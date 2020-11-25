
package UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

public class RoutePaint {
    
    private List<GeoPosition> track;
    private Color color = Color.RED;
    private boolean antiAlias = true;
    
    public RoutePaint(List<GeoPosition> track) {
        this.track = new ArrayList<GeoPosition>(track);
    }
    
    public void paint(Graphics2D graph, JXMapViewer map, int width, int height) {
        graph = (Graphics2D) graph.create();
        Rectangle rect = map.getViewportBounds();
        graph.translate(-rect.x, -rect.y);
        if(antiAlias) {
            graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        graph.setColor(Color.BLACK);
        graph.setStroke(new BasicStroke(4));
        drawRoute(graph, map);
        /*
        g.setColor(color);
        g.setStroke(new BasicStroke(2));
        drawRoute(g, map);
        g.dispose();
        */
    }
    
    public void drawRoute(Graphics2D graph, JXMapViewer map ){
        int lastX = 0;
        int lastY = 0;
        boolean first = true;
        
        for(GeoPosition gp : this.track) {
            // convert geo-coordinate to world bitmap pixel
            Point2D pt = map.getTileFactory().geoToPixel(gp, map.getZoom());
            if(first) {
                first = false;
            }
            else {
                graph.drawLine(lastX, lastY, (int) pt.getX(), (int) pt.getY());
            }
            lastX = (int) pt.getX();
            lastY = (int) pt.getY();
        }
        

    }
    
    
}
