package bigdata.ngram.dao.impl;

import bigdata.ngram.dao.GramDao;
import bigdata.ngram.model.Gram;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;


public class ImplGramDao implements GramDao {

    @Autowired private HbaseTemplate hbaseTemplate;
    @Getter @Setter private String history="";


    private String history_;
    public Gram getGram(String history,int limit) {
        history_ = history.trim().replaceAll("\\s+", " ");
        int i = history_.split(" ").length;
        String backoffHistory="";
        while (backoffHistory == "" && history_ != null) {
            backoffHistory = hbaseTemplate.get("prob" + i, history_, (result, rowNumber) -> history_);
            history_ = history_.substring(history_.indexOf(" ") + 1);
            --i;
        }
        return new Gram(history,backoffHistory,limit);
    }

    public void test(){
        hbaseTemplate.get("order1", "lambda", (result, rowNumber) -> "test");
    }

}
