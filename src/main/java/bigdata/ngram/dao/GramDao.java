package bigdata.ngram.dao;

public interface GramDao {

    String[] getTopByNumber(int topK);

    String[] getTopByProb(double totalProb,int limit);
}
