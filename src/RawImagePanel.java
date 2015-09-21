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
        int oldImageWidth = rawImage.getImageWidth();
        int oldImageHeight = rawImage.getImageHeight();
        rawImage.setImageWidth(width);
        updateImage(getGraphics(), oldImageWidth, oldImageHeight);
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
        drawImage(g);
    }

    private void updateImage(Graphics g, int width, int height){
        g.clearRect(0, 0, width, height);
        drawImage(g);
    }

    private void drawImage(Graphics g){
        g.drawImage(rawImage.getBufferImage(), 0, 0, null);
    }
}
