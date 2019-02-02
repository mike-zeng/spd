package model;


/**
 * 统一图片形式
 */
public class SpdImage {
    private final static String URL="url";
    private final static String BASE64="base64";
    private final static String BYTE_ARRAY="byteArray";

    private Integer width;
    private Integer height;

    private String image=null;
    private byte[] imageArr=null;
    private String type=null;

    public SpdImage(){

    }

    public SpdImage(byte[] image){
        this.type=SpdImage.BYTE_ARRAY;
        imageArr=image;
    }

    public SpdImage(String image,String type){
        this.image=image;
        this.type=type;
    }

    public String getImage(){
        return image;
    }

    public String getType(){
        return type;
    }

    public byte[] getImageArr() {
        return imageArr;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
