package fudan.doubleh.mktsupervision.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MarketTask extends Task{


    List<Product> productList;

    List<CheckRecord> records=new ArrayList<CheckRecord>();


    public MarketTask(int id, Date endTime, boolean status) {
        super(id, endTime, status);
        this.productList = new ArrayList<Product>();
        this.records=new ArrayList<CheckRecord>();
    }

    public MarketTask(int id, Date endTime, boolean status, List<Product> productList, List<CheckRecord> records) {
        super(id, endTime, status);
        this.productList = productList;
        this.records = records;
    }

    public void addCheckRecord(CheckRecord checkRecord){
        this.records.add(checkRecord);
        if(this.ifFinished()){
            this.status = true;
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<CheckRecord> getRecords() {
        return records;
    }

    public void setRecords(List<CheckRecord> records) {
        this.records = records;
    }

    public void print(){
        System.out.print("( ");
        for(Product p: productList){
            boolean ifchecked = false;
            for(CheckRecord c:records){
                if(c.productId==p.id){
                    ifchecked=true;
                    break;
                }
            }

            if(ifchecked==false){
                System.out.print(p.getId()+":"+p.name+" ");
            }
        }
        System.out.print(") ");
    }

    public boolean ifFinished(){
        for(Product p: productList){
            boolean ifchecked = false;
            for(CheckRecord c:records){
                if(c.productId==p.id){
                    ifchecked=true;
                    break;
                }
            }
            if(ifchecked==false){
                return false;
            }
        }
        return true;
    }
}
