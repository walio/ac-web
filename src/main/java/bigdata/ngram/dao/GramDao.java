package bigdata.ngram.dao;

import bigdata.ngram.model.Predict;

public interface GramDao {

    String[] getBackoffHistory(String history);

    Predict[] getTopByNumber(int topK);

    Predict[] getTopByProb(double totalProb);
}
