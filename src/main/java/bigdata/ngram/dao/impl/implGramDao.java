package bigdata.ngram.dao.impl;

import bigdata.ngram.dao.GramDao;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;


public class implGramDao implements GramDao {

    @Autowired private HbaseTemplate hbaseTemplate;
    @Getter @Setter private int ngramNumber;
    @Getter @Setter private String backoffHistory="";
    private String history;

    private String history_;
    public void setHistory(String history) {
        history_ = history.replaceAll("\\s+"," ");
        while(backoffHistory=="" && history_!=null){
            backoffHistory = hbaseTemplate.get(history_, "lambda", (result,rowNumber)->history_);

            history_ = history_.substring(history_.indexOf(" ")+1);
        }

    }




    @Override
    public String getLambda() {
        hbaseTemplate.execute()
    }

    @Override
    public String[] getTopByNumber(int topK) {
        return new String[0];
    }

    @Override
    public String[] getTopByProb(double totalProb) {
        return new String[0];
    }
}
