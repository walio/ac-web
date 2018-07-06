package bigdata.ngram.model;


import lombok.Getter;
import lombok.Setter;

public class Predict {
    @Getter @Setter private String following;
    @Getter @Setter private double prob;

    public Predict(){}

    public Predict(String following,double prob){
        this.following = following;
        this.prob = prob;
    }
}
