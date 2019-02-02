package detector.impl;

import com.baidu.aip.face.AipFace;
import com.google.gson.Gson;
import detector.Detector;
import model.Angle;
import model.HeadInfo;
import model.Location;
import model.SpdImage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 头部检测器
 * @author zeng
 */
public class HeadDetector implements Detector {
    private AipFace client=null;
    private boolean state=false;

    public boolean init(String appId, String apiKey, String secretKey) {
        client=new AipFace(appId,apiKey,secretKey);
        if (client!=null){
            state=true;
        }
        return state;
    }

    public HeadInfo detection(SpdImage spdImage) throws Exception {
        HeadInfo headInfo=new HeadInfo();
        if (!state){
            throw new Exception("检测器初始化失败!");
        }
        try {
            JSONObject ret=client.detect(spdImage.getImage(),spdImage.getType(),new HashMap<String, String>());

            JSONObject result= (JSONObject) ret.get("result");

            JSONArray faceList= (JSONArray) result.get("face_list");

            if (faceList.length()<=0){
                return null;
            }
            //获取人脸信息
            JSONObject faceInfo=faceList.getJSONObject(0);

            //获取3d旋转角度
            JSONObject angle= (JSONObject) faceInfo.get("angle");
            JSONObject location=(JSONObject)faceInfo.get("location");
            Gson gson=new Gson();

            if (angle!=null){
                Angle angle1=gson.fromJson(angle.toString(), Angle.class);
                headInfo.setAngel(angle1);
            }else {
                return null;
            }
            if (location!=null){
                System.out.println(location);
                Location location1=gson.fromJson(location.toString(),Location.class);
                headInfo.setLocation(location1);
            }else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
        return headInfo;
    }

}
