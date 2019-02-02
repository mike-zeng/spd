package detector.impl;


import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import com.google.gson.Gson;
import detector.Detector;
import model.Point;
import model.SpdImage;
import model.UpperBodyInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * 上身检测器
 * @author zeng
 */
public class UpperBodyDetector implements Detector {
    AipBodyAnalysis client=null;
    private boolean state=false;
    Gson gson=new Gson();
    JSONObject bodyParts=null;
    public boolean init(String appId, String apiKey, String secretKey) {
        client=new AipBodyAnalysis(appId,apiKey,secretKey);
        if (client==null){
            return false;
        }
        state=true;
        return true;
    }

    public UpperBodyInfo detection(SpdImage spdImage) throws Exception {
        if (!state){
            return null;
        }
        UpperBodyInfo upperBodyInfo=new UpperBodyInfo();
        upperBodyInfo.setWidth(spdImage.getWidth());
        upperBodyInfo.setHeight(spdImage.getHeight());

        JSONObject ret=client.bodyAnalysis(spdImage.getImageArr(),new HashMap<String, String>());
        System.out.println(ret);
        if (ret==null||ret.get("person_info")==null){
            return null;
        }
        JSONArray jsonArray= (JSONArray) ret.get("person_info");
        if (jsonArray.length()<=0){
            return null;
        }
        JSONObject personInfo=jsonArray.getJSONObject(0);

        bodyParts= (JSONObject) personInfo.get("body_parts");


        Point nose=getKeyPoint("nose");
        Point neck=getKeyPoint("neck");

        Point leftShoulder=getKeyPoint("left_shoulder");
        Point rightShoulder=getKeyPoint("right_shoulder");

        Point leftElbow=getKeyPoint("left_elbow");
        Point rightElbow=getKeyPoint("right_elbow");

        Point leftWrist=getKeyPoint("left_wrist");
        Point rightWrist=getKeyPoint("right_wrist");

        upperBodyInfo.setNose(nose);
        upperBodyInfo.setNeck(neck);

        upperBodyInfo.setLeftShoulder(leftShoulder);
        upperBodyInfo.setRightShoulder(rightShoulder);

        upperBodyInfo.setLeftElbow(leftElbow);
        upperBodyInfo.setRightElbow(rightElbow);

        upperBodyInfo.setLeftWrist(leftWrist);
        upperBodyInfo.setRightWrist(rightWrist);

        return upperBodyInfo;
    }

    public Point getKeyPoint(String key){
        Point point=null;
        String jsonNose=bodyParts.get(key).toString();
        point=gson.fromJson(jsonNose, Point.class);
        return point;
    }

    //图片到byte数组
    public byte[] image2byte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }
}
