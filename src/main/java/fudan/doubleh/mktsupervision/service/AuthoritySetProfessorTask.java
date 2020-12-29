package fudan.doubleh.mktsupervision.service;

import fudan.doubleh.mktsupervision.Main;
import fudan.doubleh.mktsupervision.pojo.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AuthoritySetProfessorTask implements AuthoritySetTask{
    public void setTask(){

        int type=0;

        Scanner scan = new Scanner(System.in);

        List<Market> markets = new ArrayList<Market>();
        List<Product> products = new ArrayList<Product>();

        int professor = -1;

        Main.professorPool.print();
        System.out.println("请输入想要选择的专家");


        do {
            type =  scan.nextInt();
            if(Main.professorPool.ifContains(type)) {
                professor=type;
                break;
            }else {
                System.out.println("输入错误，请重新输入");
            }
        } while(true);

        Main.marketPool.print();
        System.out.println("请输入想要选择的农贸市场,输入-1完成输入");


        do {
            type =  scan.nextInt();
            if(type==-1) {
                break;
            }else if(Main.marketPool.ifContains(type)) {
                markets.add(Main.marketPool.get(type));
            }else {
                System.out.println("输入错误，请重新输入");
            }
        } while(true);


        Main.productPool.print();
        System.out.println("请输入想要选择的农贸产品,输入-1完成输入");
        do {
            type =  scan.nextInt();
            if(type==-1) {
                break;
            }else if(Main.productPool.ifContains(type)) {
                products.add(Main.productPool.get(type));
            }else {
                System.out.println("输入错误，请重新输入");
            }
        } while(true);


        Main.productPool.print();
        Date date = null;
        System.out.println("请输入截止日期,格式按照:1997.2.20");
        String d = scan.nextLine();
        d = scan.nextLine();
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");   //定义日期格式
        try {
            date = df.parse(d);
        }catch (Exception e){
            e.printStackTrace();
        }

        Task professorTask = new ProfessorTask(Main.professorPool.get(professor).getTasks().size(),date, false, markets, products,new ArrayList<CheckRecord>());
        Main.professorPool.get(professor).addTask(professorTask);


        System.out.println("已成功布置专家抽查任务,返回上一层\n");
    }
}
