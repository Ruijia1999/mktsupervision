package fudan.doubleh.mktsupervision.service;

import fudan.doubleh.mktsupervision.Main;
import fudan.doubleh.mktsupervision.pojo.Authority;

import java.util.Scanner;

public class AuthoriyService {



    public static int service() {
        int type=0;
        Scanner scan = new Scanner(System.in);
        AuthoritySetTask authoritySetTask;


        System.out.println("请选择下列业务：\n" +
                "   -3.退出系统\n" +
                "   -2.退出登录\n" +
                "   1.发起任务\n" +
                "   2.查看得分情况\n" +
                "   3.查看农产品合格状况");


        int ret=0;
        do {
            type =  scan.nextInt();
            switch (type){
                case -3: return -3;
                case -2: return -2;
                case 1: while((ret = ((Authority)Main.user).setTasks())==0){} return ret;
                case 2: while((ret = ((Authority)Main.user).checkScore())==0){} return ret;
                case 3: while((ret = ((Authority)Main.user).checkProducts())==0){} return ret;
                default: System.out.println("输入错误，请重新输入");

            }
        } while(type!=1&&type!=2);

        return 0;
    }



}
