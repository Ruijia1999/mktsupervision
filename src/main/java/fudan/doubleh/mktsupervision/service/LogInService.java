package fudan.doubleh.mktsupervision.service;

import fudan.doubleh.mktsupervision.Main;
import fudan.doubleh.mktsupervision.pojo.Authority;
import fudan.doubleh.mktsupervision.pojo.Market;
import fudan.doubleh.mktsupervision.pojo.Professor;

import java.util.Scanner;

public class LogInService {
    public static int logIn(){

        int type=0;
        int userId=0;
        Scanner scan = new Scanner(System.in);

        System.out.println();
        System.out.println("请选择下列身份进行登陆：\n" +
                "   -3.退出系统\n" +
                "   1.监管局工作人员\n" +
                "   2.农贸市场负责人\n" +
                "   3.专家");

        int ret = 0;
        do{
            type =  scan.nextInt();
            switch (type) {
                case -3:return -3;
                case 1:
                    Main.user = new Authority(1,"监管局");
                    System.out.println(((Authority)Main.user).getName()+", 欢迎!");
                    return type;
                case 2:
                    System.out.println("请输入要登陆的用户ID：\n");
                    Main.marketPool.print();
                    userId = scan.nextInt();
                    Main.user = Main.marketPool.get(userId);
                    System.out.println(((Market)Main.user).getName()+", 欢迎!");
                    return type;
                case 3:
                    System.out.println("请输入要登陆的用户ID：\n");
                    Main.professorPool.print();
                    userId = scan.nextInt();
                    Main.user = Main.professorPool.get(userId);
                    System.out.println(((Professor)Main.user).getName()+", 欢迎!");
                    return type;
                default:
                    System.out.println("输入错误，请重新选择");
            }

        } while (true);

    }

}
