package bigdata.ngram.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Gram {
    @Getter @Setter private String history="";
    @Getter @Setter private int limit=10;
    @Getter @Setter private ArrayList<Predict> predict=new ArrayList<Predict>();

    public Predict addPredict(Predict predict){
        if (this.predict.size()>=limit){
            return null;
        }
        this.predict.add(predict);
        return this.predict.get(this.predict.size()-1);
    }
}
