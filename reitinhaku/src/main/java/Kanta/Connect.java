package Kanta;

import Tietorakenteet.Kaari;
import Tietorakenteet.Node;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.jxmapviewer.viewer.GeoPosition;

/*
* Connect toimii SQL-rajapintana.
*/

public class Connect {
    
    String path;
    Connection conn;
    
    public Connect(String path) {
        this.path = path;
    }
    /*
    Avaa yhteyden pathin osoittamaan tietokantaan.
    */
    private void connect() {
        Connection conn = null;
        try {
            String currPath = "jdbc:sqlite:"+path;
            conn = DriverManager.getConnection(currPath);
            this.conn = conn;
            System.out.println("Yhteys tietokantaan muodostettu");
        } catch (Exception e) {
            System.out.println("Connect connect(): "+e);
            System.out.println(e.getMessage());
        }
    }
    /*
    Sulkee yhteyden tietokantaan
    */
    private void closeConnection() {
        try {
            if(this.conn != null) {
                this.conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println(ex.getMessage());
        }
    }
    
    /*
    Lisää kantaan yhden noden.
    */
    public void insertNode(Node nod) {
        connect();
        Double lat = nod.getPos().getLatitude();
        Double lon = nod.getPos().getLongitude();
        String sql = "INSERT INTO Node(Latitude,Longitude) VALUES(?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql); 
            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lon);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Connect insertNode: "+e);
            System.out.println(e.getMessage());
        }
        closeConnection();
    }
    
    /*
    Hakee kaikki SQL-kannassa olevat nodet ja näitten naapurit ja yhdistelee näistä
    käytettävän listan.
    */
    public ArrayList<Node> getAll() {
        connect();
        ArrayList<Node> ret = selectAllNodes();
        ArrayList<Kaari> kaaret = getKaaret();
        ret = combineKaariNode(ret,kaaret);
        closeConnection();
        return ret;
    }
    /*
    Hakee kannan kaikki nodet
    */
    private ArrayList<Node> selectAllNodes() {
        ArrayList<Node> ret = new ArrayList<Node>();
        String sql = "SELECT * FROM Node";
        try {
            System.out.println("Selectall try");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            while(rs.next()) {
                i++;
                GeoPosition pos = new GeoPosition(rs.getDouble("Latitude"),
                                                rs.getDouble("Longitude"));
                Node nod = new Node(pos,rs.getInt("NodeId"));
                ret.add(nod);
            }
        } catch (Exception e) {
            System.out.println("Connect selectAllNodes: "+e);
            System.out.println(e.getMessage());
        }
        return ret;
    }
    
    /*
    Hakee kaaret - eli nodejen naapuritiedot
    */
    public ArrayList<Kaari> getKaaret() {
        ArrayList<Kaari> kaaret = new ArrayList<Kaari>();
        String sql = "SELECT * FROM Kaari";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            while(rs.next()) {
                //System.out.println("getKaaret i: "+i);
                i++;
                Double pituus = rs.getDouble("Pituus");
                int id = rs.getInt("KaariId");
                int id1 = rs.getInt("NodeId1");
                int id2 = rs.getInt("NodeId2");
                Kaari uusi = new Kaari(pituus,id,id1,id2);
                kaaret.add(uusi);
            }
        } catch (Exception e) {
            System.out.println("Connect getKaaret: "+e);
            System.out.println(e.getMessage());
        }
        return kaaret;
    }
    
    /*
    Yhdistää nodejen naapuritiedot sekä nodet itsensä
    */
    private ArrayList<Node> combineKaariNode(ArrayList<Node> nodes, ArrayList<Kaari> kaaret) {
        for(int i = 0; i <kaaret.size();i++) {
            Kaari kaari = kaaret.get(i);
            Node yks = nodes.get(kaari.getNodeId1()-1);
            Node kaks = nodes.get(kaari.getNodeId2()-1);
            yks.lisaaNaapuri(kaks, kaari.getPituus());
            //kaks.lisaaNaapuri(yks,kaari.getPituus());
        }
        return nodes;
    }
    
    public void printAllNode() {
        connect();
        String sql = "SELECT * FROM Node";
        try {
            //System.out.println("Selectall try");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                System.out.println(rs.getInt("NodeId") + "\t"+
                                    rs.getDouble("Latitude")+"\t"+
                                    rs.getDouble("Longitude"));
            }
        } catch (Exception e) {
            System.out.println("Connect printAllNode: "+e);
            System.out.println(e.getMessage());
        }
        closeConnection();
    }
    
    public void insertAll(ArrayList<Node> nodes) {
        connect();
        String sql = "";
        try {
            int i = 0;
            for(Node nod : nodes) {
                //System.out.println("insertAll i: "+i);
                //System.out.println(nod);
                i++;
                sql = "INSERT INTO Node(NodeId,Latitude,Longitude) VALUES(?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,nod.getId());
                pstmt.setDouble(2, nod.getPos().getLatitude());
                pstmt.setDouble(3, nod.getPos().getLongitude());
                pstmt.executeUpdate();
                insertKaaret(nod);
            }
            System.out.println("Nodet kannassa");
        } catch (Exception e) {
            System.out.println("Connect, insertAll: "+e);
            System.out.println(e.getMessage());
        }
        closeConnection();
    }
    
    private void insertKaaret(Node nod) {
        ArrayList<Double> etaisyydet = nod.getEtaisyydet();
        ArrayList<Node> naapurit = nod.getNaapurit();
        //System.out.println("Naapurit len: "+naapurit.size()+"  etäisyydet len: "+etaisyydet.size());
        
        for(int i=0;i<etaisyydet.size();i++) {
            //System.out.println(etaisyydet.get(i)+" "+nod.getId()+" "+naapurit.get(i).getId());
            if(!(naapurit.get(i).getId()==null)){ //voi ehkä poistaa lopulliseen
                insertKaari(etaisyydet.get(i),nod.getId(),naapurit.get(i).getId());
            }
            
        }
    }
    
    private void insertKaari(Double pituus, int nod1, int nod2) {
        //System.out.println("Connect - insertKaari - pituus: "+pituus);
        String sql = "INSERT INTO Kaari(Pituus,NodeId1,NodeId2) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, pituus);
            pstmt.setInt(2,nod1);
            pstmt.setInt(3,nod2);
            pstmt.executeUpdate();
            //pstmt.getGeneratedKeys().getInt(1);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
        }
        
    }
    
    /*
    private void insertNodeKaari(int i, int[] kaaret) {
        String sql = "";
        try {
            for(int j = 0; j<kaaret.length;j++) {
                System.out.println("Kaaren indeksi:"+kaaret[j]);
                sql = "INSERT INTO NodeKaari (NodeId,KaariId) VALUES(?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, i);
                pstmt.setInt(2, kaaret[j]);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }*/
    
    public void clearAll() {
        connect();
        String sql = "";
        try {
            sql = "DELETE  FROM Node";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            sql = "DELETE  FROM Kaari";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
        }
        closeConnection();
    }
    
}
