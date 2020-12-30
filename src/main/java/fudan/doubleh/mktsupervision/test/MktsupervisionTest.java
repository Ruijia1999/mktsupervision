package fudan.doubleh.mktsupervision.test;

import fudan.doubleh.mktsupervision.pojo.*;
import fudan.doubleh.mktsupervision.service.AuthoritySetMarketTask;
import fudan.doubleh.mktsupervision.service.AuthoritySetProfessorTask;
import fudan.doubleh.mktsupervision.service.AuthoritySetTask;
import fudan.doubleh.mktsupervision.service.IndicatorService;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

public class MktsupervisionTest {
    public static ProfessorPool professorPool=new ProfessorPool();
    public static MarketPool marketPool=new MarketPool();
    public static ProductPool productPool=new ProductPool();

    public static User user;

    @Test //监管局给一组农贸市场发起监管任务，农贸市场查看待完成任务，并完成抽检任务。
    public void test1(){
        //监管局给一组农贸市场发起监管任务
        user = new Authority(1,"监管局");
        AuthoritySetTask authoritySetTask = new AuthoritySetMarketTask();//authoritySetTask = new AuthoritySetProfessorTask();
        //任务数据：市场（1:罗咸阳 4：贺曦）；农贸产品（0 : 蔬菜，1 : 水果，2 : 肉禽，3 : 海鲜）
        List<Integer> markets = new ArrayList<Integer>();
        List<Product> products = new ArrayList<Product>();
        markets.add(1);
        markets.add(4);
        products.add(productPool.get(1));
        products.add(productPool.get(2));
        //设置截止日期
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = df.parse("2020.1.2");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //发起监管任务：市场（1:罗咸阳 4：贺曦）；农贸产品（1 : 水果，2 : 肉禽）
        authoritySetTask.setTaskByData(markets,null,products,date,marketPool,null);

        //这组农贸市场查看待完成任务（并测试是否有日期和农贸产品都正确的任务）
        Integer taskId=-1;
        for(Integer marketID: markets){
            int userId = marketID;//
            user = marketPool.get(userId);
            ((Market)user).checkTasks();
            List<Task> tasks = ((Market)user).getTasks();
            boolean isExist = false;
            if(userId==1){
                taskId = findTaskID(tasks,date,products);
            }
            if(taskId>=0){
                isExist = true;
            }
            assertTrue(isExist);
        }

        //完成抽检任务（测试1：罗 是否完成）
        user = marketPool.get(1);
        for(Product product:products){
            ((Market)user).addCheckRecordByData(((Market)user).getTasks().get(taskId),1,product.getId(),1,date);
        }
        assertTrue(((Market)user).getTasks().get(taskId).isFinished());
    }

    @Test //监管局给一组专家发起监管任务，专家查看待完成任务，并完成抽检任务。
    public void test2(){
        user = new Authority(1,"监管局");
        AuthoritySetTask authoritySetTask = new AuthoritySetProfessorTask();
        //任务数据：专家（0 : 黄睿佳，3 : 陈国华）；市场（1:罗咸阳 4：贺曦）；农贸产品（0 : 蔬菜，1 : 水果，2 : 肉禽，3 : 海鲜）
        List<Integer> markets = new ArrayList<Integer>();
        List<Product> products = new ArrayList<Product>();
        markets.add(1);
        products.add(productPool.get(0));
        products.add(productPool.get(3));
        //设置截止日期
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Date date = null;
        try {
            date = df.parse("2020.1.3");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //发起监管任务：专家（0 : 黄睿佳）；市场（1:罗咸阳 ）；农贸产品（0 : 蔬菜，3 : 海鲜）
        authoritySetTask.setTaskByData(markets,0,products,date,marketPool,professorPool);

        //专家查看待完成任务（并测试是否有日期和农贸产品都正确的任务）
        user = professorPool.get(0);
        ((Professor)user).checkTasks();
        List<Task> tasks = ((Professor)user).getTasks();
        Integer taskId=-1;
        boolean isExist = false;
        for(Task task:tasks){
            if(task.getEndTime().equals(date)){//相同日期
                isExist =true;//你中有我，我中有你，两个农产品清单相同
                for(Product product:((ProfessorTask)task).getProductList()){
                    if(!products.contains(product)){
                        isExist =false;
                        break;
                    }
                }
                for(Product product:products){
                    if(!((ProfessorTask)task).getProductList().contains(product)){
                        isExist = false;
                        break;
                    }
                }
                if(isExist){//找到相同日期和同农产品清单的任务
                    taskId = task.getId();
                    break;
                }
            }
        }
        assertTrue(isExist);

        //完成抽检任务（测试 是否完成）
        try {
            date = df.parse("2019.12.22");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(Product product:products){
            ((Professor)user).addCheckRecordByData(((Professor)user).getTasks().get(taskId),1,product.getId(),0,date);
        }
        assertTrue(((Professor)user).getTasks().get(taskId).isFinished());
    }

    @Test //监管局查看某个农贸产品类别在某个时间范围内的总的不合格数（时间以抽检日期为准）  需要先跑test2（其中报告了一个不合格）
    public void test3(){
        user = new Authority(1,"监管局");

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = df.parse("2019.12.21");
            endDate = df.parse("2020.1.2");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int n = ((Authority)user).checkProductsByData(0,startDate,endDate,professorPool,marketPool);
        assertEquals(1,n);
    }

    @Test //验证专家和农贸市场按时完成和未按时完成的抽检的场景，获取评估总得分和评估得/扣分的记录。测试代码中可以模拟定时器调用IndicatorService.update，需要设计控制系统时间的方法。
    public void test4(){
        //设置几个重要的时间节点
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Date ddlDate = null;
        Date nowDate = null;
        Date wellFinishDate = null;
        Date delay1Date = null;
        Date future20Date = null;
        try {
            ddlDate = df.parse("2077.1.1");
            nowDate = df.parse("2076.12.25");
            wellFinishDate = df.parse("2076.12.26");
            delay1Date = df.parse("2077.1.2");
            future20Date = df.parse("2077.1.21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //设置系统时间
        SystemTime systemTime = new SystemTime(nowDate);
        IndicatorService indicatorService = new IndicatorService(systemTime,professorPool.getProfessorPool(),marketPool.getMarketPool());

        //设置所有专家和所有农贸市场检查农产品0号
        List<Product> products = new ArrayList<Product>();
        products.add(productPool.get(0));
        setTask(ddlDate,products);

        //按时完成抽检任务（市场4：贺曦）  1：罗 不完成
        user = marketPool.get(4);
        int taskId = findTaskID(((Market)user).getTasks(),ddlDate,products);
        for(Product product:products){
            ((Market)user).addCheckRecordByData(((Market)user).getTasks().get(taskId),4,product.getId(),1,wellFinishDate);
        }
        //按时完成抽检任务(专家0 : 黄睿佳)
        user = professorPool.get(0);
        taskId = findPTaskID(((Professor)user).getTasks(),ddlDate,products);
        for(Product product:products){
            ((Professor)user).addCheckRecordByData(((Professor)user).getTasks().get(taskId),1,product.getId(),0,wellFinishDate);
        }

        //模拟时间流动
        systemTime.setDate(ddlDate);
        indicatorService.update();

        //超时1天完成抽检任务（专家3 : 陈国华）
        user = professorPool.get(3);
        taskId = findPTaskID(((Professor)user).getTasks(),ddlDate,products);
        for(Product product:products){
            ((Professor)user).addCheckRecordByData(((Professor)user).getTasks().get(taskId),1,product.getId(),0,delay1Date);
        }

        //模拟时间流动
        systemTime.setDate(delay1Date);
        indicatorService.update();
        systemTime.setDate(future20Date);
        indicatorService.update();

        //获取评估总得分
        int productID= 0;//0:蔬菜
        user = new Authority(1,"监管局");
        //总评分
        professorPool.printScore();
        marketPool.printScore();

        assertEquals(10,professorPool.get(0).getScore());
        assertEquals(-10,professorPool.get(3).getScore());
        assertEquals(-30,marketPool.get(1).getScore());
        assertEquals(10,marketPool.get(4).getScore());

        //评估得/扣分的记录
        professorPool.get(0).printScores();
        professorPool.get(3).printScores();
        marketPool.get(1).printScores();
        marketPool.get(4).printScores();
    }

    private void setTask(Date ddlDate,List<Product> products){
        AuthoritySetTask authoritySetTask = new AuthoritySetMarketTask();
        List<Integer> markets = new ArrayList<Integer>();
        markets.add(1);
        markets.add(4);
        //发起监管任务：市场（1:罗咸阳 4：贺曦）；农贸产品（0 : 蔬菜）
        authoritySetTask.setTaskByData(markets,null,products,ddlDate,marketPool,null);
        AuthoritySetTask authoritySetTaskP = new AuthoritySetProfessorTask();
        List<Integer> marketsP = new ArrayList<Integer>();
        markets.add(1);
        //发起监管任务：专家（0 : 黄睿佳，3 : 陈国华）；市场（1:罗咸阳 ）；农贸产品（0 : 蔬菜）
        authoritySetTaskP.setTaskByData(marketsP,0,products,ddlDate,marketPool,professorPool);
        authoritySetTaskP.setTaskByData(marketsP,3,products,ddlDate,marketPool,professorPool);
    }

    private int findTaskID(List<Task> tasks,Date date,List<Product> products){
        boolean isExist =false;

        for(Task task:tasks){
            if(task.getEndTime().equals(date)){//相同日期
                isExist =true;//你中有我，我中有你，两个农产品清单相同
                for(Product product:((MarketTask)task).getProductList()){
                    if(!products.contains(product)){
                        isExist =false;
                        break;
                    }
                }
                for(Product product:products){
                    if(!((MarketTask)task).getProductList().contains(product)){
                        isExist = false;
                        break;
                    }
                }
                if(isExist){//找到相同日期和同农产品清单的任务
                    return task.getId();
                }
            }
        }
        return -1;
    }

    private int findPTaskID(List<Task> tasks,Date date,List<Product> products){
        boolean isExist =false;

        for(Task task:tasks){
            if(task.getEndTime().equals(date)){//相同日期
                isExist =true;//你中有我，我中有你，两个农产品清单相同
                for(Product product:((ProfessorTask)task).getProductList()){
                    if(!products.contains(product)){
                        isExist =false;
                        break;
                    }
                }
                for(Product product:products){
                    if(!((ProfessorTask)task).getProductList().contains(product)){
                        isExist = false;
                        break;
                    }
                }
                if(isExist){//找到相同日期和同农产品清单的任务
                    return task.getId();
                }
            }
        }
        return -1;
    }
}