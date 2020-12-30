package fudan.doubleh.mktsupervision.service;

import fudan.doubleh.mktsupervision.pojo.MarketPool;
import fudan.doubleh.mktsupervision.pojo.Product;
import fudan.doubleh.mktsupervision.pojo.ProfessorPool;

import java.util.Date;
import java.util.List;

public interface AuthoritySetTask {
    public void setTask();
    public void setTaskByData(List<Integer> markets, Integer professor, List<Product> products, Date date, MarketPool marketPool,ProfessorPool professorPool);
}
