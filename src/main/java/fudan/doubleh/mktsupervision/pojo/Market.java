package fudan.doubleh.mktsupervision.pojo;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Market extends User{

    int score;

    List<ScoreRecord> scores;
    List<Task> tasks;

    //查看待完成任务
    public void checkTasks(){

        SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd");


        System.out.println(this.getName()+"待完成任务列表：");
        for(Task t:tasks){
            if(t.status==false) {
                System.out.print(t.getId() + " " + ft.format(t.getEndTime()) + " ");
                ((MarketTask) t).print();
            }

        }
        System.out.println();
        System.out.println(this.getName()+"待完成任务列表打印完毕");

    }

    //处理任务
    public void dealTasks(){



        int type = 0;
        Scanner scan = new Scanner(System.in);

        do {
            this.checkTasks();
            System.out.println("请输入id选择待完成任务进行处理, 输入-1返回上一层");
            type =  scan.nextInt();
            if(type>=0&&type<tasks.size()&&!tasks.get(type).isFinished()){
                addCheckRecord(tasks.get(type));
            } else if(type==-1)
                return;
            else{System.out.println("输入错误，请重新选择");}

        } while (true);

    }


    private void addCheckRecord(Task task){
        int proId = 0;
        int outcome = 0;
        Scanner scan = new Scanner(System.in);

        do {
            ((MarketTask)task).print();
            System.out.println("请输入产品id, 输入-1退出");
            proId =  scan.nextInt();
            if(proId==-1)
                return;
            System.out.println("请输入检查结果: 0.不合格  1.合格");
            outcome = scan.nextInt();

            Date date = null;
            System.out.println("请输入抽检日期, 例:1997.2.20");
            String d = scan.nextLine();
            d = scan.nextLine();

            DateFormat df = new SimpleDateFormat("yyyy.MM.dd");   //定义日期格式
            try {
                date = df.parse(d);
            }catch (Exception e){
                e.printStackTrace();
            }

//            ((MarketTask)task).addCheckRecord(new CheckRecord(this.getId(),proId,outcome, date));

            addCheckRecordByData(task,this.getId(),proId,outcome,date);
            if((task).isFinished()) {
//                System.out.println("该任务已完成");
                return;
            }
        } while (true);

    }

    public void addCheckRecordByData(Task task,Integer marketID,Integer proId,int outcome,Date date){
        ((MarketTask)task).addCheckRecord(new CheckRecord(marketID,proId,outcome, date));
        if((task).isFinished()) {
            System.out.println("该任务已完成");
        }
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }
    //打印分数具体信息
    public void printScores(){
        System.out.println(this.getName()+"得扣分列表：");
        for(ScoreRecord s:scores){
            s.print();
        }
        System.out.println(this.getName()+"得扣分列表打印完毕");
    }

    public void addScores(ScoreRecord scoreRecord){
        this.scores.add(scoreRecord);
        this.score+=scoreRecord.score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<ScoreRecord> getScores() {
        return scores;
    }

    public void setScores(List<ScoreRecord> scores) {
        this.scores = scores;
    }




    public Market(int id, String name) {
        super(id, name);
        this.score = 0;
        this.tasks=new ArrayList<Task>();
        this.scores=new ArrayList<ScoreRecord>();
    }


}
