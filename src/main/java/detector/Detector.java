package detector;

import java.util.HashMap;

public interface Detector {
    public void init(HashMap<String,String> config);

    public Object detection(String base);
}
