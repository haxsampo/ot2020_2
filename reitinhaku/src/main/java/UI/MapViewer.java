
package UI;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;


public class MapViewer {
    
    //GeoPosition espoo = new GeoPosition(60.2055, 24.6559);
    JXMapViewer viewer;
    TileFactoryInfo info;
    DefaultTileFactory tileFac;
    
    public MapViewer(int zoom, double lat, double lon) {
        JXMapViewer mapViewer = new JXMapViewer();
        this.viewer = mapViewer;
        TileFactoryInfo info = new OSMTileFactoryInfo();
        this.info = info;
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        this.tileFac = tileFactory;
        mapViewer.setTileFactory(tileFactory);

        // Use 8 threads in parallel to load the tiles
        tileFactory.setThreadPoolSize(8);

        // Set the focus
        GeoPosition espoo = new GeoPosition(60.2055, 24.6559);

        mapViewer.setZoom(zoom);
        mapViewer.setAddressLocation(espoo);
    }

    public JXMapViewer getViewer() {
        return viewer;
    }

    public void setViewer(JXMapViewer viewer) {
        this.viewer = viewer;
    }
}
