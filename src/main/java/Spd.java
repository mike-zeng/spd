import com.baidu.aip.util.Base64Util;
import detector.impl.HeadDetector;
import detector.impl.UpperBodyDetector;
import model.HeadInfo;
import model.SittingPosition;
import model.SpdImage;
import model.UpperBodyInfo;
import service.SittingPositionDetection;
import service.impl.SimpleSittingPositionDetection;

import java.util.HashMap;

/**
 * spd是该包的入口
 */
public class Spd {
    private static Spd spd=new Spd();
    private static HeadDetector headDetector=null;
    private static UpperBodyDetector upperBodyDetector=null;

   private Spd(){
   }
   //单例模式，谁让baidu Api免费用户不支持并发呢
   public static Spd getInstance(HashMap<String,String> headDetectorConfig,HashMap<String,String> upperBodyDetectorConfig){
       //获取单例
       headDetector=HeadDetector.getHeadDetecto();
       upperBodyDetector=UpperBodyDetector.getUpperBodyDetector();

       boolean b1=headDetector.init(headDetectorConfig.get("appId"),headDetectorConfig.get("apiKey"),headDetectorConfig.get("secretKey"));
       boolean b2=upperBodyDetector.init(upperBodyDetectorConfig.get("appId"),upperBodyDetectorConfig.get("apiKey"),upperBodyDetectorConfig.get("secretKey"));
       if (!(b1&&b2)){
           return null;
       }
       return spd;
   }

    /**
     * 获取坐姿信息
     * @param uid 用户id
     * @param base 图片的base64码，大小不超过2MB
     * @return 坐姿信息类
     */
   private SittingPosition getSittingPosition(Integer uid,String base){
       HeadInfo headInfo=null;
       UpperBodyInfo upperBodyInfo=null;

       SpdImage spdImage1=new SpdImage(base,SpdImage.BASE64);
       SpdImage spdImage2=new SpdImage(Base64Util.decode(base));
       //获取基础信息：头部信息，关键点信息
       try {
           headInfo=headDetector.detection(spdImage1);
           upperBodyInfo=upperBodyDetector.detection(spdImage2);

           if (headInfo==null||upperBodyInfo==null){
               return null;
           }
       }catch (Exception e){
           e.printStackTrace();
       }

       SittingPosition sittingPosition=null;

       SittingPositionDetection sittingPositionDetection=new SimpleSittingPositionDetection();
       sittingPosition=sittingPositionDetection.getSittingPosition(headInfo,upperBodyInfo);
       return sittingPosition;
   }

}
