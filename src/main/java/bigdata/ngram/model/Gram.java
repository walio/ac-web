package bigdata.ngram.model;

import lombok.Getter;
import lombok.Setter;

public class Gram {
    @Getter @Setter private String history;
    @Getter @Setter private String predict;
}
