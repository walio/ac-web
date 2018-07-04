package bigdata.ngram;

import bigdata.ngram.dao.GramDao;
import bigdata.ngram.dao.impl.ImplGramDao;
import bigdata.ngram.model.Gram;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class NgramApplicationTests {

    @Test
    public void contextLoads() {
        ImplGramDao gramDao = new ImplGramDao();
        gramDao.test();
    }

}
