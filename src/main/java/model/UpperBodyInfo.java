package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 描述上半身信息
 * @author zeng
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpperBodyInfo {
    //参考宽度
    private Integer width;

    //参考高度
    private Integer height;

    //关键点1 鼻子
    private Point nose;

    //关键点2 颈部
    private Point neck;

    //关键点3 左肩
    private Point leftShoulder;

    //关键点4 右肩
    private Point rightShoulder;

    //关键点5 左手肘
    private Point leftElbow;

    //关键点6 右手肘
    private Point rightElbow;

    //关键点7 左手腕
    private Point leftWrist;

    //关键点8 右手腕
    private Point rightWrist;

    private double bodyWidth;

    private double bodyHeight;

}
