package bigdata.ngram.service.impl;

import bigdata.ngram.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(locations = "classpath*:application-context.xml")
public class ImplGramServiceTest {

    @Test
    public void test(){
        String[] followings = (new ImplGramService()).getPredict("san");
        for(String following:followings){
            System.out.println(following);
        }
    }
}