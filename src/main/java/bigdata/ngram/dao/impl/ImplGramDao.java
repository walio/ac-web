package bigdata.ngram.dao.impl;

import bigdata.ngram.dao.GramDao;
import bigdata.ngram.model.Gram;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.hadoop.hbase.HbaseTemplate;


public class ImplGramDao implements GramDao {

    //fixme: @Autowired get nullPointerException in unittest
//    @Autowired private HbaseTemplate hbaseTemplate;
    ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
    HbaseTemplate hbaseTemplate = (HbaseTemplate) context.getBean("hbaseTemplate");

    @Getter @Setter private String history="";
    private String aaa="adsf";


    private String history_;
    public Gram getGram(String history,int limit) {
        history_ = history.trim().replaceAll("\\s+", " ");
        int i = history_.split(" ").length;
        String backoffHistory="";
        while (backoffHistory == "" && history_ != null) {
            backoffHistory = hbaseTemplate.get("order" + i, history_, (result, rowNumber) -> history_);
            history_ = history_.substring(history_.indexOf(" ") + 1);
            --i;
        }
        return new Gram(history,backoffHistory,limit);
    }

}
