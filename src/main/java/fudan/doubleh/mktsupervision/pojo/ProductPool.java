package fudan.doubleh.mktsupervision.pojo;

import fudan.doubleh.mktsupervision.dao.Dao;

import java.util.Map;

public class ProductPool {
    private Map<Integer,Product> productPool=Dao.getProducts();

    public  String getName(int id){
        return productPool.get(id).getName();
    }
    public Product get(int id){
        return productPool.get(id);
    }

    public boolean ifContains(int id){
        return productPool.containsKey(id);
    }

    public void print(){

        System.out.println("农贸产品列表:");
        for(Product p: this.productPool.values()){
            System.out.println("    "+p.getId()+" : "+p.getName());
        }
    }
}
