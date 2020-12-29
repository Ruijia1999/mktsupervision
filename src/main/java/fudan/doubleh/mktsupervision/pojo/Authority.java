package fudan.doubleh.mktsupervision.pojo;

import fudan.doubleh.mktsupervision.Main;
import fudan.doubleh.mktsupervision.service.AuthoritySetMarketTask;
import fudan.doubleh.mktsupervision.service.AuthoritySetProfessorTask;
import fudan.doubleh.mktsupervision.service.AuthoritySetTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Authority extends User{


    public Authority(int id, String name) {
        super(id, name);
    }

    //查看分数
    public int checkScore(){
        int type = 0;
        Scanner scan = new Scanner(System.in);
        AuthoritySetTask authoritySetTask;



        Main.professorPool.printScore();
        Main.marketPool.printScore();

        do {

            System.out.println("请选择想要进一步查看的人员，输入-1返回上一层");

            type =  scan.nextInt();
            //  System.out.println(type);
            if(type==-1){
                return -1;
            } else if(Main.professorPool.ifContains(type)){
                Main.professorPool.get(type).printScores();
            } else if(Main.marketPool.ifContains(type)){
                Main.marketPool.get(type).printScores();

            }
        } while (true);



    }

    //检查产品合格情况
    public int checkProducts(){
        int type=0;
        int unqualified = 0;
        Date startDate = null;
        Date endDate = null;

        Scanner scan = new Scanner(System.in);

        Main.productPool.print();
        System.out.println("请输入想要选择的农贸产品,输入-1返回上一层");
        do {
            type =  scan.nextInt();
            if(type==-1) {
                return -1;
            }else if(!Main.productPool.ifContains(type)){
                System.out.println("输入错误，请重新输入");
            }else break;
        } while(true);


        System.out.println("请输入开始日期, 例:1997.2.20");
        String startD = scan.nextLine();
        startD = scan.nextLine();
        System.out.println("请输入结束日期, 例:1997.2.20");
        String endD = scan.nextLine();



        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");   //定义日期格式
        try {
            startDate = df.parse(startD);
            endDate = df.parse(endD);
        }catch (Exception e){
            e.printStackTrace();
        }


        for(Professor p: Main.professorPool.getProfessorPool().values()){
            for(Task t: p.tasks){
                for(CheckRecord c: ((ProfessorTask)t).getRecords()){
                    if(c.productId==type&&c.checkTime.after(startDate)&&c.checkTime.before(endDate)&&c.outcome==0){
                        unqualified++;
                    }
                }
            }
        }
        for(Market m: Main.marketPool.getMarketPool().values()){
            for(Task t: m.tasks){
                for(CheckRecord c: ((MarketTask)t).getRecords()){
                    if(c.productId==type&&c.checkTime.after(startDate)&&c.checkTime.before(endDate)&&c.outcome==0){
                        unqualified++;
                    }
                }
            }
        }



        System.out.println("不合格总数为: "+unqualified);
        return 0;
    }

    //下发任务
    public int setTasks(){

        int type = 0;
        Scanner scan = new Scanner(System.in);
        AuthoritySetTask authoritySetTask;

        System.out.println("请选择发布任务类型：\n" +
                "   -3.退出系统\n" +
                "   -2.退出登录\n" +
                "   -1.返回上一层\n" +
                "   1.农贸市场自检\n" +
                "   2.专家抽检");



        do {

            type =  scan.nextInt();
          //  System.out.println(type);
            switch (type) {
                case -3: return -3;
                case -2: return -2;
                case -1:return -1;
                case 1:
                    authoritySetTask = new AuthoritySetMarketTask();
                    authoritySetTask.setTask();
                    return 0;
                case 2:
                    authoritySetTask = new AuthoritySetProfessorTask();
                    authoritySetTask.setTask();
                    return 0;
                default:

                   System.out.println("输入错误，请重新选择");
            }
        } while (true);



    }

}
