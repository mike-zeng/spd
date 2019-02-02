package model;

import java.sql.Timestamp;

/**
 * 坐姿数据
 * @author zeng
 */
public class SittingPosition {
    int id;
    int uid;
    private int status;//0,1,2,3,4,5,6
    private double quality;//质量，范围为[0,1]越接近1，越符合该种姿势
    private Timestamp time;
}
