package service.impl;

import model.HeadInfo;
import model.SittingPosition;
import model.UpperBodyInfo;
import service.SittingPositionDetection;

/**
 * 简单的坐姿检测服务，仅供测试，因为深度学习模型暂时训练不出来
 */
public class SimpleSittingPositionDetection implements SittingPositionDetection {

    /**
     * 基于头部信息和人体关键点获取坐姿信息
     * @param headInfo
     * @param upperBodyInfo
     * @return
     */
    public SittingPosition getSittingPosition(HeadInfo headInfo, UpperBodyInfo upperBodyInfo) {
        //保证信息的可靠性
        if (headInfo.getProbability()<0.5){
            return null;
        }
        return null;
    }

    /**
     * 判断头部偏离系数，范围为[-1,1]
     * @param headInfo
     * @param upperBodyInfo
     * @return 偏离系数
     */
    private static Double analysisHeadSlantData(HeadInfo headInfo, UpperBodyInfo upperBodyInfo){
        return 0.0;
    }

    /**
     * 判断驼背系数
     * @param headInfo
     * @param upperBodyInfo
     * @return
     */
    private static Double analysisHumpbackData(HeadInfo headInfo, UpperBodyInfo upperBodyInfo){
        return 0.0;
    }

    /**
     * 判断是否趴下
     * @param headInfo
     * @param upperBodyInfo
     * @return
     */
    private static boolean isUnderarm(HeadInfo headInfo, UpperBodyInfo upperBodyInfo){
        return false;
    }

    /**
     * 判断手部位置
     * @param headInfo
     * @param upperBodyInfo
     * @return = 0 正常
     *         > 0
     *         < 0
     */
    private static Double judgeHandPosition(HeadInfo headInfo, UpperBodyInfo upperBodyInfo){

        return 0.0;
    }
}
