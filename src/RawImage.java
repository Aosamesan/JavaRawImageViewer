import java.awt.*;
import java.awt.image.BufferedImage;

public class RawImage {
    private byte[] imageBytes;
    private int imageHeight;
    private int imageWidth;
    private Image bufferImage;
    private Dimension imageSize;

    public RawImage(){
        imageBytes = null;
        imageHeight = 0;
        imageWidth = 0;
        bufferImage = null;
        imageSize = new Dimension(imageWidth, imageHeight);
    }

    public final byte[] getImageBytes() {return imageBytes;}

    public int getImageHeight(){
        return imageHeight;
    }

    public int getImageWidth(){
        return imageWidth;
    }

    public Image getBufferImage(){
        return bufferImage;
    }

    public Dimension getSize(){
        return imageSize;
    }

    public void setImageWidth(int width){
        setImage(imageBytes, width);
    }

    private void setSize(int width, int height){
        imageSize.setSize(width, height);
    }

    public void setImage(byte[] imageBytes, int width){
        if(imageBytes == null)
            return;
        if(this.imageBytes != imageBytes)
            this.imageBytes = imageBytes;
        if(width < 1)
            width = (int)Math.sqrt(imageBytes.length);
        imageWidth = width;
        imageHeight = imageBytes.length / width;

        bufferImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = bufferImage.getGraphics();
        Color c;
        for(int i = 0; i < imageHeight; i++){
            for(int j = 0; j < imageWidth; j++){
                c = getColorFromByte(this.imageBytes[i * imageWidth + j]);
                g.setColor(c);
                g.drawRect(j, i, 1, 1);
            }
        }
        g.dispose();
        bufferImage.flush();
        setSize(imageWidth, imageHeight);
    }

    public static Color getColorFromByte(byte b){
        int value = b & 0xFF;
        return new Color(value, value, value);
    }
}
