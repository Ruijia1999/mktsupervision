package fudan.doubleh.mktsupervision.pojo;

import fudan.doubleh.mktsupervision.dao.Dao;

import java.util.Map;

public class ProfessorPool {

    private Map<Integer,Professor> professorPool=Dao.getProfessors();

    public  String getName(int id){
        return professorPool.get(id).getName();
    }
    public Professor get(int id){
        return professorPool.get(id);
    }

    public  boolean ifContains(int id){
        return professorPool.containsKey(id);
    }

    public Map<Integer, Professor> getProfessorPool() {
        return professorPool;
    }

    public void print(){
        System.out.println("专家列表:");
        for(Professor p: this.professorPool.values()){

            System.out.println("    "+p.getId()+" : "+p.getName());
        }
    }
    public void printScore(){
        System.out.println("专家列表:");
        for(Professor p: this.professorPool.values()){

            System.out.println("    "+p.getId()+" : "+p.getName()+" "+p.score);
        }
    }
}
