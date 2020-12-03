package Algoritmit;

import Tietorakenteet.Node;
import java.util.ArrayList;

public class NodeHolder {
    
    ArrayList<Node> nodes;
    
    public NodeHolder() {
        this.nodes = new ArrayList<Node>();
    }
    
    public void add(Node node) {
        nodes.add(node);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
}
