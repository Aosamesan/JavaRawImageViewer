import java.awt.*;
import javax.swing.*;

public class RawImageScrollPane extends JScrollPane {
    private RawImagePanel rawImagePanel;

    public RawImageScrollPane(){
        super();
        rawImagePanel = new RawImagePanel();
        add(rawImagePanel);
        setBackground(Color.blue);
        setViewportView(rawImagePanel);
    }

    public void setNewImage(byte[] bytes){
        try{
            String s = JOptionPane.showInputDialog(this, "Input width");
            int width = Integer.parseInt(s);
            if(width > 0){
                rawImagePanel.setRawImage(bytes, width);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setRawImageWidth(int width){
        rawImagePanel.setRawImageWidth(width);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        rawImagePanel.paintComponent(g);
    }
}
