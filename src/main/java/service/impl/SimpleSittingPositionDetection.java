package service.impl;

import model.*;
import service.SittingPositionDetection;

/**
 * 该坐姿检测程序的理论来源于知网的一篇论文的数据，准确性达到百分之90左右
 */
public class SimpleSittingPositionDetection implements SittingPositionDetection {

    /**
     * 基于头部信息和人体关键点获取坐姿信息
     * @param headInfo
     * @param upperBodyInfo
     * @return
     */
    public SittingPosition getSittingPosition(HeadInfo headInfo, UpperBodyInfo upperBodyInfo) {
        SittingPosition sittingPosition=new SittingPosition();
        //数据修复，保证数据残缺时可以得到有效数据

        //1. 获取是否趴桌
        boolean isUnderarm=isUnderarm(upperBodyInfo);
        if (isUnderarm==true){
            sittingPosition.setStatus(6);
            return sittingPosition;
        }

        //2. 身体是否倾斜
        boolean isBodySlant=isBodySlant(upperBodyInfo);
        if (isBodySlant){
            sittingPosition.setStatus(5);
            return sittingPosition;
        }

        //3.获取头部偏离
        int b=analysisHeadSlantData(headInfo);
        if (b==-1){
            sittingPosition.setStatus(1);
            return sittingPosition;
        }else if (b==1){
            sittingPosition.setStatus(2);
            return sittingPosition;
        }

        //4. 获取手部位置
        int a=judgeHandPosition(upperBodyInfo);
        if (a==1){
            sittingPosition.setStatus(3);
            return sittingPosition;
        }else if (a==-1){
            sittingPosition.setStatus(4);
            return sittingPosition;
        }

        //未出现异常
        sittingPosition.setStatus(0);
        return sittingPosition;
    }

    /**
     * 百度返回的数据中包含了人脸3d角度的偏移，可以直接通过参数判断头部是否偏了
     * 判断头部偏离系数，范围为[-1,1],当返回值为0时，表示头部未发生偏离，
     * @param headInfo
     * @return 偏离系数
     */
    private static int analysisHeadSlantData(HeadInfo headInfo){
        Double rotation=headInfo.getLocation().getRotation();
        int flag=rotation>0?1:-1;
        double temp_res=0.0;
        rotation=Math.abs(rotation);

        //判断是否在合理的范围内
        if(rotation<=SittingPositionParam.HEAD_LEFT_ROTATION){
            return 0;//没有偏离
        }
        return flag;
    }


    /**
     * 判断是否趴下
     *  人体区域高度和人体区域宽度的比值小于常数k
     * @param upperBodyInfo
     * @return
     */
    private static boolean isUnderarm(UpperBodyInfo upperBodyInfo){
        double bodyWidth=upperBodyInfo.getBodyWidth();
        double bodyHeight=upperBodyInfo.getBodyHeight();

        Point nose=upperBodyInfo.getNose();
        Point shoulder=upperBodyInfo.getLeftShoulder();

        //利用宽度和高度比
        if (bodyWidth==0){
            return false;
        }
        double ratio=nose.getY()/bodyWidth;
        if (ratio<0.65){
            return true;
        }
        //利用关键点相对位置比较

        return false;//没有趴下
    }

    /**
     * 判断身体是否倾斜
     * 根据论文中训练的模型表示，下面这种情况可以视为身体倾斜
     * 左偏 L2<80度
     * 右偏 L2>100度
     *
     */

    private static boolean isBodySlant(UpperBodyInfo upperBodyInfo){
        Point p1=upperBodyInfo.getRightShoulder();//左肩位置
        Point p2=upperBodyInfo.getNeck();//脖子位置

        double tan=(p1.getY()-p2.getY())/(p1.getX()-p2.getX());
        tan=Math.abs(tan);
        System.out.println();
        double l2=0;
        if (p1.getY()<p2.getY()){
            l2=90.0+Math.atan(tan)/Math.PI*180;
        }else {
            l2=90.0-Math.atan(tan)/Math.PI*180;
        }
        if (l2<80||l2>100){
            return true;
        }
        return false;
    }

    /**
     * 判断手部位置
     * @param upperBodyInfo
     * @return = 0 正常
     *         = 1
     *         = -1
     */
    private static int judgeHandPosition(UpperBodyInfo upperBodyInfo){
        Point point1=upperBodyInfo.getNose();//鼻子
        Point point2=upperBodyInfo.getNeck();//脖子
        Point point3=upperBodyInfo.getLeftElbow();//左手腕
        Point point4=upperBodyInfo.getRightElbow();//右手腕

        if (point1!=null&&point2!=null&&point3!=null&&point4!=null){
            if ((point3.getY()-point1.getY())*(point3.getY()-point2.getY())<0){
                return -1;
            }
            if (((point4.getY()-point1.getY())*(point4.getY()-point2.getY()))<0){
                return 1;
            }
        }else {
            return 0;//不在视野内，无法推算，返回正常
        }
        return 0;
    }
}
