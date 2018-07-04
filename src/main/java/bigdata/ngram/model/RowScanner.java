package bigdata.ngram.model;

import bigdata.ngram.model.Predict;
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
        Cell current = scanner.current();
        stack.push(new Predict(CellUtil.cloneQualifier(current).toString(),lambda*Double.parseDouble(CellUtil.cloneValue(current).toString())));
        return true;
    }

}
