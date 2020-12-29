package fudan.doubleh.mktsupervision.service;

import fudan.doubleh.mktsupervision.pojo.Market;
import fudan.doubleh.mktsupervision.pojo.Product;
import fudan.doubleh.mktsupervision.pojo.Professor;

import java.util.List;

public class Tools {





    public static Professor getProfessor(List<Professor> professors, int id){
        int size = professors.size();
        for(Professor p: professors){
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }

    public static Market getMarket(List<Market> markets, int id){
        int size = markets.size();
        for(Market m: markets){
            if(m.getId()==id){
                return m;
            }
        }
        return null;
    }


    public static Product getProduct(List<Product> products, int id){
        int size = products.size();
        for(Product p: products){
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }

}
