package service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

        UnderarmData underarmData=new UnderarmData();
        BodySlant bodySlant=new BodySlant();
        HandPosition handPosition=new HandPosition();
        HeadSlant headSlant=new HeadSlant();

        if (upperBodyInfo!=null){
            //1. 获取是否趴桌
            underarmData=analysisUnderarm(upperBodyInfo);
            //2. 身体是否倾斜
            bodySlant=analysisBodySlant(upperBodyInfo);
            //4. 获取手部位置
            handPosition=analysisHandPosition(upperBodyInfo);
        }
        if (headInfo!=null){
            //3. 获取头部偏离
            headSlant=analysisHeadSlantData(headInfo);
        }




        //获取标志性错误(错误最大最明显)
        if (underarmData.isUnderarm){
            sittingPosition.setStatus(6);
            sittingPosition.setDegree(underarmData.getDegree());
            return sittingPosition;
        }else if (bodySlant.isBodySlant){
            sittingPosition.setStatus(5);
            sittingPosition.setDegree(bodySlant.getDegree());
            return sittingPosition;
        }else if (headSlant.isSlant){
            if (headSlant.getDirection()==Direction.LEFT){
                sittingPosition.setStatus(1);
                sittingPosition.setDegree(1);
                return sittingPosition;
            }else {
                sittingPosition.setStatus(2);
                sittingPosition.setDegree(1);
                return sittingPosition;
            }
        }else {
            sittingPosition.setStatus(0);
            sittingPosition.setDegree(0);
            return sittingPosition;
        }
    }

    /**
     * 百度返回的数据中包含了人脸3d角度的偏移，可以直接通过参数判断头部是否偏了
     * 判断头部偏离系数，范围为[0,1],当返回值为0时，表示头部未发生偏离，
     * @param headInfo
     * @return 偏离系数
     */
    private static HeadSlant analysisHeadSlantData(HeadInfo headInfo){
        Double rotation=headInfo.getLocation().getRotation();
        int flag=rotation>0?1:-1;
        rotation=Math.abs(rotation);

        //判断是否在合理的范围内
        if(rotation<=SittingPositionParam.HEAD_LEFT_ROTATION){
            return new HeadSlant(false,Direction.NO,0);//没有偏离
        }

        HeadSlant slantData=new HeadSlant();
        slantData.setDegree(rotation*(1.0/78)-(12.0/78));
        slantData.setSlant(true);
        if (flag==1){
            slantData.setDirection(Direction.LEFT);
        }else {
            slantData.setDirection(Direction.RIGHT);
        }
        return slantData;
    }


    /**
     * 判断是否趴下
     *  人体区域高度和人体区域宽度的比值小于常数k
     * @param upperBodyInfo
     * @return
     */
    private static UnderarmData analysisUnderarm(UpperBodyInfo upperBodyInfo){
        double bodyWidth=upperBodyInfo.getBodyWidth();
        double bodyHeight=upperBodyInfo.getBodyHeight();

        //利用宽度和高度比
        if (bodyWidth==0){
            return new UnderarmData(false,0);
        }
        double ratio=bodyHeight/bodyWidth;
        if (ratio<0.5){
            if (ratio<0.1){
                return new UnderarmData(false,0);
            }
            double degree=-1/0.4*ratio+1/4;
            return new UnderarmData(true,degree);
        }else {
            return new UnderarmData(false,0);
        }
    }

    /**
     * 判断身体是否倾斜
     * 根据论文中训练的模型表示，下面这种情况可以视为身体倾斜
     * 左偏 L2<80度
     * 右偏 L2>100度
     *
     */

    private static BodySlant analysisBodySlant(UpperBodyInfo upperBodyInfo){
        Point p1=upperBodyInfo.getRightShoulder();//左肩位置
        Point p2=upperBodyInfo.getNeck();//脖子位置

        double tan2=(p1.getY()-p2.getY())/(p1.getX()-p2.getX());
        tan2=Math.abs(tan2);
        double l2=0;
        if (p1.getY()<p2.getY()){
            l2=90.0+Math.atan(tan2)/Math.PI*180;
        }else {
            l2=90.0-Math.atan(tan2)/Math.PI*180;
        }

        double cha=Math.abs(l2-100);
        if (cha>=10){
            double degree=1.0/35.0*cha-2.0/7.0;
            return new BodySlant(true,degree);
        }else {
            return new BodySlant(false,0);
        }
    }

    /**
     * 判断手部位置
     * @param upperBodyInfo
     * @return = 0 正常
     *         = 1
     *         = -1
     */
    private static HandPosition analysisHandPosition(UpperBodyInfo upperBodyInfo){
        Point point1=upperBodyInfo.getNose();//鼻子
        Point point2=upperBodyInfo.getNeck();//脖子
        Point point3=upperBodyInfo.getLeftElbow();//左手腕
        Point point4=upperBodyInfo.getRightElbow();//右手腕

        if (point1!=null&&point2!=null&&point3!=null&&point4!=null){
            if ((point3.getY()-point1.getY())*(point3.getY()-point2.getY())<0){
                return new HandPosition(true,Direction.LEFT);
            }
            if (((point4.getY()-point1.getY())*(point4.getY()-point2.getY()))<0){
                return new HandPosition(true,Direction.RIGHT);
            }
        }
        return new HandPosition(false,Direction.NO);
    }

    //--------------------对检测结果的封装-------------------------------------------

    //方向
    enum Direction{
        LEFT,RIGHT,NO;
    }

    //1.头偏离
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class HeadSlant{
        private boolean isSlant;//是否偏离
        private Direction direction;//偏离方向
        private double degree;//偏离程度

    }

    //2.趴下
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class UnderarmData{
        private boolean isUnderarm;
        private double degree;
    }

    //3.身体倾斜
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class BodySlant{
        private boolean isBodySlant;
        private double degree;
    }

    //4.手部位置
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class HandPosition{
        private boolean isError;
        private Direction direction;
    }
}
