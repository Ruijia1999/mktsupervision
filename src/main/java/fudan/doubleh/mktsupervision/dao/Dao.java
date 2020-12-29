package fudan.doubleh.mktsupervision.dao;


import fudan.doubleh.mktsupervision.pojo.Market;
import fudan.doubleh.mktsupervision.pojo.Product;
import fudan.doubleh.mktsupervision.pojo.Professor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dao {

    public static Map<Integer,Professor> getProfessors(){


        SqlSession session=null;

        try{


        // TODO Auto-generated method stub
            String resource = "SqlMapConfig.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(reader);

            session = ssf.openSession();

            List<Professor> professors = session.selectList("selectAllProfessors");
            System.out.println("Successfully get professor list");


            Map<Integer, Professor> ret = new HashMap<Integer, Professor>();
            for(Professor p:professors){
                ret.put(p.getId(), new Professor(p.getId(),p.getName()));
            }

            return ret;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public static Map<Integer, Market> getMarkets(){
        SqlSession session=null;

        try{


            // TODO Auto-generated method stub
            String resource = "SqlMapConfig.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(reader);

            session = ssf.openSession();

            List<Market> markets = session.selectList("selectAllMarkets");
            System.out.println("Successfully get market list");


            Map<Integer, Market> ret = new HashMap<Integer, Market>();


            for(Market m: markets){
                ret.put(m.getId(),new Market(m.getId(),m.getName()));
            }

            return ret;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public static Map<Integer,Product> getProducts(){
        SqlSession session=null;

        try{


            // TODO Auto-generated method stub
            String resource = "SqlMapConfig.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(reader);

            session = ssf.openSession();

            List<Product> products = session.selectList("selectAllProducts");
            System.out.println("Successfully get product list");

            Map<Integer, Product> ret = new HashMap<Integer, Product>();
            for(Product p:products){
                ret.put(p.getId(), p);
            }
            return ret;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    };
}
