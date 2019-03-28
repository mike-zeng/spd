# spd
spd是sitting position detection的缩写
spd是一套基于Baidu AI成熟的人脸检测和人体分析接口实现的一套坐姿识别工具

# 算法思路
本项目参考了知网中的一篇论文并针对具体应用调整了部分参数。
该项目的最初目的是为了实验室的项目服务。

# 开始使用
你可以参考下面的测试类，了解如何使用，我们对细节进行了高度的封装，只暴露了一个接口。

```
public class Test{


    public static void main(String[] args) throws FileNotFoundException {
       HashMap<String,String> config1=new HashMap<String, String>();
       config1.put("appId","xxxxxx");
       config1.put("apiKey","xxxxxxxx");
       config1.put("secretKey","xxxxxxxx");

       HashMap<String,String> config2=new HashMap<String, String>();
       config2.put("appId","xxxxxx");
       config2.put("apiKey","xxxxxxx");
       config2.put("secretKey","xxxxxxxxx");

       Spd spd=Spd.getInstance(config1,config2);
       String base=getImageStr("/home/zeng/IdeaProjects/spd/src/main/resources/test7.jpg");

       //获取图片base64编码信息
       //。。。
        long s1=System.currentTimeMillis();
       SittingPosition sittingPosition=spd.getSittingPosition(1,base);
        long s2=System.currentTimeMillis();
        System.out.println((s2-s1)+"ms");
       System.out.println(sittingPosition);
    }

    public static String getImageStr(String imgFile)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }
}
```
