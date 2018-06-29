package bigdata.ngram.dao.impl;

import bigdata.ngram.dao.GramDao;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import java.sql.ResultSet;


public class implGramDao implements GramDao {

    @Autowired private HbaseTemplate hbaseTemplate;
    @Value("${highestOrder}") private int highestOrder;
    private int ngramNumber=-1;
    private String backoffHistory ="";
    private double[] lambdas;



    public implGramDao(String history){
        this.setBackoffHistory(history);
    }


    private String history_;
    public void setBackoffHistory(String history) {
        history_ = history.trim().replaceAll("\\s+"," ");
        int i=history_.split(" ").length;
        while(this.backoffHistory =="" && history_!=null){
            this.backoffHistory = hbaseTemplate.get("prob"+i,history_, (result, rowNumber)->history_);
            history_ = history_.substring(history_.indexOf(" ")+1);
            --i;
        }
        this.ngramNumber=this.backoffHistory.split(" ").length+1;


    }

    public void setLambdas(String backoffHistory) {
        if (this.ngramNumber==-1){
            return;
        }
        lambdas = new double[this.ngramNumber-1];
        for (int i=0;i<this.ngramNumber;++i){
            lambdas[i] = Double.parseDouble(
                    hbaseTemplate.get(
                            "prob"+i,backoffHistory,"lambda","lambda",(result, i1) -> result
                    ).value().toString()
            );
        }
    }

    @Override
    public String[] getTopByNumber(int topK) {
        if (this.ngramNumber==-1){
            return null;
        }
        String[] res = new String[topK];
        for(int i=0;i<topK;++i){
            res
        }
    }

    @Override
    public String[] getTopByProb(double totalProb,int limit) {
        return new String[]{"adfadsf"};
    }
}
