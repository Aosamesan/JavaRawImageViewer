public interface IBlur extends IImageOperation {
    byte[] blur(final byte[] originBytes, final int width);

    @Override
    default byte[] operation(final byte[] originBytes, final int width){
        return blur(originBytes, width);
    }
}