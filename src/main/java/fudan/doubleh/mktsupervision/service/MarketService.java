package fudan.doubleh.mktsupervision.service;

import fudan.doubleh.mktsupervision.Main;
import fudan.doubleh.mktsupervision.pojo.Market;

import java.util.Scanner;

public class MarketService {
    public static int service() {
        int type=0;
        Scanner scan = new Scanner(System.in);


        System.out.println("请选择下列业务：\n" +
                "   -3.退出系统\n" +
                "   -2.退出登录\n" +
                "   1.查看待完成任务\n" +
                "   2.处理任务");
        type =  scan.nextInt();
        int ret = 0;
        do {
            switch (type){
                case -3: return -3;
                case -2: return -2;
                case 1: ((Market)Main.user).checkTasks();return -1;
                case 2: ((Market)Main.user).dealTasks(); return -1;
                default: System.out.println("输入错误，请重新输入");
                    type =  scan.nextInt();
            }
        } while(true);

    }
}
