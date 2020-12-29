package fudan.doubleh.mktsupervision.service;

import fudan.doubleh.mktsupervision.Main;
import fudan.doubleh.mktsupervision.pojo.CheckRecord;
import fudan.doubleh.mktsupervision.pojo.MarketTask;
import fudan.doubleh.mktsupervision.pojo.Product;
import fudan.doubleh.mktsupervision.pojo.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AuthoritySetMarketTask implements AuthoritySetTask{
    public void setTask(){

        int type=0;

        Scanner scan = new Scanner(System.in);

        List<Integer> markets = new ArrayList<Integer>();
        List<Product> products = new ArrayList<Product>();

        Main.marketPool.print();
        System.out.println("请输入想要选择的农贸市场,输入-1完成输入");


        do {
            type =  scan.nextInt();
            if(type==-1) {
                break;
            }else if(Main.marketPool.ifContains(type)) {
                markets.add(type);
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



        Date date = null;
        System.out.println("请输入截止日期, 例:1997.2.20");
        String d = scan.nextLine();
        d = scan.nextLine();

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");   //定义日期格式
        try {
            date = df.parse(d);
        }catch (Exception e){
            e.printStackTrace();
        }


        for(int m: markets){
            Task marketTask = new MarketTask(Main.marketPool.get(m).getTasks().size(),date, false, products, new ArrayList<CheckRecord>());
            Main.marketPool.get(m).addTask(marketTask);

        }

        System.out.println("已成功布置农贸市场自检任务,返回上一层\n");


    }
}
