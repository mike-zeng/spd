package model;
/**
 * 坐姿数据
 * @author zeng
 */
public class SittingPosition {
    private int id;
    private int uid;
    /**
     * 0 正常
     * 1 头左偏
     * 2 头右偏
     * 3 左手错误放置
     * 4 右手错误放置
     * 5 身体倾斜
     * 6 趴桌
     */
    private int status;
    private double degree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "SittingPosition{" +
                "id=" + id +
                ", uid=" + uid +
                ", status=" + status +
                ", degree=" + degree +
                '}';
    }
}
