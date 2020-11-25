package UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import org.jxmapviewer.JXMapViewer;


public class Frame {
    
    JFrame frame;
    Dimension screenSize;
    int width;
    int height;
    
    public Frame(JXMapViewer viewer) {
        JFrame frame = new JFrame("Reitinhaku_juttu");
        this.frame = frame;
        addViewer(viewer);
        getDimensions();
        frame.setSize(width, height);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public void getDimensions() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = (int)screenSize.getWidth();
        this.height = (int)screenSize.getHeight();
    }
    
    public void addViewer(JXMapViewer viewer) {
        this.frame.getContentPane().add(viewer);
    }
    
    public void setSize(int width, int height) {
        this.frame.setSize(width,height);
    }
    
}
