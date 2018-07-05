package bigdata.ngram.dao.impl;

import bigdata.ngram.Application;
import bigdata.ngram.dao.GramDao;
import bigdata.ngram.model.Gram;
import bigdata.ngram.model.Predict;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.CellUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(locations = "classpath*:application-context.xml")
public class ImplGramDaoTest {

    @Autowired HbaseTemplate hbaseTemplate;
    @Test
    public void test(){
//        System.out.println("asdfasdf");
//        byte[] s = hbaseTemplate.get("order2", "meps",
//                (result, rowNumber) ->  result.getValue("predict".getBytes(),"1".getBytes()) );
//        System.out.println(new String(s));
        ImplGramDao gramDao = new ImplGramDao();
        Gram gram = gramDao.getGram("san",10);
        ArrayList<Predict> predicts = gram.getPredicts();
        System.out.println("test start");
        System.out.println(gram.getBackoffHistory());
        for (Predict predict:predicts){
            System.out.print(predict.getFollowing()+"=");
            System.out.println(predict.getProb());
        }
    }

    @Test
    public void test1() throws Exception{
        CellScanner cs = hbaseTemplate.get("order1", " ",
                "predict",(result, rowNumber) -> result);
        System.out.println("@@@@@");
        System.out.println(new String(CellUtil.cloneValue(cs.current())));
        System.out.println("@@@@@");
    }

}