package bigdata.ngram;

import bigdata.ngram.dao.impl.ImplGramDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(locations = "classpath:application-context.xml",classes = Application.class)
public class NgramApplicationTests {

    @Test
    public void contextLoads() {
        ImplGramDao gramDao = new ImplGramDao();
        gramDao.test();
    }

}
