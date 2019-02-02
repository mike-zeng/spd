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
    //人脸位置
    private Location location;
    //人脸旋转角度参数
    private Angle angel;
    //人脸可信度
    private Double probability;
}
