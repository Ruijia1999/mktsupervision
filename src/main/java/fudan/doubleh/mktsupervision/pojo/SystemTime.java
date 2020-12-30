package fudan.doubleh.mktsupervision.pojo;

import java.util.Date;

public class SystemTime {
    Date date;

    public SystemTime(Date date){
        this.date = date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void update(){
        //实时更新系统时间，不需要实现
    }
}
