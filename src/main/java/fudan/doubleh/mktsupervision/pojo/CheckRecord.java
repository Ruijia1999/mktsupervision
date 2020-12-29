package fudan.doubleh.mktsupervision.pojo;

import java.util.Date;

public class CheckRecord {

    int mktId;
    int productId;
    int outcome;
    Date checkTime;



    public CheckRecord(int mktId, int productId, int outcome, Date checkTime) {
        this.mktId = mktId;
        this.productId = productId;
        this.outcome = outcome;
        this.checkTime = checkTime;
    }

}
