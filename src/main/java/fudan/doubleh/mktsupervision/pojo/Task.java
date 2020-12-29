package fudan.doubleh.mktsupervision.pojo;

import java.util.Date;

public class Task {

    int id;
    Date endTime;
    boolean status;

    public Task(int id, Date endTime, boolean status) {
        this.id = id;
        this.endTime = endTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isFinished() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
