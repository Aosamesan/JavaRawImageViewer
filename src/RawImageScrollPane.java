import java.awt.*;
import javax.swing.*;

public class RawImageScrollPane extends JScrollPane {
    private RawImagePanel rawImagePanel;
    private JScrollBar horizontalBar;
    private JScrollBar verticalBar;

    public RawImageScrollPane(){
        super();
        rawImagePanel = new RawImagePanel();
        setBackground(Color.blue);
        setViewportView(rawImagePanel);

        horizontalBar = getHorizontalScrollBar();
        verticalBar = getVerticalScrollBar();
    }

    public void setNewImage(byte[] bytes){
        try{
            String s = JOptionPane.showInputDialog(this, "Input width");
            int width = Integer.parseInt(s);
            rawImagePanel.setRawImage(bytes, width);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setRawImageWidth(int width){
        Dimension oldSize = rawImagePanel.getSize();
        rawImagePanel.setRawImageWidth(width);
        Dimension newSize = rawImagePanel.getSize();
        if(oldSize.getWidth() > newSize.getWidth()){
            horizontalBar.setVisible(false);
        } else {
            horizontalBar.setVisible(true);
        }

        if(oldSize.getHeight() > newSize.getHeight()){
            verticalBar.setVisible(false);
        } else {
            verticalBar.setVisible(true);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        rawImagePanel.paintComponent(g);
    }
}
