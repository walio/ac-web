package bigdata.ngram.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;

import java.util.Stack;

//wrapper for CellScanner,do cache, lambda and return predict instead of cell
//todo: use IOC
public class RowScanner {
    @Setter @Getter Result scanner;
    @Getter @Setter double lambda;
    //stack is for cache
    @Getter private Stack<Predict> stack = new Stack<>();

    public Predict current(){
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
        String current = new String(CellUtil.cloneValue(scanner.current()));
        stack.push(new Predict(current.split("=")[0],lambda*Double.parseDouble(current.split("=")[1])));
        return true;
    }

}
