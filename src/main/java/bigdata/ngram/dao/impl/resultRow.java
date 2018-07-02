package bigdata.ngram.dao.impl;

import bigdata.ngram.model.Predict;
import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.hbase.client.Result;

import java.util.Stack;

//todo: use IOC
public class resultRow {
    @Setter @Getter Result scanner;
    @Getter @Setter double lambda;
    @Getter private Stack<Predict> stack = new Stack<>();

    public Predict getNext(Predict predict){
        if (stack.isEmpty()){
            if (!advance()){
                return null;
            }
        }
        return stack.peek();
    }

    public boolean advance(){
        if (!scanner.advance()){
            return false;
        }
        if (!stack.isEmpty()){
            stack.pop();
        }
        stack.push(new Predict(scanner.current()));
        return true;
    }

}
