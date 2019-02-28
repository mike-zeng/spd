package model;


import com.baidu.aip.util.Base64Util;
import sun.misc.BASE64Decoder;

/**
 * 统一图片形式
 */
public class SpdImage {
    public final static String URL="URL";
    public final static String BASE64="BASE64";


    private Integer width;
    private Integer height;

    private String image=null;
    private String type=SpdImage.BASE64;

    public SpdImage(){

    }

    public SpdImage(String image){
        this.image=image;
    }

    public String getImage(){
        return image;
    }

    public String getType(){
        return type;
    }

    public byte[] getImageArr() {
        BASE64Decoder base64Decoder=new BASE64Decoder();
        byte[] bytes=null;
        try {
            bytes=base64Decoder.decodeBuffer(image);
        }catch (Exception e){
            e.printStackTrace();
        }

        return bytes;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
