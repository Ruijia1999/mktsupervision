package fudan.doubleh.mktsupervision.pojo;

public class ScoreRecord {
    int score;
    String reason;
    int taskId;

    public ScoreRecord(int score, String reason) {
        this.score = score;
        this.reason = reason;
    }

    public void print(){
        System.out.println("    在 "+taskId+" 号任务中得分 "+score+", 原因: "+reason);
    }
}
