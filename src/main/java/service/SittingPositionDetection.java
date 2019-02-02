package service;

import model.HeadInfo;
import model.SittingPosition;
import model.UpperBodyInfo;

/**
 * 坐姿检测
 */
public interface SittingPositionDetection {
    public SittingPosition getSittingPosition(HeadInfo headInfo, UpperBodyInfo upperBodyInfo);
}
