package bigdata.ngram.model;


import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;

public class Predict {
    @Getter @Setter private String following;
    @Getter @Setter private double prob;

    public Predict(){}

    public Predict(Cell cell){
        this.following = CellUtil.cloneQualifier(cell).toString();
        this.prob = Integer.parseInt(CellUtil.cloneValue(cell).toString());
    }
}
