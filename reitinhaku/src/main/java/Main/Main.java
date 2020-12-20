package Main;

import Algoritmit.Atahti;
import Algoritmit.NodeHolder;
import Algoritmit.YksiSuuntainen;
import GatherProcess.CSVHandler;
import Kanta.Connect;
import Tietorakenteet.Node;
import UI.MapViewer;
import UI.Frame;
import UI.RoutePaint;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.GeoPosition;


public class Main {
    public static void main(String[] args) {    
        CSVHandler handler = new CSVHandler();
        String path = "C:\\Users\\toni_\\Koulu\\UTAPIOLA.csv";
        Scanner myObj = new Scanner(System.in);
        NodeHolder nh = new NodeHolder();
        while(true) {
            System.out.println("Jos sqlkanta ei jostain syystä toimi, kokeile annettua csv:tä");
            System.out.println("CSV vai annettu tietokanta? csv/sql");
            String valinta = myObj.nextLine();
                if(valinta.equals("sql")) {
                    System.out.println("Reitti tietokantaan: ");
                String dbPath = myObj.nextLine();
                Connect yht = new Connect(dbPath);
                ArrayList<Node> sqlNodes = yht.getAll();
                int pituus = sqlNodes.size()-1;
                System.out.println("Valitse node1, nrot 0 - "+pituus);
                Integer node1 = Integer.parseInt(myObj.nextLine());
                System.out.println("Valitse node2, nrot 0 - "+pituus);
                Integer node2 = Integer.parseInt(myObj.nextLine());
                List<GeoPosition> track = new ArrayList<GeoPosition>();
                Atahti tahti = new Atahti(sqlNodes);
                tahti.etsiReitti(sqlNodes.get(node1), sqlNodes.get(node2));
                tahti.parsiReitti();
                track = tahti.getValmisReitti();
                
                System.out.println("k sulkee ohjelman");
                String exit = myObj.nextLine();
                if(exit.equals("k")) {
                    break;
                }
            } else {
                System.out.println("Reitti CSV:hen");
                String csvPath = myObj.nextLine();
                try{
                    handler.readCSV(csvPath);
                } catch (Exception e) {
                    System.out.println(e);
                    break;
                }
                nh.setNodes(handler.csvToNodes());
                nh.laskeNaapurit();
                nh.indeksoiNodet();
                //System.out.println("Tallennetaanko tulokset tietokantaa");
                ArrayList<Node> nodet = nh.getNodes();
                int pituus = nodet.size()-1;
                System.out.println("Valitse node1, nrot 0 - "+pituus);
                Integer node1 = Integer.parseInt(myObj.nextLine());
                System.out.println("Valitse node2, nrot 0 - "+pituus);
                Integer node2 = Integer.parseInt(myObj.nextLine());
                List<GeoPosition> track = new ArrayList<GeoPosition>();
                Atahti tahti = new Atahti(nodet);
                tahti.etsiReitti(nodet.get(node1), nodet.get(node2));
                tahti.parsiReitti();
                track = tahti.getValmisReitti();
                
                MapViewer mapper = new MapViewer(7,60.2055, 24.6559);
                RoutePaint painter = new RoutePaint(track);
                MouseInputListener mia = new PanMouseInputListener(mapper.getViewer());
                mapper.getViewer().addMouseListener(mia);
                mapper.getViewer().addMouseMotionListener(mia);
                mapper.getViewer().addMouseListener(new CenterMapListener(mapper.getViewer()));
                mapper.getViewer().addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapper.getViewer()));
                mapper.getViewer().addKeyListener(new PanKeyListener(mapper.getViewer()));
                Frame frame = new Frame(mapper.getViewer());
                mapper.getViewer().setOverlayPainter(painter);
                //System.out.println("");
            }
            
        }
        /*
        Connect conn = new Connect("C:\\Users\\toni_\\Koulu\\reitinetsin\\db.db");
        conn.clearAll();
        YksiSuuntainen ys = new YksiSuuntainen();
        
        try{
            handler.readCSV(path);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
        nh.setNodes(handler.csvToNodes());
        nh.laskeNaapurit();
        nh.indeksoiNodet();
        conn.insertAll(nh.getNodes());
        ArrayList<Node> sqlNodes = conn.getAll();
        
        List<GeoPosition> track = new ArrayList<GeoPosition>();
        Atahti tahti = new Atahti(sqlNodes);
        tahti.etsiReitti(sqlNodes.get(0), sqlNodes.get(10));
        tahti.parsiReitti();
        track = tahti.getValmisReitti();
        
        MapViewer mapper = new MapViewer(7,60.2055, 24.6559);
        RoutePaint painter = new RoutePaint(track);
        MouseInputListener mia = new PanMouseInputListener(mapper.getViewer());
        mapper.getViewer().addMouseListener(mia);
        mapper.getViewer().addMouseMotionListener(mia);
        mapper.getViewer().addMouseListener(new CenterMapListener(mapper.getViewer()));
        mapper.getViewer().addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapper.getViewer()));
        mapper.getViewer().addKeyListener(new PanKeyListener(mapper.getViewer()));
        Frame frame = new Frame(mapper.getViewer());
        mapper.getViewer().setOverlayPainter(painter);
        */
        
    }
    
}
