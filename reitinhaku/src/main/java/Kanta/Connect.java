package Kanta;

import Tietorakenteet.Kaari;
import Tietorakenteet.Node;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.jxmapviewer.viewer.GeoPosition;

public class Connect {
    
    String path;
    Connection conn;
    
    public Connect(String path) {
        this.path = path;
    }
    
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
    
    public ArrayList<Node> getAll() {
        connect();
        ArrayList<Node> ret = selectAllNodes();
        ArrayList<Kaari> kaaret = getKaaret();
        ret = combineKaariNode(ret,kaaret);
        closeConnection();
        return ret;
    }
    
    private ArrayList<Node> selectAllNodes() {
        ArrayList<Node> ret = new ArrayList<Node>();
        String sql = "SELECT * FROM Node";
        try {
            System.out.println("Selectall try");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
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
    Lisätään nodeihin kaaret eli naapurit
    */
    private ArrayList<Kaari> getKaaret() {
        ArrayList<Kaari> kaaret = new ArrayList<Kaari>();
        String sql = "SELECT * FROM Kaari";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
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
    
    private ArrayList<Node> combineKaariNode(ArrayList<Node> nodes, ArrayList<Kaari> kaaret) {
        for(int i = 0; i <kaaret.size();i++) {
            Kaari kaari = kaaret.get(i);
            Node yks = nodes.get(kaari.getNodeId1());
            Node kaks = nodes.get(kaari.getNodeId2());
            yks.lisaaNaapuri(kaks, kaari.getPituus());
            kaks.lisaaNaapuri(yks,kaari.getPituus());
        }
        return nodes;
    }
    
    public void printAllNode() {
        connect();
        String sql = "SELECT * FROM Node";
        try {
            System.out.println("Selectall try");
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
        System.out.println("Nodes length in insertall"+nodes.size());
        connect();
        String sql = "";
        try {
            for(Node nod : nodes) {
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
        
        for(int i=0;i<etaisyydet.size();i++) {
            //System.out.println("naapuri Id:"+nod.getNaapurit().get(i).getId());
            insertKaari(etaisyydet.get(i),nod.getId(),
                        nod.getNaapurit().get(i).getId());
        }
    }
    
    private void insertKaari(Double pituus, int nod1, int nod2) {
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
