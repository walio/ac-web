package bigdata.ngram.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

//one to many predict
public class Gram {

    @Autowired private HbaseTemplate hbaseTemplate;
    @Getter private String history="";
    @Getter private String backoffHistory = "";
    @Getter private ArrayList<Predict> predicts= new ArrayList<Predict>();

    public Gram(String history, String backoffHistory, int limit){
        this.history = history;
        this.backoffHistory = backoffHistory;
        setPredicts(limit);
    }

    public Gram(){

    }

    private String history_;
    private void setPredicts(int limit) {
        history_ = this.backoffHistory;
        Set<String> followings = new HashSet<>();
        PriorityQueue<RowScanner> priorityQueue = new PriorityQueue<RowScanner>((result1, result2)->{
            if(result1.current().getProb()>result2.current().getProb()){
                return 1;
            }else if(result1.current().getProb()<result2.current().getProb()){
                return -1;
            }else{
                return 0;
            }
        });
        for (int i  = this.backoffHistory.split(" ").length;i>0;--i){
            RowScanner r = new RowScanner();
            hbaseTemplate.get("prob" + i, history_,"prob",(result, rowNumber) -> {
                r.setScanner(result);
                return null;
            });
            hbaseTemplate.get("prob" + i, history_,"lambda",(result, rowNumber) -> {
                r.setLambda(Integer.parseInt(result.getValue("lambda".getBytes(),"lambda".getBytes()).toString()));
                return null;
            });
            priorityQueue.add(r);
            history_ = history_.substring(history_.indexOf(" ")+1);
        }
        int i=0;
        RowScanner temp;
        while (i<limit){
            temp = priorityQueue.peek();
            if (!followings.contains(temp.current().getFollowing())){
                this.predicts.add(temp.current());
                ++i;
            }
            temp = priorityQueue.poll();
            if (temp.advance()){
                priorityQueue.add(temp);
            }
        }

    }

    public Predict[] getPredictsByNumber(int topK) {
        return (Predict[]) predicts.subList(0,topK).toArray();
    }

    public Predict[] getPredictsByProb(double totalProb) {
        ArrayList<Predict> res = new ArrayList<>();
        for (Predict predict:predicts){
            if (totalProb<predict.getProb()){
                break;
            }
            res.add(predict);
            totalProb-=predict.getProb();
        }
        return (Predict[])res.toArray();
    }

}
