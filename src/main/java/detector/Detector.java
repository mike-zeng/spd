package detector;

import model.SpdImage;

public interface Detector {
    public boolean init(String appId,String apiKey,String secretKey);

    public Object detection(SpdImage spdImage) throws Exception;
}
