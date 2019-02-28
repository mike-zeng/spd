package model;
/**
 * 坐姿数据
 * @author zeng
 */
public class SittingPosition {
    int id;
    int uid;
    /**
     * 0 正常
     * 1 头左偏
     * 2 头右偏
     * 3 左手错误放置
     * 4 右手错误放置
     * 5 身体倾斜
     * 6 趴桌
     */
    int status;

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

    @Override
    public String toString() {
        return "SittingPosition{" +
                "id=" + id +
                ", uid=" + uid +
                ", statu=" + status +
                '}';
    }
}
