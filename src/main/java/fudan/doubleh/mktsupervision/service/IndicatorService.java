package fudan.doubleh.mktsupervision.service;

import fudan.doubleh.mktsupervision.pojo.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class IndicatorService {//定时器计分

    SystemTime systemTime;
    Date oldDate;
    private Map<Integer,Professor> professorPool;
    private Map<Integer,Market> marketPool;

    //依赖注入系统date时间
    public IndicatorService(SystemTime systemTime,Map<Integer,Professor> professorPool,Map<Integer,Market> marketPool) {
        this.systemTime = systemTime;
        Date date = systemTime.getDate();
        this.oldDate = new Date(systemTime.getDate().getTime());
        this.professorPool = professorPool;
        this.marketPool = marketPool;
    }

    //更新得分
    public void update(){
        for(Market m: this.marketPool.values()){
            for(Task t: m.getTasks()){
                Date endTime = t.getEndTime();
                int days = calcuDays(endTime,systemTime.getDate());
                if(days==0){//到期
                    if(t.isFinished()){//已完成加分
                        m.addScores(new ScoreRecord(10,"完成"));//
                    }else {//未完成-10
                        m.addScores(new ScoreRecord(-10,"未按时完成"));//
                    }
                }
                if((days==20) && (!t.isFinished())){//超过20天未完成
                    m.addScores(new ScoreRecord(-20,"超过20天未完成"));///...
                }
            }
        }
        for(Professor p: this.professorPool.values()){
            for(Task t: p.getTasks()){
                Date endTime = t.getEndTime();
                int days = calcuDays(endTime,systemTime.getDate());
                if(days==0){//到期
                    if(t.isFinished()){//已完成加分
                        p.addScores(new ScoreRecord(10,"完成"));//
                    }else {//未完成-10
                        p.addScores(new ScoreRecord(-10,"未按时完成"));//
                    }
                }
                if((days==20) && (!t.isFinished())){//超过20天未完成
                    p.addScores(new ScoreRecord(-20,"超过20天未完成"));///...
                }
            }
        }
    }


    //判断时间是否改变
    //运行
    public void run(){
        //每天判断一次
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    int days = calcuDays(oldDate,systemTime.getDate());
                    if(days>0){//若日期改变，则更新一下
                        update();
                        oldDate = new Date(systemTime.getDate().getTime());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },0, 1000*3600*24);
    }

    //计算间隔了多少天
    private int calcuDays(Date startDate,Date endDate){
        int days = -1;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long startDateTime = dateFormat.parse(dateFormat.format(startDate)).getTime();
            long endDateTime = dateFormat.parse(dateFormat.format(endDate)).getTime();
            days = (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

}
