package bigdata.ngram.dao;

import bigdata.ngram.model.Gram;

public interface GramDao {

    Gram getGram(String history,int limit);

}
