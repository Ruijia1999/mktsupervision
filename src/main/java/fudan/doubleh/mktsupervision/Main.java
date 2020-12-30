package fudan.doubleh.mktsupervision;


import fudan.doubleh.mktsupervision.pojo.*;
import fudan.doubleh.mktsupervision.service.*;

import java.io.IOException;
import java.util.Date;

public class Main {


    public static ProfessorPool professorPool=new ProfessorPool();
    public static MarketPool marketPool=new MarketPool();
    public static ProductPool productPool=new ProductPool();

    public static User user;
    public static void main(String[] args) throws IOException {

        professorPool.get(0).addScores(new ScoreRecord(10,"完成"));
        professorPool.get(0).addScores(new ScoreRecord(-10,"完成"));
        marketPool.get(1).addScores(new ScoreRecord(-20,"未完成"));

        int ret=0;

        SystemTime systemTime = new SystemTime(new Date());
        IndicatorService is = new IndicatorService(systemTime,professorPool.getProfessorPool(),marketPool.getMarketPool());
        is.run();//开始计时

        do{
            ret = LogInService.logIn();
            switch (ret){
                case 1: while((ret = AuthoriyService.service())==-1){} break;
                case 2: while((ret = MarketService.service())==-1){} break;
                case 3: while((ret = ProfessorService.service())==-1){} break;

            }
            if(ret==-3) {System.out.println("再见！");return;}

        } while(true);



    }

}