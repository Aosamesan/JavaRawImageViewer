import javax.swing.*;
import java.awt.*;

public class RawImagePanel extends JPanel{
    RawImage rawImage;

    public RawImagePanel(){
        super();
        rawImage = new RawImage();
    }

    public void setRawImage(byte[] imageBytes, int width){
        rawImage.setImage(imageBytes, width);
    }

    public void setRawImageWidth(int width){
        rawImage.setImageWidth(width);
        updateImage(getGraphics());
    }

    @Override
    public Dimension getSize(){
        return rawImage.getSize();
    }

    @Override
    public Dimension getPreferredSize(){
        return getSize();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        repaint();
        updateImage(g);
    }

    private void updateImage(Graphics g){
        g.drawImage(rawImage.getBufferImage(), 0, 0, null);
    }
}
