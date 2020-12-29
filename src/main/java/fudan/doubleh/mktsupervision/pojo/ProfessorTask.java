package fudan.doubleh.mktsupervision.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfessorTask extends Task {


    List<Market> marketList;

    List<Product> productList;

    List<CheckRecord> records;

    public ProfessorTask(int id, Date endTime, boolean status, List<Market> marketList, List<Product> productList, List<CheckRecord> records) {
        super(id, endTime, status);
        this.marketList = marketList;
        this.productList = productList;
        this.records = records;
    }

    public void addCheckRecord(CheckRecord checkRecord){
        this.records.add(checkRecord);
        if(this.ifFinished()){
            this.status = true;
        }
    }
    public boolean ifFinished(){
        for(Market m: marketList){
            List<Product> pro = new ArrayList<Product>();
            for(Product p: productList){
                boolean ifChecked = false;
                for(CheckRecord c:records) {
                    if (c.productId == p.getId() && c.mktId == m.getId())
                        ifChecked=true;

                }
                if(!ifChecked)
                    return false;

            }
        }
        return true;
    }

    public void print(){
        for(Market m: marketList){
            List<Product> pro = new ArrayList<Product>();
            for(Product p: productList){
                boolean ifchecked = false;
                for(CheckRecord c:records){
                    if(c.productId==p.getId()&&c.mktId==m.getId())
                        ifchecked=true;

                }
                if(!ifchecked)
                    pro.add(p);

            }
            if(pro.size()!=0){
                System.out.print("  "+m.getId()+": "+m.name+" ( ");
                for(Product p:pro){
                    System.out.print(p.getId()+":"+p.getName()+" ");
                }
                System.out.println(")");
            }

        }

    }

    public boolean isFinished(){
        return status;}

    public List<Market> getMarketList() {
        return marketList;
    }

    public void setMarketList(List<Market> marketList) {
        this.marketList = marketList;
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
}
