package fudan.doubleh.mktsupervision.pojo;

import fudan.doubleh.mktsupervision.dao.Dao;

import java.util.Map;

public class MarketPool {

    private Map<Integer,Market> marketPool= Dao.getMarkets();
    int size;


    public String getName(int id){
        return marketPool.get(id).getName();
    }
    public Market get(int id){
        return marketPool.get(id);
    }

    public boolean ifContains(int id){
        return marketPool.containsKey(id);
    }

    public Map<Integer, Market> getMarketPool() {
        return marketPool;
    }

    public void print(){
        System.out.println("农贸市场列表:");
        for(Market m: this.marketPool.values()){
            System.out.println("    "+m.getId()+" : "+m.getName());
        }
    }
    public void printScore(){
        System.out.println("农贸市场列表:");
        for(Market m: this.marketPool.values()){
            System.out.println("    "+m.getId()+" : "+m.getName()+" "+m.getScore());
        }
    }

}
