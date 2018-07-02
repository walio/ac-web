package bigdata.ngram.dao.impl;

import bigdata.ngram.dao.GramDao;
import bigdata.ngram.model.Predict;
import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import java.sql.ResultSet;
import java.util.ArrayList;


public class implGramDao implements GramDao {

    @Autowired private HbaseTemplate hbaseTemplate;
    @Getter private String backoffHistory ="";
    @Getter private Predict[] predicts=new Predict[0];


    private String history_;
    public void setBackoffHistory(String history) {
        history_ = history.trim().replaceAll("\\s+", " ");
        int i = history_.split(" ").length;
        while (this.backoffHistory == "" && history_ != null) {
            this.backoffHistory = hbaseTemplate.get("prob" + i, history_, (result, rowNumber) -> history_);
            history_ = history_.substring(history_.indexOf(" ") + 1);
            --i;
        }
    }

    private String history_2;
    public void setPredicts(int limit) {
        history_2 = this.backoffHistory;
        int ngramNumber = this.backoffHistory.split(" ").length;
        resultRow[] resr = new resultRow[ngramNumber];
        for (int i  = ngramNumber;i>0;--i){
            resultRow r = new resultRow();
            hbaseTemplate.get("prob" + i, history_2,"prob",(result, rowNumber) -> {
                r.setScanner(result);
                return null;
            });
            hbaseTemplate.get("prob" + i, history_2,"lambda","lambda",(result, rowNumber) -> {
                r.setLambda(Integer.parseInt(result.getValue("lambda".getBytes(),"lambda".getBytes()).toString()));
                return null;
            });
            resr[i]=r;
            history_2.substring()
        }


    }

    @Override
    public Predict[] getTopByNumber(int topK) {

    }

    @Override
    public Predict[] getTopByProb(double totalProb) {
        return new String[]{"adfadsf"};
    }
}
