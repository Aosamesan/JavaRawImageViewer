public class BasicBlur implements IBlur {
    private int radius;

    public BasicBlur(){
        radius = 1;
    }

    public int getRadius(){
        return radius;
    }

    public void setRadius(int r){
        radius = r;
    }

    @Override
    public byte[] blur(final byte[] originBytes, final int width){
        int height = originBytes.length / width;
        byte[] newBytes = new byte[originBytes.length];
        int x, y;

        for(int i = 0; i < originBytes.length; i++){
            x = i % width;
            y = i / width;
            newBytes[i] = blur(originBytes, width, height, x, y);
        }

        return newBytes;
    }

    byte blur(final byte[] originBytes, final int width, final int height, int x, int y){
        int left = x - radius < 0 ? 0 : x - radius;
        int top = y - radius < 0 ? 0 : y - radius;
        int right = x + radius >= width ? width - 1 : x + radius;
        int bottom = y + radius >= height ? height - 1 : y + radius;
        int size = (right - left + 1) * (bottom - top + 1);
        double sum = 0;

        for(int i = top; i <= bottom; i++){
            for(int j = left; j <= right; j++){
                sum += originBytes[i * width + j];
            }
        }
        System.out.println(sum / size);

        return (byte)(((int)(sum / size)) & 0xFF);
    }
}
