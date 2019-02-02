package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * 描述头部信息
 * @author zeng
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeadInfo {
    //参考宽度
    private Integer width;
    //参考高度
    private Integer height;
    //人脸参照照片垂直方向的角度(顺时针)
    private Double rotation;
    //人脸旋转角度参数
    private Map<String,Double> angel;
    //人脸表情
    private String expression;
    //人脸可信度
    private Double probability;
}
