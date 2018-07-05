package bigdata.ngram.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

//one to many predict
public class Gram {

    //fixme: @Autowired get nullPointerException in unittest
//    @Autowired private HbaseTemplate hbaseTemplate;
    ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
    HbaseTemplate hbaseTemplate = (HbaseTemplate) context.getBean("hbaseTemplate");

    @Getter private String history="";
    @Getter private String backoffHistory = "";
    @Getter private int limit=10;
    @Getter private ArrayList<Predict> predicts= new ArrayList<Predict>();
    @Getter private PriorityQueue<RowScanner> rowScanners = new PriorityQueue<RowScanner>((result1, result2)->{
        if(result1.current().getProb()>result2.current().getProb()){
            return -1;
        }else if(result1.current().getProb()<result2.current().getProb()){
            return 1;
        }else{
            return 0;
        }
    });

    public Gram(String history, String backoffHistory, int limit){
        this.history = history;
        this.backoffHistory = backoffHistory;
        setRowScanners(limit);
        setPredicts();

    }

    public Gram(){

    }

    private String history_;
    private void setRowScanners(int limit){
        history_ = this.backoffHistory;
        this.limit=limit;

        RowScanner r1= new RowScanner();
        r1.setScanner(hbaseTemplate.get("order" + (this.backoffHistory.split(" ").length+1), history_,
                "predict",(result, rowNumber) -> result));
        r1.setLambda(1);
        rowScanners.add(r1);

        for (int i  = this.backoffHistory.split(" ").length;i>1;--i){
            RowScanner r = new RowScanner();
            r.setLambda(hbaseTemplate.get("order" + (i+1), history_,
                    (result, rowNumber) -> Double.parseDouble(new String(result.getValue("lambda".getBytes(),"lambda".getBytes())))
            ));
            history_ = history_.substring(history_.indexOf(" ")+1);
            r.setScanner(hbaseTemplate.get("order" + i, history_,"predict",(result, rowNumber) -> result));
            rowScanners.add(r);
        }

        RowScanner r2 = new RowScanner();
        r2.setLambda(hbaseTemplate.get("order2", history_,
                (result, rowNumber) -> Double.parseDouble(new String(result.getValue("lambda".getBytes(),"lambda".getBytes())))
        ));
        r2.setScanner(hbaseTemplate.get("order1", " ","predict",(result, rowNumber) -> result));
        rowScanners.add(r2);

    }


    private void setPredicts() {
        int i=0;
        RowScanner temp;
        Set<String> followings = new HashSet<>();
            System.out.println(rowScanners.peek().current().getFollowing());

        while (i<this.limit){
            temp = rowScanners.peek();
            if (!followings.contains(temp.current().getFollowing())){
                this.predicts.add(temp.current());
                ++i;
            }
            temp = rowScanners.poll();
            if (temp.advance()){
                rowScanners.add(temp);
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
